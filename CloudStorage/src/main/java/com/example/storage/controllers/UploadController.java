package com.example.storage.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.storage.model.User;
import com.example.storage.service.UserService;

@Controller
public class UploadController {

	@Value("${cloud.folder.root}")
	private String cloudFolder;
	
	@GetMapping("/upload")
	public String uploadPage(Model model) {
		return "upload";
	}
	


	
//	private void listUser(List<User> users) {
//		for (User user : users) {
//			System.out.println(user.toString());
//		}
//	}
//	

	
	@PostMapping("/upload")
	public String sendFile(@RequestParam(name="uploadFile")MultipartFile file, Model model) throws IOException {
		var extension = FilenameUtils.getExtension(file.getOriginalFilename());
		var transferFile = File.createTempFile("inordic_", "_temp." + extension, new File(cloudFolder));
		//System.out.println(file.getOriginalFilename());
		
		file.transferTo(transferFile);
		System.out.println(transferFile.length()/1024.f);
		System.out.println(FileUtils.byteCountToDisplaySize(transferFile.length()));
//		@Autowired
//		listUser(UserService.getAllUsers());
		
		return "redirect:/home";
	}
}
