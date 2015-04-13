package org.Rooney.api.servlet

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.groovy.runtime.GroovyCategorySupport;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.servlet.ServletBinding;
import groovy.servlet.ServletCategory;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

/**重写GroovyServlet的service方法
 * 做通用设置
 * @author ming
 *
 */
class GroovyController extends ControllerServlet {
	/**
	 * The script engine executing the Groovy scripts for this servlet
	 */
	private GroovyScriptEngine gse;

	/**
	 * Initialize the GroovyServlet.
	 *
	 * @throws ServletException
	 *  if this method encountered difficulties
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// Set up the scripting engine
		gse = createGroovyScriptEngine();

		servletContext.log("Groovy servlet initialized on " + gse + ".");
	}
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		request.setCharacterEncoding("UTF-8")
		response.setContentType("text/html;charset=UTF-8")
		response.setHeader("Cache-Control","no-stored")
		response.setHeader("Pragma","no-cache")
		response.setDateHeader("Expires",0)
		// Get the script path from the request - include aware (GROOVY-815)
		final String scriptUri = getScriptUri(request);

		/*// Set it to HTML by default
		 response.setContentType("text/html; charset="+encoding);*/

		// Set up the script context
		final Binding binding = new ServletBinding(request, response, servletContext);

		// Run the script
		try {
			Closure closure = new Closure(gse) {

						public Object call() {
							try {
								return ((GroovyScriptEngine) getDelegate()).run(scriptUri, binding);
							} catch (ResourceException e) {
								throw new RuntimeException(e);
							} catch (ScriptException e) {
								throw new RuntimeException(e);
							}
						}

					};
			GroovyCategorySupport.use(ServletCategory.class, closure);
			/*
			 * Set reponse code 200.
			 */
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (RuntimeException runtimeException) {
			StringBuffer error = new StringBuffer("GroovyServlet Error: ");
			error.append(" script: '");
			error.append(scriptUri);
			error.append("': ");
			Throwable e = runtimeException.getCause();
			/*
			 * Null cause?!
			 */
			if (e == null) {
				error.append(" Script processing failed.");
				error.append(runtimeException.getMessage());
				if (runtimeException.getStackTrace().length > 0)
					error.append(runtimeException.getStackTrace()[0].toString());
				servletContext.log(error.toString());
				System.err.println(error.toString());
				runtimeException.printStackTrace(System.err);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, error.toString());
				return;
			}
			/*
			 * Resource not found.
			 */
			if (e instanceof ResourceException) {
				error.append(" Script not found, sending 404.");
				servletContext.log(error.toString());
				System.err.println(error.toString());
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			/*
			 * Other internal error. Perhaps syntax?! 
			 */
			servletContext.log("An error occurred processing the request", runtimeException);
			error.append(e.getMessage());
			if (e.getStackTrace().length > 0)
				error.append(e.getStackTrace()[0].toString());
			servletContext.log(e.toString());
			System.err.println(e.toString());
			runtimeException.printStackTrace(System.err);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} finally {
			/*
			 * Finally, flush the response buffer.
			 */
			response.flushBuffer();
			// servletContext.log("Flushed response buffer.");
		}
	}

	/**
	 * Hook method to setup the GroovyScriptEngine to use.<br/>
	 * Subclasses may override this method to provide a custom
	 * engine.
	 */
	protected GroovyScriptEngine createGroovyScriptEngine(){
		return new GroovyScriptEngine(this);
	}
}
