package com.rogersamson.entity;

import lombok.Data;

@Data
public class SessionEntity implements Comparable<SessionEntity> {
	private String title;
	private int priority;
	private String startTime;
	private String endTime;
	private String speaker;
	private int vip;

	@Override //Compare VIP, PRIORITY, START TIME, TITLE
	public int compareTo(SessionEntity other) {

		if (Integer.compare(other.getVip(), this.getVip()) != 0) {
			return Integer.compare(other.getVip(), this.getVip());
		}

		if (Integer.compare(other.getPriority(), this.getPriority()) != 0) {
			return Integer.compare(this.getPriority(), other.getPriority());
		}

		if (!startTime.equalsIgnoreCase(other.getStartTime())) {
			return this.getStartTime().compareTo(other.getStartTime());
		}

		return this.getTitle().compareTo(other.getTitle());
	}

}
