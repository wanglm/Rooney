package org.Rooney.hadoop.mapreduce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
		public Map<String, Long> UserMap;

		private void getIds(String fileName, Map<String, Long> map) {
			try (BufferedReader fis = new BufferedReader(new FileReader(
					fileName));) {
				String line = null;
				String[] lineData = null;
				while ((line = fis.readLine()) != null) {
					lineData = line.split(",");
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
			URI[] uris = Job.getInstance(context.getConfiguration())
					.getCacheFiles();
			adMap = new HashMap<String, Long>();// 存储转换的广告id
			UserMap = new HashMap<String, Long>();// 存储转换的用户id
			for (URI uri : uris) {
				String path = uri.getPath();
				if (path.equals("adIds")) {
					getIds(new Path(path).getName(), adMap);
				} else {
					getIds(new Path(path).getName(), UserMap);
				}
			}

		}

		@Override
		protected void map(Text key, Text value,
				Mapper<Text, Text, LongWritable, LongWritable>.Context context)
				throws IOException, InterruptedException {
			LongWritable adId = new LongWritable(adMap.get(key.toString()));
			LongWritable userId = new LongWritable(
					UserMap.get(value.toString()));
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
