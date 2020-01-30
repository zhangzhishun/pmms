package com.springboot.until;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author eternalSy
 * @version 1.0.0
 */
public class FileUtils {


    /**
     * @param file 文件
     * @param filePath 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static void upload(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static synchronized String createtFileName() {
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName= fmt.format(dt);
        return fileName;
    }
}
