package com.example.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.storage.model.FileM;

public interface FileRepository extends JpaRepository<FileM, Integer> {

}
