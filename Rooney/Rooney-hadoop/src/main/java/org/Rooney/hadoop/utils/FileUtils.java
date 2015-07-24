package org.Rooney.hadoop.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.Rooney.hadoop.ConfigPath;
import org.Rooney.hadoop.HAppConfig;
import org.Rooney.hadoop.HadoopConf;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileUtils {

	public static void putFileToHDFS(String fileName,ConfigPath configPath) throws IOException,NullPointerException {
		FileSystem fs = FileSystem.get(HadoopConf.INSTANCE.get());
		StringBuilder url = new StringBuilder();
		String localUrl=null;
		switch (configPath) {
			case LOG_HDFS_PATH: {
				url.append(HAppConfig.INSTANCE.getValue("logHDFSPath"));
				localUrl=HAppConfig.INSTANCE.getValue("logPath");
				break;
			}
			case RECOMMEND_ITEM: {
				url.append(HAppConfig.INSTANCE.getValue("recommendItemPath"));
				localUrl=HAppConfig.INSTANCE.getValue("recommendItemLocal");
				break;
			}
			case USER_ID: {
				url.append(HAppConfig.INSTANCE.getValue("userIdPath"));
				localUrl=HAppConfig.INSTANCE.getValue("userIdLocal");
				break;
			}
			case ITEM_ID: {
				url.append(HAppConfig.INSTANCE.getValue("itemIdPath"));
				localUrl=HAppConfig.INSTANCE.getValue("itemIdLocal");
				break;
			}
			default:{
				break;
			}
		}
		if(StringUtils.isBlank(url.toString())){
			throw new NullPointerException("hdfs路径不能为空！");
		}else{
			String today=getToday();
			url.append("/"+today);
			Path path=new Path(url.toString());
			if(!fs.exists(path)){
				//采用默认权限，即启动hdfs的用户
				fs.mkdirs(path);
			}
			String fn="/"+fileName;
			url.append(fn);
			localUrl+=fn;
			fs.copyFromLocalFile(new Path(localUrl), new Path(url.toString()));
		}
	}

	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String getToday() {
		return getDateString(new Date(), "yyyyMMdd");
	}

	public static String getToday(String pattern) {
		return getDateString(new Date(), pattern);
	}
	
	/**重置输出目录
	 * @param output
	 * @param config
	 * @throws IOException
	 */
	public static Path reSetOutput(String output,Configuration config) throws IOException{
		FileSystem fs=FileSystem.get(HadoopConf.INSTANCE.get());
		Path out=new Path(output); 
		if (fs.exists(out)) {
			fs.delete(out, true);
		}
		return out;
	}
}
