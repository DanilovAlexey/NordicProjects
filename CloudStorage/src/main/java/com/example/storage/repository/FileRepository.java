package com.example.storage.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.storage.model.FileM;

public interface FileRepository extends JpaRepository<FileM, Integer> {
	FileM findByFileUUID(UUID id);
}
