package com.secsc.controllers;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.secsc.entity.UploadRecord;
import com.secsc.exception.EmptyUploadFileException;
import com.secsc.mapper.UploadMapper;
import com.secsc.security.AuthenticationInfo;



@RestController
@RequestMapping("/upload")
public class UploadController {
	
	@Resource
	private UploadMapper uploadMapper;

	@Resource(name="authInfo")
	private AuthenticationInfo authenticationInfo;

	@RequestMapping(value = "/singleUpload", method = RequestMethod.PUT)
	public List<UploadRecord> upload(@RequestParam("file") MultipartFile file,
			@RequestParam("uploadTarget") String uploadTarget,
			HttpServletRequest request) throws Exception {
		UploadRecord record = new UploadRecord();
		String username=authenticationInfo.getUserDetails().getUsername();
		if (file.isEmpty()) {
			throw new EmptyUploadFileException("Upload File can't be empty");
		} else {
			record.setOriginalName(file.getOriginalFilename());
			record.setFileType(getExtensionName(file.getOriginalFilename()));
			record.setUuid(UUID.randomUUID().toString().replace("-", ""));
			record.setUsername(username);
			record.setUploadTarget(uploadTarget);
			record.setUploadDateTime(LocalDateTime.now());
			record.setFileSize(file.getSize());

			String projectPath = request.getSession().getServletContext()
					.getRealPath("/");
			String path = "/WEB-INF/upload/";
			path = path + record.getUuid() + record.getFileType();

			record.setFilePath(path);

			file.transferTo(new File(projectPath + path));

			uploadMapper.insertUploadRecord(record);
		}
		return (List<UploadRecord>) uploadMapper.getUploadRecords(record);
	}
	
	
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public List<UploadRecord> getUploadRecords(UploadRecord uploadRecord){
		String username=authenticationInfo.getUserDetails().getUsername();
		uploadRecord.setUsername(username);
		return uploadMapper.getUploadRecords(uploadRecord);
		
	}
	
	//获取后缀名
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				filename = filename.substring(dot);
			}
		}
		return filename;
	}
}
