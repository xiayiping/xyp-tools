package xyp.tool.mail;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MailInfo {
	public static final String SEPERATOR_COMMA = ",";
	public static final String SEPERATOR_SIMI_COMMA = ";";

	private String subject = null;
	private String textTemplate = null;
	private String text = null;

	private String tos = null;

	private String ccs = null;
	private String bccs = null;
	private List<String> attachFiles = new LinkedList<String>();
	private Map<String, String> inlineFiles = new HashMap<String, String>();

	public MailInfo(String subject, String textTemplate, String text, String tos, String ccs,
			String bccs, List<String> attachFiles, Map<String, String> inlineFiles) {
		super();
		this.subject = subject;
		this.textTemplate = textTemplate;
		this.text = text;
		this.tos = tos;
		this.ccs = ccs;
		this.bccs = bccs;

		this.attachFiles = attachFiles;
		this.inlineFiles = inlineFiles;
	}

	@Override
	public MailInfo clone() {
		List<String> attachFiles = new LinkedList<String>();
		attachFiles.addAll(this.attachFiles);

		Map<String, String> inlineFiles = new HashMap<String, String>();
		inlineFiles.putAll(inlineFiles);

		return new MailInfo(this.subject, this.textTemplate, this.text, this.tos, this.ccs,
				this.bccs, attachFiles, inlineFiles);
	}

	public String getSubject() {
		return subject;
	}

	public String getTextTemplate() {
		return textTemplate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	// public Map<String, Object> getVarMap() {
	// return varMap;
	// }

	public String getTos() {
		return tos;
	}

	public String getCcs() {
		return ccs;
	}

	public String getBccs() {
		return bccs;
	}

	public List<String> getAttachFiles() {
		return attachFiles;
	}

	public Map<String, String> getInlineFiles() {
		return inlineFiles;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(" ");
		sb.append("[subject=").append(subject).append("] ");
		sb.append("[text template=").append(textTemplate).append("] ");
		sb.append("[text=").append(text).append("] ");
		sb.append("[tos=").append(tos).append("] ");
		sb.append("[ccs=").append(ccs).append("] ");
		sb.append("[bccs=").append(bccs).append("] ");
		sb.append("[attach=");
		int count = 0;
		if (null != attachFiles) {
			for (String f : attachFiles) {
				if (count > 0) {
					count++;
					sb.append(SEPERATOR_COMMA);
				}
				sb.append(f);
			}
		}
		sb.append("] ");
		sb.append("[inline=");
		if (null != inlineFiles) {
			for (Entry<String, String> en : inlineFiles.entrySet()) {
				sb.append("{").append(en.getKey()).append("=").append(en.getValue()).append("} ");
			}
		}
		sb.append("] ");

		return sb.toString();
	}

}
