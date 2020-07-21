package com.example.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.storage.model.FileM;
import com.example.storage.repository.FileRepository;


@Service
public class FileServiceImpl implements FileService  {

    @Autowired
    private FileRepository fileRepository;
    
    @Override
	public FileM addFile(FileM file) {
		return fileRepository.saveAndFlush(file);
	}
    @Override
	public List<FileM> getAllFiles() {
		return fileRepository.findAll();
	}
    @Override
	public FileM updateFile(FileM file) {
		var updatedFile = fileRepository.saveAndFlush(file);
		return updatedFile;
	}
    @Override
	public void deleteFile(FileM file) {
		fileRepository.delete(file);
		
	}
	@Override
	public FileM getFileById(Integer id) {
		return fileRepository.getOne(id);
	}

}
