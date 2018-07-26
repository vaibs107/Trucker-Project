package io.egen.trucker.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import io.egen.trucker.entity.Geolocation;
import io.egen.trucker.repository.GeolocationRepository;

@Repository
public class GeolocationRepositoryImpl implements GeolocationRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Geolocation> getAllGeolocationsByVin(String vin) {
		TypedQuery<Geolocation> gquery = em.createNamedQuery("Geolocation.getAllGeolocationsByVin", Geolocation.class);
		gquery.setParameter("paramVin", vin);
		return gquery.getResultList();
	}

	@Override
	public void setGeolocations(String formattedAddress, String vin) {
		Geolocation location = new Geolocation();
		location.setFormattedAddress(formattedAddress);
		location.setVin(vin);
		em.persist(location);
	}

}
