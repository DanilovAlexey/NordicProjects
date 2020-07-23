package com.example.storage.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.storage.model.FileM;
import com.example.storage.service.FileService;
import com.example.storage.service.UserService;


@Controller
@RequestMapping("/file")
@Secured({"ROLE_admin", "ROLE_user"})
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
		//System.out.println(transferFile.length()/1024);
		//System.out.println(FileUtils.byteCountToDisplaySize(transferFile.length()));
		
		fileM.setUser(currUser);
		fileM.setFileName(transferFile.getName());
		fileM.setPath(transferFile.getCanonicalPath());
		fileM.setSize((int)transferFile.length()/1024);
		fileM.setFileUUID(UUID.randomUUID());
		
		fileService.addFile(fileM);
		
		return "redirect:/home";
	}
	

	@PostMapping("/delete/{id}")
	public String deleteFileById(@PathVariable(name = "id") Integer id) throws IOException {
		System.out.println(id);
		fileService.deleteFile(fileService.getFileById(id));
		//Files.delete(Paths.get(cloudFolder + File.separator + id));

		return "redirect:/home";
	}
		

}
