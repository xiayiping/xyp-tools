package xyp.tool.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xyp.tool.velocity.VelocityEngineWrapper;

public class MyMailSender {
	private static Log logger = LogFactory.getLog(MyMailSender.class);
	/*
	 * mail.store.protocol=pop3 mail.transport.protocol=smtp
	 * mail.host=smtp.qq.com mail.from=summer_xyp@qq.com
	 * mail.user=summer_xyp@qq.com mail.password=5425054250
	 */
	public static final String PROP_FROM = "mail.from";
	public static final String PROP_USER = "mail.user";
	public static final String PROP_PASSWORD = "mail.password";
	public static final String PROP_HOST = "mail.host";
	public static final String PROP_HOST_PORT = "mail.host.port";
	public static final String PROP_STORE_PROTOCAL = "mail.store.protocol";
	public static final String PROP_TRANSPORT_PROTOCAL = "mail.transport.protocol";

	String from = null;
	String host = null;
	String username = null;
	String password = null;
	int port = 25;

	Session session = null;

	VelocityEngineWrapper velocityEnginWrapper = null;

	private MyMailSender(Properties props, VelocityEngineWrapper vew) {
		session = Session.getInstance(props);
		from = session.getProperty(PROP_FROM);
		host = session.getProperty(PROP_HOST);
		username = session.getProperty(PROP_USER);
		password = session.getProperty(PROP_PASSWORD);
		try {
			port = Integer.parseInt(session.getProperty(PROP_HOST_PORT));
		} catch (Exception e) {
			logger.error(e, e);
		}
		this.velocityEnginWrapper = vew;
	}

	public static MyMailSender newInstance(File properties, VelocityEngineWrapper vew)
			throws FileNotFoundException, IOException {
		FileInputStream fi = null;
		try {
			Properties props = new Properties();
			fi = new FileInputStream(properties);
			props.load(fi);

			return new MyMailSender(props, vew);
		} finally {
			fi.close();
		}
	}

	public void sendMail(MailInfo mailInfo, Map<String, Object> varMap) throws AddressException,
			MessagingException {
		Message msg = getMessage(mailInfo, varMap);
		Transport transport = session.getTransport();
		transport.connect(host, port, username, password);

		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}

	public void sendMails(List<MailInfo> mailInfos, List<Map<String, Object>> varMaps)
			throws AddressException, MessagingException {
		if (null == mailInfos || null == varMaps || mailInfos.size() != varMaps.size()) {
			throw new IllegalArgumentException("mail info and var map list size not match");
		}
		LinkedList<Message> msgs = new LinkedList<Message>();
		Iterator<MailInfo> infoIter = mailInfos.iterator();
		Iterator<Map<String, Object>> varIter = varMaps.iterator();
		while (infoIter.hasNext()) {
			MailInfo mi = infoIter.next();
			Map<String, Object> var = varIter.next();
			Message msg = getMessage(mi, var);
			msgs.addLast(msg);
		}

		Transport transport = session.getTransport();
		transport.connect(host, port, username, password);
		for (Message msg : msgs) {
			transport.sendMessage(msg, msg.getAllRecipients());
		}
		transport.close();

	}

	private Message getMessage(MailInfo mailInfo, Map<String, Object> varMap)
			throws AddressException, MessagingException {
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setSubject(mailInfo.getSubject());
		if (null != mailInfo.getTos()) {
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailInfo.getTos(), false));
		}
		if (null != mailInfo.getCcs()) {
			msg.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(mailInfo.getCcs(), false));
		}
		if (null != mailInfo.getBccs()) {
			msg.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(mailInfo.getBccs(), false));
		}
		MimeMultipart multiPart = new MimeMultipart();

		MimeBodyPart contentBody = new MimeBodyPart();
		if (null != mailInfo.getTextTemplate()) {
			String content = velocityEnginWrapper.mergeTemplate(mailInfo.getTextTemplate(), varMap);
			contentBody.setContent(content, "text/html;charset=UTF-8");
		} else {
			contentBody.setText(mailInfo.getText());
		}

		multiPart.addBodyPart(contentBody);

		if (null != mailInfo.getAttachFiles()) {
			for (String fpath : mailInfo.getAttachFiles()) {
				File file = new File(fpath);
				if (file.exists() && !file.isDirectory()) {
					MimeBodyPart attachPart = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(file);
					attachPart.setDataHandler(new DataHandler(fds));
					attachPart.setDisposition(MimeBodyPart.ATTACHMENT);
					attachPart.setFileName(file.getName());
					multiPart.addBodyPart(attachPart);
				}
			}
		}

		if (null != mailInfo.getInlineFiles()) {
			Set<Entry<String, String>> entset = mailInfo.getInlineFiles().entrySet();
			for (Entry<String, String> ent : entset) {
				String keyStr = ent.getKey();
				String keyVal = ent.getValue();
				if (null != keyVal) {
					File file = new File(keyVal);
					if (file.exists() && !file.isDirectory()) {
						MimeBodyPart inlinePart = new MimeBodyPart();
						FileDataSource fds3 = new FileDataSource(file);
						inlinePart.setHeader("Content-ID", "<" + keyStr + ">");
						inlinePart.setDataHandler(new DataHandler(fds3));
						inlinePart.setDisposition(MimeBodyPart.INLINE);
						multiPart.addBodyPart(inlinePart);
					}
				}
			}
		}

		msg.setContent(multiPart);
		return msg;
	}
}
