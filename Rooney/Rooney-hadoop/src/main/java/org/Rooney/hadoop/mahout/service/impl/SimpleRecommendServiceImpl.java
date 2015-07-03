package org.Rooney.hadoop.mahout.service.impl;

import java.io.IOException;

import org.Rooney.hadoop.HadoopConf;
import org.Rooney.hadoop.mahout.service.RecommendService;
import org.Rooney.hadoop.mapreduce.ConvertMahout;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

/**
 * 推荐服务简单实现
 * 
 * @author ming
 *
 */
public class SimpleRecommendServiceImpl implements RecommendService {
	private final String JAR_NAME="Rooney-hadoop-0.0.1.jar";

	@Override
	public void convertLog(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance(HadoopConf.INSTANCE.get());
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		job.setJarByClass(ConvertMahout.class);
		job.setJar(JAR_NAME);
		job.setMapperClass(ConvertMahout.ConvertMapper.class);
		job.setReducerClass(ConvertMahout.ConvertReducer.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		job.submit();
	}

	@Override
	public void recommend(String inputPath, String outputPath) {
		// TODO Auto-generated method stub

	}

}
