package io.egen.trucker.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import io.egen.trucker.entity.Vehicle;
import io.egen.trucker.repository.VehicleRepository;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Vehicle create(Vehicle vehicle) {
		em.persist(vehicle);
		return vehicle;
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		TypedQuery<Vehicle> vquery = em.createNamedQuery("Vehicle.getAllVehicles", Vehicle.class);
		return vquery.getResultList();
	}

	@Override
	public Vehicle findById(String vin) {
		return em.find(Vehicle.class, vin);
	}

	@Override
	public Vehicle update(Vehicle vehicle) {
		em.merge(vehicle);
		return vehicle;
	}
}
