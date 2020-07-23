package com.example.storage.service;

import java.util.List;
import java.util.UUID;

import com.example.storage.model.FileM;


public interface FileService {
	FileM addFile(FileM file);
	List<FileM> getAllFiles();
	FileM updateFile(FileM file);
	FileM getFileById (Integer id);
	FileM getFileByUUID (UUID id);
	void deleteFile(FileM file);
}
