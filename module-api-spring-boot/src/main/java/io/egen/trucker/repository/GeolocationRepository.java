package io.egen.trucker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.egen.trucker.entity.Geolocation;

public interface GeolocationRepository extends CrudRepository<Geolocation, String> {

	Optional<List<Geolocation>> findByVin(String vin);

}
