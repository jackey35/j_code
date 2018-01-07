package com.jack.fo.util;

import java.io.File;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger("FileUtil");
	private static List<String> allowedFile = Arrays.asList("image/jpeg", "image/gif", "image/png", "image/bmp","image/pjpeg","image/x-png");
	
	public static String uplaodFile(@RequestParam MultipartFile image,PayConfig payConfig,String fileName) throws Exception {
		logger.info("Try to upload file, filename="+image.getOriginalFilename()+",filetype="+image.getContentType());
        if (image.isEmpty())
            throw new Exception("没有文件被上传！");
        if (!allowedFile.contains(image.getContentType().toLowerCase()))
            throw new Exception("不支持的文件类型");
        
        logger.info("upload a screenshot!");
        String fileSuff = FilenameUtils.getExtension(image.getOriginalFilename());
        
        String filePath = payConfig.getFilePath();
        String fileUrl = filePath+fileName+"."+fileSuff;
        File saveFile = new File(fileUrl);
        int i = 0;
        while (saveFile.exists()) {
            saveFile = new File(filePath+fileName + (++i) + fileSuff);
        }
        FileUtils.writeByteArrayToFile(saveFile, image.getBytes());
        
        return fileName+"."+fileSuff;
	}
}
