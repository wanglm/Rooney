package org.Rooney.hadoop;

import java.io.IOException;

import org.Rooney.hadoop.mahout.service.RecommendService;
import org.Rooney.hadoop.mahout.service.impl.SimpleRecommendServiceImpl;
import org.Rooney.hadoop.mapreduce.ConvertID;
import org.Rooney.hadoop.mapreduce.SimpleRecommend;
import org.Rooney.hadoop.mapreduce.inputformat.LogInputFormat;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.mahout.cf.taste.hadoop.RecommendedItemsWritable;
import org.apache.mahout.math.VarLongWritable;
import org.apache.mahout.math.VectorWritable;

public class Apps {
	private static final String JAR_NAME = "Rooney-hadoop-0.0.1-jar-with-dependencies.jar";

	public static void main(String... args) {
		try {
			RecommendService service=new SimpleRecommendServiceImpl();
			String inputPath=HAppConfig.INSTANCE.getValue("recommendInputPath");
			String outputPath=HAppConfig.INSTANCE.getValue("recommendOutputPath");
			String userVectorOutPath=HAppConfig.INSTANCE.getValue("userVectorOutPath");
			String coOutputPath=HAppConfig.INSTANCE.getValue("coOutputPath");
			String recommondUser=HAppConfig.INSTANCE.getValue("recommondUser");
			String recommondItem=HAppConfig.INSTANCE.getValue("recommondItem");
			service.recommend(inputPath, outputPath, userVectorOutPath, coOutputPath, recommondUser, recommondItem);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void  recommond() throws IOException, ClassNotFoundException, InterruptedException{
		Job job = Job.getInstance(HadoopConf.INSTANCE.get(), "recommond");
		FileSystem fs = FileSystem.get(HadoopConf.INSTANCE.get());
		Path out = new Path(HAppConfig.INSTANCE.getValue("recommendOutputPath"));
		if (fs.exists(out)) {
			fs.delete(out, true);
		}
		FileInputFormat.addInputPath(job, new Path(HAppConfig.INSTANCE.getValue("recommondVectors")));
		FileOutputFormat.setOutputPath(job, out);
		job.setJarByClass(SimpleRecommend.class);
		job.setJar(JAR_NAME);
		job.setMapperClass(SimpleRecommend.RecommedMapper.class);
		job.setCombinerClass(SimpleRecommend.RecommedCombiner.class);
		job.setReducerClass(SimpleRecommend.RecommedReducer.class);
		job.setMapOutputKeyClass(VarLongWritable.class);
		job.setMapOutputValueClass(VectorWritable.class);
		job.setOutputKeyClass(VarLongWritable.class);
		job.setOutputValueClass(RecommendedItemsWritable.class);
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.submit();
	}
	
	public static void  convertUserID() throws IOException, ClassNotFoundException, InterruptedException{
		Job job = Job.getInstance(HadoopConf.INSTANCE.get(), "conver-user-ids");
		FileSystem fs = FileSystem.get(HadoopConf.INSTANCE.get());
		Path out = new Path(HAppConfig.INSTANCE.getValue("userIdPath"));
		if (fs.exists(out)) {
			fs.delete(out, true);
		}
		FileInputFormat.addInputPath(job, new Path("/user/ming/input"));
		FileOutputFormat.setOutputPath(job, out);
		job.setJarByClass(ConvertID.class);
		job.setJar(JAR_NAME);
		job.setMapperClass(ConvertID.convertMapperForUser.class);
		job.setReducerClass(ConvertID.convertReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		job.setInputFormatClass(LogInputFormat.class);
		job.submit();
	}
	
	public static void  convertItemID() throws IOException, ClassNotFoundException, InterruptedException{
		Job job = Job.getInstance(HadoopConf.INSTANCE.get(), "conver-item-ids");
		FileSystem fs = FileSystem.get(HadoopConf.INSTANCE.get());
		Path out = new Path(HAppConfig.INSTANCE.getValue("itemIdPath"));
		if (fs.exists(out)) {
			fs.delete(out, true);
		}
		FileInputFormat.addInputPath(job, new Path("/user/ming/input"));
		FileOutputFormat.setOutputPath(job, out);
		job.setJarByClass(ConvertID.class);
		job.setJar(JAR_NAME);
		job.setMapperClass(ConvertID.convertMapperForItme.class);
		job.setReducerClass(ConvertID.convertReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		job.setInputFormatClass(LogInputFormat.class);
		job.submit();
	}
}
