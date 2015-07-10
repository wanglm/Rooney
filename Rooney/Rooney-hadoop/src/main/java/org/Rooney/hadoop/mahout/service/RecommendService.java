package org.Rooney.hadoop.mahout.service;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;

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
	public Job convertLog(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException;

	/**
	 * 推荐
	 * 
	 * @param inputPath
	 *            来源文件的hdfs目录
	 * @param outputPath
	 *            输出文件的hdfs目录
	 * @param userVectorOutPath
	 *            用户向量输出目录
	 * @param coOutputPath
	 *            共现矩阵输出目录
	 * @param recommondUser 推荐的用户向量输入
	 * @param recommondItem 推荐的物品向量输入
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public void recommend(String inputPath, String outputPath,
			String userVectorOutPath, String coOutputPath,
			String recommondUser, String recommondItem) throws IOException,
			ClassNotFoundException, InterruptedException;

	/**
	 * 收集用户向量
	 * 
	 * @param inputPath
	 * @param outputPath
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public Job collectUserVector(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException;

	/**
	 * 收集物品向量
	 * 
	 * @param inputPath
	 * @param outputPath
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public Job collectItemVector(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException;

	/**
	 * 分割用户向量
	 * 
	 * @param inputPath
	 * @param outputPath
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public Job getUserVector(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException;

	/**
	 * 物品共现矩阵
	 * 
	 * @param inputPath
	 * @param outputPath
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public Job getCooccurrence(String inputPath, String outputPath)
			throws IOException, ClassNotFoundException, InterruptedException;

}
