package com.example.storage.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.storage.model.FileM;
import com.example.storage.service.FileService;
import com.example.storage.service.UserService;


@Controller
public class UploadController {
	
	@Autowired
	@Qualifier("fileServiceImpl")
	private FileService fileService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Value("${cloud.folder.root}")
	private String cloudFolder;
	
	@GetMapping("/upload")
	public String uploadPage(Model model) {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String sendFile(@RequestParam(name="uploadFile")MultipartFile file, Model model, @ModelAttribute FileM fileM, Authentication authentication) throws IOException {
		var extension = FilenameUtils.getExtension(file.getOriginalFilename());
		var transferFile = File.createTempFile("inordic_", "_temp." + extension, new File(cloudFolder));
		var currUser = userService.getUser(authentication.getName());
		
		file.transferTo(transferFile);
		System.out.println(transferFile.length()/1024);
		//System.out.println(FileUtils.byteCountToDisplaySize(transferFile.length()));
		
		fileM.setUser(currUser);
		fileM.setFileName(transferFile.getName());
		fileM.setPath(transferFile.getCanonicalPath());
		fileM.setSize((int)transferFile.length()/1024);
		
		fileService.addFile(fileM);
		
		return "redirect:/home";
	}
	

}
