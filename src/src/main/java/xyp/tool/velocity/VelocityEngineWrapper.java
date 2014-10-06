package xyp.tool.velocity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class VelocityEngineWrapper {

	private static Log logger = LogFactory.getLog(VelocityEngineWrapper.class);

	private VelocityEngineWrapper() {
	}

	private VelocityEngine velocityEngine = null;

	public static VelocityEngineWrapper newInstance(InputStream is) throws FileNotFoundException,
			IOException {
		try {
			VelocityEngineWrapper eng = new VelocityEngineWrapper();
			Properties props = new Properties();
			props.load(is);
			eng.velocityEngine = new VelocityEngine();
			eng.velocityEngine.init(props);
			return eng;
		} finally {
			is.close();
		}
	}
	
	public String mergeTemplate(String templateName, Map<String, ?> param) {

		StringWriter sw = new StringWriter();
		mergeTemplate(templateName, param, sw);
		try {
			sw.close();
		} catch (IOException e) {
			logger.error(e, e);
		}
		return sw.toString();
	}

	public void mergeTemplate(String templateName, Map<String, ?> param, Writer writer) {
		Template template = velocityEngine.getTemplate(templateName);
		VelocityContext context1 = new VelocityContext();
		if (null != param) {
			Iterator<?> iter = param.entrySet().iterator();  
			while (iter.hasNext()) {
				@SuppressWarnings("unchecked")
				Entry<String, Object> ent =  (Entry<String, Object>) (iter.next());
				context1.put(ent.getKey(), ent.getValue());
			} 
		}
		template.merge(context1, writer);

	}

	public boolean evaluate(Map<String, Object> param, Writer out, String instring)
			throws ParseErrorException, MethodInvocationException, ResourceNotFoundException {
		VelocityContext context1 = null;
		if (null != param) {
			context1 = new VelocityContext();
			Iterator<Entry<String, Object>> iter = param.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, ? extends Object> ent = iter.next();
				context1.put(ent.getKey(), ent.getValue());
			}
		}
		return velocityEngine.evaluate(context1, out, "", instring);
	}

	public String evaluate(Map<String, Object> param, String instring) throws ParseErrorException,
			MethodInvocationException, ResourceNotFoundException {

		StringWriter sw = new StringWriter();
		evaluate(param, sw, instring);
		try {
			sw.close();
		} catch (IOException e) {
			logger.error(e, e);
		}
		return sw.toString();
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
}
