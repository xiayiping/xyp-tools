package xyp.tool.util;

/**
 * default intervalSecond = 1800L;<br/>
 * used for interval mails, prevent the tool from sending it too many within a very short time.
 * @author xiayip
 *
 */
public class TimeIntervalRecord {

	
	public TimeIntervalRecord() {
	}

	public TimeIntervalRecord(long intervalSecond) {
		this.intervalSecond = intervalSecond;
	}

	private long lastTimeMilliseconds = 0L;
	private long intervalSecond = 1800L;

	public long getIntervalSecond() {
		return intervalSecond;
	}

	public void setIntervalSecond(long intervalSecond) {
		this.intervalSecond = intervalSecond;
	}

	public long getIntervalTimeMilliseconds() {
		return intervalSecond * 1000;
	}

	public boolean isIntervalPassed(boolean resetLastTimeIfPassed) {
		long current = System.currentTimeMillis();
		if (current - lastTimeMilliseconds > intervalSecond * 1000) {
			if (resetLastTimeIfPassed) {
				lastTimeMilliseconds = current;
			}
			return true;
		}
		return false;
	}

}
