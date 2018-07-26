package io.egen.trucker.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import io.egen.trucker.entity.Tire;
import io.egen.trucker.repository.TireRepository;

@Repository
public class TireRepositoryImpl implements TireRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Tire tire) {
		em.persist(tire);
	}
}
