package com.springboot.controller;

import com.springboot.until.httpResult.HttpResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @CrossOrigin 实现跨域访问
 * @author eternalSy
 * @version 1.0.0
 */
@Controller
@CrossOrigin
@ResponseBody
public class FileDownloadController {

    /**
     * 文件下载相关代码
     * */
    @RequestMapping("download/{fileName}")
    public Object downloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName) {
        //String fileName = "20200130125337252.docx";// 设置文件名，根据业务需要替换成要下载的文件名
        System.out.println("开始下载文件");
        System.out.println(fileName);
        if (fileName != null) {
            //设置文件路径
            String realPath = "F:\\pmms\\";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return new HttpResult(1);
    }

}
