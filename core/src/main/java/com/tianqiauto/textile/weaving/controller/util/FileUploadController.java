package com.tianqiauto.textile.weaving.controller.util;

import com.tianqiauto.textile.weaving.model.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * @ClassName FileUploadController
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-19 08:51
 * @Version 1.0
 **/

@RestController
@RequestMapping("file")
public class FileUploadController {


    @PostMapping
    public String upload(MultipartFile file,HttpServletRequest request){

        try {
            String uploadPath = request.getServletContext().getRealPath("/upload");
            File localFile = new File(uploadPath,new Date().getTime()+".txt");
            if(!localFile.getParentFile().exists()){
                localFile.getParentFile().mkdirs();
                localFile.createNewFile();
            }
            file.transferTo(localFile);
            return "上传成功";
        }catch (Exception e){
            return "上传失败:"+e.getMessage();
        }
    }

    @GetMapping("{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)throws Exception{
        String uploadPath = request.getServletContext().getRealPath("/upload");

        try (InputStream inputStream = new FileInputStream(new File(uploadPath,id+".txt"));   //jdk1.7新特性，自动关闭流
             OutputStream outputStream = response.getOutputStream();){


            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename=Hello.txt");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();

        }
    }


}
