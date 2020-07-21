package com.example.storage.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {

	@GetMapping("/download")
	private ResponseEntity<byte[]> download() throws IOException{
		var path = Paths.get("C:\\Users\\smagi\\Desktop\\Cloud\\qwe.png");
		var content = Files.readAllBytes(path);
		
		var tika = new Tika();
		var mimeType = tika.detect(path);
		
		var httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; file=" + "qwe.png");
		httpHeaders.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		httpHeaders.add(HttpHeaders.PRAGMA, "no-cache");
		httpHeaders.add(HttpHeaders.EXPIRES, "0");
		
		
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.contentLength(content.length)
				.contentType(MediaType.parseMediaType(mimeType))
				.body(content);
		
	}
	
}