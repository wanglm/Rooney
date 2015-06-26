package org.Rooney.apps

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

enum AppConfig {
	INSTANCE
	private Properties pop
	private AppConfig(){
		def _pop=new Properties()
		try {
			_pop.load(AppConfig.getResourceAsStream("/app.properties"))
			this.pop=_pop
		}catch(IOException e){
			def log = LogManager.getLogger(AppConfig)
			log.error("app配置文件读取错误！", e)
		}
	}

	final Properties getProp(){
		return this.pop
	}

	String getValue(String key){
		return this.pop.getProperty(key)
	}
}
