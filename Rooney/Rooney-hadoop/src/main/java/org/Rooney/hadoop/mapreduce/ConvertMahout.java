package org.Rooney.hadoop.mapreduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.Rooney.hadoop.HAppConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class ConvertMahout {
	private static final Log LOG = LogFactory.getLog(ConvertMahout.class);

	public static class ConvertMapper extends
			Mapper<Text, Text, LongWritable, LongWritable> {
		public Map<String, Long> adMap;
		public Map<String, Long> userMap;
		private String str;

		/**
		 * 官方教程上获取cache的方法是错的，会造成数据错误 这是实验过的正确的使用cache的方法
		 * 
		 * @param in
		 * @param map
		 */
		private void getIds(String inPath, FileSystem fs, Map<String, Long> map) {
			try (FSDataInputStream in = fs.open(new Path(inPath));
					BufferedReader fis = new BufferedReader(
							new InputStreamReader(in));) {
				String line = null;
				String[] lineData = null;
				while ((line = fis.readLine()) != null) {
					lineData = line.split("\\s+");
					map.put(lineData[0], Long.valueOf(lineData[1]));
				}
			} catch (IOException ioe) {
				LOG.error("读取cache文件出错：    ", ioe);
			}
		}

		@Override
		protected void setup(
				Mapper<Text, Text, LongWritable, LongWritable>.Context context)
				throws IOException, InterruptedException {
			FileSystem fs = FileSystem.get(context.getConfiguration());
			URI[] uris = Job.getInstance(context.getConfiguration())
					.getCacheFiles();
			adMap = new HashMap<String, Long>();// 存储转换的广告id
			userMap = new HashMap<String, Long>();// 存储转换的用户id
			for (URI uri : uris) {
				String path = uri.getPath();
				if (path.equals(HAppConfig.INSTANCE.getValue("itemIdPath")
						+ "/part-r-00000")) {
					getIds(path, fs, adMap);
				} else {
					getIds(path, fs, userMap);
				}
			}
		}

		@Override
		protected void map(Text key, Text value,
				Mapper<Text, Text, LongWritable, LongWritable>.Context context)
				throws IOException, InterruptedException {
			String userID = null;
			String itemID = null;
			str = value.toString();
			if (str.length() > 50) {
				LOG.info("特殊行：------" + str);
				String[] line = str.split(",");
				userID = line[1];
				itemID = line[0];
			} else {
				userID = value.toString();
				itemID = key.toString();
			}
			LOG.info("itemID是否为空：------" + itemID);
			LongWritable adId = new LongWritable(adMap.get(itemID));
			LongWritable userId = new LongWritable(userMap.get(userID));
			context.write(userId, adId);
		}

	}

	public static class ConvertReducer extends
			Reducer<LongWritable, LongWritable, LongWritable, Text> {

		@Override
		protected void reduce(
				LongWritable key,
				Iterable<LongWritable> values,
				Reducer<LongWritable, LongWritable, LongWritable, Text>.Context context)
				throws IOException, InterruptedException {
			StringBuilder text = new StringBuilder();
			Iterator<LongWritable> iterator = values.iterator();
			while (iterator.hasNext()) {
				text.append(iterator.next().toString());
				if (iterator.hasNext()) {
					text.append(",");
				}
			}
			context.write(key, new Text(text.toString()));
		}

	}

}
