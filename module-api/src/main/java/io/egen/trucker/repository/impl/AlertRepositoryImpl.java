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

	public List<Alert> getAllHighAlerts() {
		TypedQuery<Alert> aquery = em.createNamedQuery("Alert.getAllHighAlerts", Alert.class);
		aquery.setParameter("paramPriority", "HIGH");
		return aquery.getResultList();
	}

	@Override
	public List<Alert> getAllAlertsByVin(String vin) {
		TypedQuery<Alert> aquery = em.createNamedQuery("Alert.getAllAlertsByVin", Alert.class);
		aquery.setParameter("paramVin", vin);
		return aquery.getResultList();
	}

	@Override
	public void setAlerts(String vin, String rule, String priority) {
		Alert alert = new Alert();
		alert.setVin(vin);
		alert.setRule(rule);
		alert.setPriority(priority);
		em.persist(alert);
	}
}
