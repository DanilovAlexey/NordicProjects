package com.example.storage.service;

import java.util.List;

import com.example.storage.model.Tariff;

public interface TariffService {

	List<Tariff> getAllTariffs();
	Tariff getTariffById(Integer id);
}
