package io.egen.trucker.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import io.egen.trucker.entity.Alert;
import io.egen.trucker.repository.AlertRepository;

@Repository
public class AlertRepositoryImpl implements AlertRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Alert getHighAlert(String alertTime) {
		// System.out.println("In AlertRepositoryImpl, alertTime: " + alertTime);
		TypedQuery<Alert> aquery = em.createNamedQuery("Alert.getHighAlert", Alert.class);
		aquery.setParameter("paramPriority", "HIGH");
		aquery.setParameter("paramTimestamp", alertTime);
		return aquery.getSingleResult();
	}

	@Override
	public List<Alert> getAllAlertsByVin(String vin) {
		TypedQuery<Alert> aquery = em.createNamedQuery("Alert.getAllAlertsByVin", Alert.class);
		aquery.setParameter("paramVin", vin);
		return aquery.getResultList();
	}

	@Override
	public void setAlerts(String vin, String rule, String priority, String alertTimestamp) {
		Alert alert = new Alert();
		alert.setVin(vin);
		alert.setRule(rule);
		alert.setPriority(priority);
		alert.setAlertTimestamp(alertTimestamp);
		em.persist(alert);
	}

	@Override
	public List<String> getAlertsTimeList() {
		TypedQuery<String> alertTimeQuery = em.createNamedQuery("Alert.getAlertsTimeList", String.class);
		return alertTimeQuery.getResultList();
	}
}
