package org.Rooney.hadoop.mahout.service.impl;

import java.io.IOException;

import org.Rooney.hadoop.HadoopConf;
import org.Rooney.hadoop.mahout.service.RecommendService;
import org.Rooney.hadoop.mapreduce.ConvertMahout;
import org.Rooney.hadoop.mapreduce.Cooccurrence;
import org.Rooney.hadoop.mapreduce.SimpleRecommend;
import org.Rooney.hadoop.mapreduce.UserVectors;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.mahout.cf.taste.hadoop.RecommendedItemsWritable;
import org.apache.mahout.cf.taste.hadoop.item.VectorAndPrefsWritable;
import org.apache.mahout.math.VarLongWritable;
import org.apache.mahout.math.VectorWritable;

/**
 * 推荐服务简单实现
 * 
 * @author ming
 *
 */
public class SimpleRecommendServiceImpl implements RecommendService {
	private final String JAR_NAME = "Rooney-hadoop-0.0.1.jar";

	@Override
	public Job convertLog(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance(HadoopConf.INSTANCE.get(), "conver-log");
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		job.setJarByClass(ConvertMahout.class);
		job.setJar(JAR_NAME);
		job.setMapperClass(ConvertMahout.ConvertMapper.class);
		job.setReducerClass(ConvertMahout.ConvertReducer.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		return job;
	}

	@Override
	public void recommend(String inputPath, String outputPath,String userVectorOutPath,String coOutputPath,String recommondUser,String recommondItem)
			throws IOException, ClassNotFoundException, InterruptedException {
		Configuration config = HadoopConf.INSTANCE.get();
		//分割用户向量
		Job baseUserVector = getUserVector(inputPath, userVectorOutPath);
		//物品共现矩阵
		Job cooccurrence = getCooccurrence(inputPath,coOutputPath);
		//收集以上两步的向量
		Job userVector=collectUserVector(userVectorOutPath,recommondUser);
		Job ItemVector=collectItemVector(coOutputPath,recommondItem);
		
		
		//实现推荐
		Job simpleRecommend = Job.getInstance(config, "simpleRecommend");
		FileInputFormat.addInputPath(simpleRecommend,new Path(recommondUser));
		FileInputFormat.addInputPath(simpleRecommend,new Path(recommondItem));
		FileOutputFormat.setOutputPath(simpleRecommend, new Path(outputPath));
		simpleRecommend.setMapperClass(SimpleRecommend.RecommedMapper.class);
		simpleRecommend.setCombinerClass(SimpleRecommend.RecommedCombiner.class);
		simpleRecommend.setReducerClass(SimpleRecommend.RecommedReducer.class);
		simpleRecommend.setMapOutputKeyClass(VarLongWritable.class);
		simpleRecommend.setMapOutputValueClass(VectorWritable.class);
		simpleRecommend.setOutputKeyClass(VarLongWritable.class);
		simpleRecommend.setOutputValueClass(RecommendedItemsWritable.class);

		ControlledJob job1 = new ControlledJob(config);
		job1.setJob(baseUserVector);
		ControlledJob job3 = new ControlledJob(config);
		job3.setJob(userVector);
		job3.addDependingJob(job1);
		
		ControlledJob job2 = new ControlledJob(config);
		job2.setJob(cooccurrence);
		ControlledJob job4 = new ControlledJob(config);
		job4.setJob(ItemVector);
		job4.addDependingJob(job2);
		
		ControlledJob job5 = new ControlledJob(config);
		job5.setJob(simpleRecommend);
		job5.addDependingJob(job3);
		job5.addDependingJob(job4);
		
		JobControl jcontrol = new JobControl("recommond");
		jcontrol.addJob(job1);
		jcontrol.addJob(job2);
		jcontrol.addJob(job3);
		jcontrol.addJob(job4);
		jcontrol.addJob(job5);

		Thread t = new Thread(jcontrol);
		t.start();
		// 这个等待线程执行完毕的方式好像不太正规的样子
		while (true) {
			if (jcontrol.allFinished()) {
				jcontrol.stop();
				break;
			}
		}
	}



	@Override
	public Job collectUserVector(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance(HadoopConf.INSTANCE.get(), "user-collect");
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		job.setJarByClass(SimpleRecommend.class);
		job.setJar(JAR_NAME);
		job.setMapperClass(SimpleRecommend.UserVectorSplitter.class);
		job.setReducerClass(SimpleRecommend.NoneDoReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(VectorAndPrefsWritable.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		return job;
		
	}

	@Override
	public Job collectItemVector(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance(HadoopConf.INSTANCE.get(), "item-collect");
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		job.setJarByClass(SimpleRecommend.class);
		job.setJar(JAR_NAME);
		job.setMapperClass(SimpleRecommend.CoWrapper.class);
		job.setReducerClass(SimpleRecommend.NoneDoReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(VectorAndPrefsWritable.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		return job;
	}

	@Override
	public Job getUserVector(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance(HadoopConf.INSTANCE.get(), "user-vector");
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		job.setJarByClass(UserVectors.class);
		job.setJar(JAR_NAME);
		job.setMapperClass(UserVectors.UserVectorMapper.class);
		job.setReducerClass(UserVectors.UserVectorReducer.class);
		job.setOutputKeyClass(VarLongWritable.class);
		job.setOutputValueClass(VectorWritable.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		return job;
	}

	@Override
	public Job getCooccurrence(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance(HadoopConf.INSTANCE.get(), "item-cooccurrence");
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		job.setJarByClass(Cooccurrence.class);
		job.setJar(JAR_NAME);
		job.setMapperClass(Cooccurrence.CoMapper.class);
		job.setReducerClass(Cooccurrence.coReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(VectorWritable.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		return job;
	}
	
	
	

}
