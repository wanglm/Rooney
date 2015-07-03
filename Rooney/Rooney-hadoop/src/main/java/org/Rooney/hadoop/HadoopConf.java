package org.Rooney.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public enum HadoopConf {
	INSTANCE;
	private Configuration conf;

	private HadoopConf() {
		Configuration _conf = new Configuration();
		try {
			_conf.addResource("core-site.xml");
			_conf.addResource("hdfs-site.xml");
			_conf.addResource("mapred-site.xml");
			_conf.addResource("yarn-site.xml");
		} catch (Exception e) {
			Logger log = LogManager.getLogger(HadoopConf.class);
			log.error("hadoop配置文件读取出错！", e);
		}
		this.conf = _conf;
	}

	public Configuration get() {
		return this.conf;
	}
}
