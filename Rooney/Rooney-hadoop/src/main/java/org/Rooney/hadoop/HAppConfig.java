package org.Rooney.hadoop;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public enum HAppConfig {
	INSTANCE;
	private Properties pop;

	private HAppConfig() {
		Properties _pop = new Properties();
		try {
			_pop.load(HAppConfig.class.getResourceAsStream("/hadoop.properties"));
			this.pop = _pop;
		} catch (IOException e) {
			Logger log = LogManager.getLogger(HAppConfig.class);
			log.error("hadoop-app配置文件读取错误！", e);
		}
	}

	public final Properties getProp() {
		return this.pop;
	}

	public String getValue(String key) {
		return this.pop.getProperty(key);
	}
}
