package org.Rooney.hadoop.mapreduce;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class ConvertID {
	private static final Log LOG = LogFactory.getLog(ConvertID.class);

	public static class convertMapperForUser extends
			Mapper<Text, Text, Text, Text> {
		private static final Text USER = new Text("user");
		private String str;

		@Override
		protected void map(Text key, Text value,
				Mapper<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			str = value.toString();
			if (str.length() > 50) {
				LOG.info("特殊行：------" + str);
				String[] line = str.split(",");
				String userId = line[1];
				context.write(new Text(userId), USER);
			} else {
				context.write(value, USER);
			}
		}

	}

	public static class convertMapperForItme extends
			Mapper<Text, Text, Text, Text> {
		private static final Text ITEM = new Text("item");
		private String str;

		@Override
		protected void map(Text key, Text value,
				Mapper<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			str = value.toString();
			if (str.length() > 50) {
				LOG.info("特殊行：------" + str);
				String[] line = str.split(",");
				String itemID = line[0];
				context.write(new Text(itemID),ITEM);
			} else {
				context.write(key,ITEM);
			}
		}

	}

	public static class convertReducer extends
			Reducer<Text, Text, Text, LongWritable> {
		private Random random = new Random();

		@Override
		protected void reduce(Text key, Iterable<Text> value,
				Reducer<Text, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			long time = System.currentTimeMillis();
			long randomLong = Long.valueOf(random.nextInt(100));
			long idLong = time + Long.valueOf(random.nextInt(100));
			String idstr = String.valueOf(idLong) + String.valueOf(randomLong);
			LongWritable id = new LongWritable(Long.valueOf(idstr));
			context.write(key, id);
		}

	}

}
