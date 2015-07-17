package org.Rooney.hadoop.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.Rooney.hadoop.HAppConfig;
import org.Rooney.hadoop.HadoopConf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CheckID {
	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				String tokens = itr.nextToken();
				word.set(tokens);
				context.write(word, one);
			}
		}
	}

	public static class IntSumReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		String input = HAppConfig.INSTANCE.getValue("userIdPath");
		String output = "/user/ming/check/userid";
		FileSystem fs = FileSystem.get(HadoopConf.INSTANCE.get());
		Path out = new Path(output);
		if (fs.exists(out)) {
			fs.delete(out, true);
		}
		Configuration con = HadoopConf.INSTANCE.get();
		Job job = Job.getInstance(con, "check id");
		job.setJar(HAppConfig.INSTANCE.getValue("jar"));
		job.setJarByClass(CheckID.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(input));
		FileOutputFormat.setOutputPath(job, new Path(output));
		job.submit();
	}

}
