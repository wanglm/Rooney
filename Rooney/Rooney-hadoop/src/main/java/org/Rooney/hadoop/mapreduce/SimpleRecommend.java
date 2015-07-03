package org.Rooney.hadoop.mapreduce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.cf.taste.hadoop.RecommendedItemsWritable;
import org.apache.mahout.cf.taste.hadoop.item.VectorAndPrefsWritable;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;
import org.apache.mahout.cf.taste.impl.recommender.ByValueRecommendedItemComparator;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.math.VarLongWritable;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.math.Vector.Element;

public class SimpleRecommend {
	private static final Log LOG = LogFactory.getLog(SimpleRecommend.class);
	private static final int NUM = 5;// 推荐数量

	/**
	 * 封装共现矩阵关系列
	 * 
	 * @author ming
	 *
	 */
	public static class CoWrapper
			extends
			Mapper<IntWritable, VectorWritable, IntWritable, VectorOrPrefWritable> {

		@Override
		protected void map(
				IntWritable key,
				VectorWritable value,
				Mapper<IntWritable, VectorWritable, IntWritable, VectorOrPrefWritable>.Context context)
				throws IOException, InterruptedException {
			context.write(key, new VectorOrPrefWritable(value.get()));
		}

	}

	/**
	 * 分割用户向量
	 * 
	 * @author ming
	 *
	 */
	public static class UserVectorSplitter
			extends
			Mapper<IntWritable, VectorWritable, IntWritable, VectorOrPrefWritable> {

		@Override
		protected void map(
				IntWritable key,
				VectorWritable value,
				Mapper<IntWritable, VectorWritable, IntWritable, VectorOrPrefWritable>.Context context)
				throws IOException, InterruptedException {
			long userID = key.get();
			Vector userVector = value.get();
			Iterator<Element> it = userVector.nonZeroes().iterator();
			IntWritable itemIndexWritable = new IntWritable();
			while (it.hasNext()) {
				Element e = it.next();
				int itemIndex = e.index();
				float preferenceValue = (float) e.get();
				itemIndexWritable.set(itemIndex);
				context.write(itemIndexWritable, new VectorOrPrefWritable(
						userID, preferenceValue));
			}
		}

	}

	/**
	 * 计算推荐向量
	 * 
	 * @author ming
	 *
	 */
	public static class RecommedMapper
			extends
			Mapper<IntWritable, VectorAndPrefsWritable, VarLongWritable, VectorWritable> {

		@Override
		protected void map(
				IntWritable key,
				VectorAndPrefsWritable value,
				Mapper<IntWritable, VectorAndPrefsWritable, VarLongWritable, VectorWritable>.Context context)
				throws IOException, InterruptedException {
			Vector coColumn = value.getVector();
			List<Long> userIDs = value.getUserIDs();
			List<Float> prefValues = value.getValues();
			for (int i = 0, n = userIDs.size(); i < n; i++) {
				long userID = userIDs.get(i);
				float prefValue = prefValues.get(i);
				Vector partialProduct = coColumn.times(prefValue);
				context.write(new VarLongWritable(userID), new VectorWritable(
						partialProduct));
			}
		}

	}

	/**
	 * 本地部分聚合
	 * 
	 * @author ming
	 *
	 */
	public static class RecommedCombiner
			extends
			Reducer<VarLongWritable, VectorWritable, VarLongWritable, VectorWritable> {

		@Override
		protected void reduce(
				VarLongWritable key,
				Iterable<VectorWritable> values,
				Reducer<VarLongWritable, VectorWritable, VarLongWritable, VectorWritable>.Context context)
				throws IOException, InterruptedException {
			Vector partial = null;
			for (VectorWritable value : values) {
				partial = (partial == null) ? value.get() : partial.plus(value
						.get());
			}
			context.write(key, new VectorWritable(partial));
		}

	}

	/**
	 * 形成推荐
	 * 
	 * @author ming
	 *
	 */
	public static class RecommedReducer
			extends
			Reducer<VarLongWritable, VectorWritable, VarLongWritable, RecommendedItemsWritable> {
		private Map<Integer, Long> itemMap;// 自定义补充的物品
		private int itemNum = 0; // 自定义补充的物品数量，防止越界

		@Override
		protected void setup(
				Reducer<VarLongWritable, VectorWritable, VarLongWritable, RecommendedItemsWritable>.Context context)
				throws IOException, InterruptedException {
			URI[] uris = Job.getInstance(context.getConfiguration())
					.getCacheFiles();
			Path path = null;
			for (URI uri : uris) {
				String pathUrl = uri.getPath();
				if (pathUrl.equals("items")) {
					path = new Path(pathUrl);
				}
			}
			try (BufferedReader fis = new BufferedReader(new FileReader(
					path.getName()));) {
				String line = null;
				String[] lineData = null;
				while ((line = fis.readLine()) != null) {
					itemNum++;
					lineData = line.split(",");
					itemMap.put(Integer.valueOf(lineData[0]),
							Long.valueOf(lineData[1]));
				}
			} catch (IOException e) {
				LOG.error("读取item文件出错！  ", e);
				throw e;
			}
		}

		@Override
		protected void reduce(
				VarLongWritable key,
				Iterable<VectorWritable> values,
				Reducer<VarLongWritable, VectorWritable, VarLongWritable, RecommendedItemsWritable>.Context context)
				throws IOException, InterruptedException {
			Vector recommendationVector = null;
			for (VectorWritable value : values) {
				recommendationVector = (recommendationVector == null) ? value
						.get() : recommendationVector.plus(value.get());
			}
			Queue<RecommendedItem> topitems = new PriorityQueue<RecommendedItem>(
					NUM,
					Collections.reverseOrder(ByValueRecommendedItemComparator
							.getInstance()));
			Iterator<Element> it = recommendationVector.nonZeroes().iterator();
			while (it.hasNext()) {
				Element e = it.next();
				int index = e.index();
				index = index > itemNum ? itemNum : index;
				float value = (float) e.get();
				if (topitems.size() < NUM) {
					// 添加物品，但是可以自定义
					topitems.add(new GenericRecommendedItem(itemMap.get(index),
							value));
				} else if (value > topitems.peek().getValue()) {
					topitems.add(new GenericRecommendedItem(itemMap.get(index),
							value));
					topitems.poll();
				}
			}
			List<RecommendedItem> recommendations = new ArrayList<RecommendedItem>(
					topitems.size());
			recommendations.addAll(topitems);
			Collections.sort(recommendations,
					ByValueRecommendedItemComparator.getInstance());
			context.write(key, new RecommendedItemsWritable(recommendations));
		}

	}

}
