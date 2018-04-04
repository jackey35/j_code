package com.jack.kxb.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jack.kxb.dao.KxQrRepository;
import com.jack.kxb.model.KxQr;

public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger("FileUtil");
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static List<String> allowedFile = Arrays.asList("application/zip");
	private static String QR_PATH="/usr/local/nginx/html/skin/qr/";
	//private static String QR_PATH="/Users/hejackey/Documents/test/";
	public static String uplaodFile(@RequestParam MultipartFile image) throws Exception {
		String fileName = image.getOriginalFilename();
		logger.info("Try to upload file, filename="+fileName+",filetype="+image.getContentType());
        if (image.isEmpty())
            throw new Exception("没有文件被上传！");
        if (!allowedFile.contains(image.getContentType().toLowerCase()))
            throw new Exception("不支持的文件类型");
        
        String fileUrl = QR_PATH+fileName;
        File saveFile = new File(fileUrl);
        
        FileUtils.writeByteArrayToFile(saveFile, image.getBytes());
        
        return QR_PATH+fileName;
	}
	
    /**文件读取缓冲区大小*/  
    private static final int CACHE_SIZE = 1024; 

/** 
     * 解压压缩包 解压至当前文件夹
     * @param zipFilePath 压缩文件路径 
     */  
    public static void unZip(String zipFilePath,KxQrRepository kxQrRepository) {  
        ZipFile zipFile = null;  
        File source = new File(zipFilePath);
        String destDir = source.getParent()+"/";
        try {  
            BufferedInputStream bis = null;  
            FileOutputStream fos = null;  
            BufferedOutputStream bos = null;  
            zipFile = new ZipFile(zipFilePath);  
            Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();  
            File file, parentFile;  
            ZipEntry entry;  
            byte[] cache = new byte[CACHE_SIZE];  
            while (zipEntries.hasMoreElements()) {  
                entry = (ZipEntry) zipEntries.nextElement();  
                if (entry.isDirectory()) {  
                    new File(destDir + entry.getName()).mkdirs();  
                    continue;  
                }  
                bis = new BufferedInputStream(zipFile.getInputStream(entry));  
                file = new File(destDir + entry.getName());  
                parentFile = file.getParentFile();  
                if (parentFile != null && (!parentFile.exists())) {  
                    parentFile.mkdirs();  
                }  
                fos = new FileOutputStream(file);  
                bos = new BufferedOutputStream(fos, CACHE_SIZE);  
                int readIndex = 0;  
                while ((readIndex = bis.read(cache, 0, CACHE_SIZE)) != -1) {  
                    fos.write(cache, 0, readIndex);  
                }  
                KxQr qr = new KxQr();
                qr.setQrUrl("http://www.jinxinsenhui.com/skin/qr/"+entry.getName());
                qr.setStatus(0);
                qr.setCreateDt(sdf.format(new Date()));
                kxQrRepository.save(qr);
                
                bos.flush();  
                bos.close();  
                fos.close();  
                bis.close();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                zipFile.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
}
