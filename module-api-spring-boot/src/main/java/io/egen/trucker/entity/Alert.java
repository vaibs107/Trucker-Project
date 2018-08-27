package io.egen.trucker.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Alert {

	@Id
	private String alertId;

	private String priority;
	private String rule;
	private String vin;
	private String alertTimestamp;

	public Alert() {
		this.alertId = UUID.randomUUID().toString();
	}

	public String getAlertId() {
		return alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getAlertTimestamp() {
		return alertTimestamp;
	}

	public void setAlertTimestamp(String alertTimestamp) {
		this.alertTimestamp = alertTimestamp;
	}

	@Override
	public String toString() {
		return "Alert [alertId=" + alertId + ", priority=" + priority + ", rule=" + rule + ", vin=" + vin
				+ ", alertTimestamp=" + alertTimestamp + "]";
	}

}
