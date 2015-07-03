package org.Rooney.hadoop.mahout.service;

import java.io.IOException;

public interface RecommendService {
	/**
	 * 将原始的日志转换成推荐算法需要的格式
	 * 
	 * @param inputPath
	 *            来源文件的hdfs目录
	 * @param outputPath
	 *            输出文件的hdfs目录
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public void convertLog(String inputPath, String outputPath) throws IOException, ClassNotFoundException, InterruptedException;

	/**
	 * 推荐
	 * 
	 * @param inputPath
	 *            来源文件的hdfs目录
	 * @param outputPath
	 *            输出文件的hdfs目录
	 */
	public void recommend(String inputPath, String outputPath);

}
