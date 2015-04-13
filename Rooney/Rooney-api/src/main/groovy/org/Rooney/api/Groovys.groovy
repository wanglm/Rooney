package org.Rooney.api


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**读取配置文件
 * 如果不设置配置文件groovy.properties就采用默认配置
 * @author ming
 *
 */
enum Groovys {
	INSTANCE
	Properties pop
	private Groovys(){
		def _pop=new Properties()
		_pop.load(new File('/groovy-default.properties').newInputStream())
		def gPro=new File('groovy.properties')
		if(gPro.exists()){
			_pop.load(gPro.newInputStream())
		}else{
			def log = LogManager.getLogger(Groovys)
			log.info("在路径${gPro.absolutePath}下${gPro.name} 不存在，采用默认配置!")
		}
		this.pop=_pop
	}
	
	final Properties getProp(){
		return this.pop
	}
}
