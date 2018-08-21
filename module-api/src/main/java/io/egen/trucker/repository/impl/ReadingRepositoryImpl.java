package io.egen.trucker.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import io.egen.trucker.entity.Reading;
import io.egen.trucker.repository.ReadingRepository;

@Repository
public class ReadingRepositoryImpl implements ReadingRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Reading create(Reading reading) {
		em.persist(reading);
		return reading;
	}

	@Override
	public List<Reading> getAllReadingsByVin(String vin) {
		TypedQuery<Reading> query = em.createNamedQuery("Reading.getAllReadingsByVin", Reading.class);
		query.setParameter("paramVin", vin);
		List<Reading> readingsList = query.getResultList();
		if (!readingsList.isEmpty()) {
			return readingsList;
		} else {
			return null;
		}
	}
}
