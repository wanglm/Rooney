package org.Rooney.api.servlet;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import groovy.servlet.AbstractHttpServlet;
import groovy.util.ResourceException;

/**修改脚本获取位置，方便自定义groovy脚本路径
 * @author ming
 *
 */
public class ControllerServlet extends AbstractHttpServlet {

	@Override
	public URLConnection getResourceConnection(String name)
	throws ResourceException {
		name = removeNamePrefix(name).replace('\\', '/');

		//remove the leading / as we are trying with a leading / now
		if (name.startsWith("WEB-INF/groovy/")) {
			name = name.substring(15);//just for uniformity
		} else if (name.startsWith("/")) {
			name = name.substring(1);
		}

		/*
		 * Try to locate the resource and return an opened connection to it.
		 */
		try {
			URL url = servletContext.getResource('/' + name);
			//自定义groovy文件位置
			if (url == null) {
				url = servletContext.getResource(Groovys.INSTANCE.getProp().getProperty("groovySrc") + name);
			}
			if (url == null) {
				url = servletContext.getResource("/WEB-INF/groovy/" + name);
			}
			if (url == null) {
				throw new ResourceException("Resource \"" + name + "\" not found!");
			}
			return url.openConnection();
		} catch (IOException e) {
			throw new ResourceException("Problems getting resource named \"" + name + "\"!", e);
		}
	}

}
