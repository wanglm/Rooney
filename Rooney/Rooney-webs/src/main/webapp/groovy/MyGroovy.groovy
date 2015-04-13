import groovy.json.JsonBuilder

this."${params.method}"()

void json(){
	def json=new JsonBuilder()
	//()要：
	//{}不用：
	json(cn :'wangluming',en:'wlm')
	response.setContentType("application/json;charset=UTF-8")
	println json
}

void xml(){
	String xmlText="""<?xml version="1.0" encoding="UTF-8"?>

<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	id="WebApp_ID" version="3.0">
	<display-name>waps-webs</display-name>
	<welcome-file-list>
		<welcome-file>index.jsp</welcome-file>
	</welcome-file-list>

	<servlet>
		<servlet-name>Groovy</servlet-name>
		<servlet-class>org.waps.groovy.MyGroovyServlet</servlet-class>
	</servlet>

	<servlet>
		<servlet-name>Groovy2</servlet-name>
		<servlet-class>org.waps.groovy.MyGroovyServlet</servlet-class>
	</servlet>

	<servlet-mapping>
		<servlet-name>Groovy</servlet-name>
		<url-pattern>*.groovy</url-pattern>
		<url-pattern>*.gdo</url-pattern>
	</servlet-mapping>
</web-app>
	
					"""
	def xml=new XmlSlurper().parseText(xmlText)
	//默认根节点。。。
	//特殊符号需要.字符串的形式获取
	//命名空间同上
	//属性获取@+name
	//多节点相同层次，如同数组遍历
	
	println xml.servlet[1].'servlet-name'
}