package com.example.storage.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
		var currUser = userService.getUser(authentication.getName());
		var currUserName = currUser.getUserName();
		// Формируем путь к папке пользователя
		File userDir = new File(cloudFolder + "\\" + currUserName.trim() + "_dir");
		// Создаем временный файл
        var transferFile = File.createTempFile("inordic_", "_temp." + extension, userDir);
        // Имя архива
		var zipFileName = FilenameUtils.removeExtension(transferFile.getName()) + ".zip";
		
		// Создаем папку
		if (userDir.mkdir()) {
            System.out.println("Создана новая папка");
        } else {
            System.out.println("Папка существует");
        }

        file.transferTo(transferFile);
        // Архивируем временный файл
		try (
			var fileOut = new FileOutputStream(new File(userDir, zipFileName));
			var zipFileOut = new ZipOutputStream(fileOut)) {
			
			var transferZipFile = new File(userDir, zipFileName);
			zipFileOut.putNextEntry(new ZipEntry(file.getOriginalFilename()));
			zipFileOut.write(Files.readAllBytes(Paths.get(transferFile.getPath())));
			zipFileOut.closeEntry();
			
			// Удаляем временный файл
			File tempFileInDir = new File(transferFile.getPath());
			tempFileInDir.delete();
			
			fileM.setUser(currUser);
			fileM.setFileName(transferZipFile.getName());
			fileM.setPath(transferZipFile.getCanonicalPath());
			fileM.setSize((int)transferZipFile.length()/1024);
			fileM.setFileUUID(UUID.randomUUID());
			
			// Добавляяем запись о загруженном файле в БД
			fileService.addFile(fileM);
		}	

		return "redirect:/home";
	}

	@PostMapping("/delete/{id}")
	public String deleteFileById(@PathVariable(name = "id") UUID id, Authentication authentication) throws IOException {
		var currUser = userService.getUser(authentication.getName());
		var currUserName = currUser.getUserName();
		var zipFileName = FilenameUtils.removeExtension(fileService.getFileByUUID(id).getFileName()) + ".zip";
		
		File fileInDir = new File(cloudFolder + "\\" + currUserName.trim() + "_dir", zipFileName);
		fileInDir.delete();
		
		fileService.deleteFile(fileService.getFileByUUID(id));

		return "redirect:/home";
	}
	
	@PostMapping("/update/{id}")
	public String changeFileUUID(@PathVariable(name = "id") UUID id) {
		
		var file = fileService.getFileByUUID(id);
		file.setFileUUID(UUID.randomUUID());
		
		fileService.updateFile(file);
		
		return "redirect:/home";
	}
		
}
