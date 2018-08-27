package io.egen.trucker.repository;

import org.springframework.data.repository.CrudRepository;

import io.egen.trucker.entity.Tire;

public interface TireRepository extends CrudRepository<Tire, String> {

}
