package io.egen.trucker.repository;

import java.util.List;

import io.egen.trucker.entity.Alert;

public interface AlertRepository {

	public Alert getHighAlert(String alertTime);

	public List<Alert> getAllAlertsByVin(String vin);

	public void setAlerts(String vin, String rule, String priority, String alertTimestamp);

	public List<String> getAlertsTimeList();
}
