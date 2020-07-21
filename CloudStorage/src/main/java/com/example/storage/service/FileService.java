package com.example.storage.service;

import java.util.List;

import com.example.storage.model.FileM;


public interface FileService {
	FileM addFile(FileM file);
	List<FileM> getAllFiles();
	FileM updateFile(FileM file);
	void deleteFile(FileM file);
}
