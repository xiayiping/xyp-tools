package xyp.tool.util;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {

	private boolean caseSensitive = true;

	public StringComparator(boolean cs) {
		this.caseSensitive = cs;
	}

	@Override
	public int compare(String o1, String o2) {
		if (null == o1)
			return -1;
		if (null == o2)
			return 1;
		if (caseSensitive)
			return o1.compareTo(o2);
		else
			return o1.toLowerCase().compareTo(o2.toLowerCase());

	}

}
