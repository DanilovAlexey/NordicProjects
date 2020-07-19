package com.example.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.storage.model.Tariff;

public interface TariffRepository extends JpaRepository<Tariff, Integer> {

}
