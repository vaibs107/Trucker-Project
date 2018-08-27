package io.egen.trucker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.egen.trucker.entity.Tire;
import io.egen.trucker.repository.TireRepository;
import io.egen.trucker.service.TireService;

@Service
public class TireServiceImpl implements TireService {

	@Autowired
	private TireRepository trepository;

	public void create(Tire tire) {
		trepository.save(tire);
	}

}
