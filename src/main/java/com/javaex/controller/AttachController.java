package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.AttachService;

@Controller
public class AttachController {
	
	@Autowired
	private AttachService attachService;

	//업로드폼
	@RequestMapping(value="/attach/uploadform", method= {RequestMethod.GET, RequestMethod.POST})
	public String uploadForm() {
		System.out.println("AttachController.uploadForm()");
		
		return "attach/form";
	}
	
	//업로드
	@RequestMapping(value="/attach/upload", method=RequestMethod.POST)
	public String upload(@RequestParam(value="file") MultipartFile file, Model model) {
		System.out.println("AttachController.upload()");
		
		System.out.println(file.getOriginalFilename());
		
		String saveName=attachService.exeUpload(file);
		model.addAttribute("saveName", saveName);
		
		return "attach/result";
	}

}
