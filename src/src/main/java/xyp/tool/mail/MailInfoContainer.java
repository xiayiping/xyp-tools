package xyp.tool.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xyp.tool.util.StringUtil;

/**
 * inline key : <br/>
 * mail.inline.bar=/path/to/file, the key is 'bar' <br/>
 * in mail content , put inline key as : &lt;img src="cid:bar" /&gt;
 * @author xiayip
 *
 */
public class MailInfoContainer {

	private static Log logger = LogFactory.getLog(MailInfoContainer.class);

	public static final String CONFIG_MAIL_INFO_PATH = "mail.info.path";

	public static final String MAIL_PROP_SUBJECT = "mail.subject";
	public static final String MAIL_PROP_TEXT_TEMPLATE = "mail.text.template";
	public static final String MAIL_PROP_TEXT = "mail.text";
	public static final String MAIL_PROP_TO = "mail.to";
	public static final String MAIL_PROP_CC = "mail.cc";
	public static final String MAIL_PROP_BCC = "mail.bcc";
	public static final String MAIL_PROP_ATTACH = "mail.attach";
	public static final String MAIL_PROP_INLINE = "mail.inline";

	private Map<String, MailInfo> mailInfoMap = new HashMap<String, MailInfo>();

	private String rootPath = null;

	public Map<String, MailInfo> getMailInfoMap() {
		return mailInfoMap;
	}

	public MailInfo getMailInfo(String name) {
		return mailInfoMap.get(name);
	}

	private MailInfoContainer(String rootPath) {
		File root = new File(rootPath);
		if (!root.exists()) {
			root.mkdirs();
		}
		rootPath = StringUtil.formatPathSlash(rootPath);
		rootPath = StringUtil.erasePathEndSlash(rootPath);
		this.rootPath = rootPath;
		processFile(root);
	}

	public static MailInfoContainer newInstance(File config) {
		MailInfoContainer container = null;
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(config));
			String rootPath = (String) props.get(CONFIG_MAIL_INFO_PATH);
			container = new MailInfoContainer(rootPath);
		} catch (FileNotFoundException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}
		return container;
	}

	private void processFile(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				processFile(f);
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("processing " + file.getAbsolutePath());
			}
			MailInfo mi = createMailInfo(file);
			if (mi != null) {
				String key = file.getAbsolutePath().substring(rootPath.length() + 1);
				key = StringUtil.formatPathSlash(key);
				this.mailInfoMap.put(key, mi);
			}
		}
	}

	
	private MailInfo createMailInfo(File propsFile) {
		MailInfo mi = null;
		Properties props = new Properties();

		try {
			props.load(new FileInputStream(propsFile));

			Map<String, String> inlineMap = new HashMap<String, String>();
			List<String> attachList = new LinkedList<String>();

			String subject = null;
			String textTemplate = null;
			String text = null;
			StringBuilder tos = new StringBuilder();
			StringBuilder ccs = new StringBuilder();
			StringBuilder bccs = new StringBuilder();

			Set<Entry<Object, Object>> ses = props.entrySet();
			for (Entry<Object, Object> entry : ses) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				if (key.equals(MAIL_PROP_SUBJECT)) {
					subject = value;
				} else if (key.equals(MAIL_PROP_TEXT_TEMPLATE)) {
					textTemplate = value;
				} else if (key.equals(MAIL_PROP_TEXT)) {
					text = value;
				} else if (key.startsWith(MAIL_PROP_TO)) {
					if (null != value && !value.trim().isEmpty()) {
						if (tos.length() == 0) {
							tos = new StringBuilder(value);
						} else {
							tos.append(MailInfo.SEPERATOR_COMMA).append(value);
						}
					}
				} else if (key.startsWith(MAIL_PROP_CC)) {
					if (null != value && !value.trim().isEmpty()) {
						if (ccs.length() == 0) {
							ccs = new StringBuilder(value);
						} else {
							ccs.append(MailInfo.SEPERATOR_COMMA).append(value);
						}
					}
				} else if (key.startsWith(MAIL_PROP_BCC)) {
					if (null != value && !value.trim().isEmpty()) {
						if (bccs.length() == 0) {
							bccs = new StringBuilder(value);
						} else {
							bccs.append(MailInfo.SEPERATOR_COMMA).append(value);
						}
					}
				} else if (key.startsWith(MAIL_PROP_ATTACH)) {
					if (null != value && !value.trim().isEmpty()) {
						attachList.add((value));
					}
				} else if (key.startsWith(MAIL_PROP_INLINE)) {
					if (null != value && !value.trim().isEmpty()) {
						String fkey = key.substring(MAIL_PROP_INLINE.length() + 1);
						inlineMap.put(fkey, value);
					}
				}
			}

			mi = new MailInfo(subject, textTemplate, text, tos.toString(), ccs.toString(),
					bccs.toString(), attachList, inlineMap);
		} catch (FileNotFoundException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}
		return mi;
	}

}
