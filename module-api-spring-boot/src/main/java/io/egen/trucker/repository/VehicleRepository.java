package io.egen.trucker.repository;

import org.springframework.data.repository.CrudRepository;

import io.egen.trucker.entity.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

}
