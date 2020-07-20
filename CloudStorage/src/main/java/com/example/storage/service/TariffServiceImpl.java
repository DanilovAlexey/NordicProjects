package com.example.storage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.storage.model.Tariff;
import com.example.storage.repository.TariffRepository;

@Service
public class TariffServiceImpl implements TariffService {

	@Autowired
	private TariffRepository tariffRepository;
	
	@Override
	public List<Tariff> getAllTariffs() {
		return tariffRepository.findAll();
	}

	@Override
	public Tariff getTariffById(Integer id) {
		return tariffRepository.getOne(id);
	}

}
