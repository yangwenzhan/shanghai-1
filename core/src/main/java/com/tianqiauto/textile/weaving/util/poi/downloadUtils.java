package com.tianqiauto.textile.weaving.util.poi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Author bjw
 * @Date 2019/3/15 16:52
 */
@Slf4j
public class downloadUtils {

    public static void download(HttpServletResponse response,Resource resource){
        try(InputStream bis = new BufferedInputStream(new FileInputStream(resource.getFile()));BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());){
            String fileName = new String(resource.getFilename().getBytes("utf-8"),"ISO-8859-1" );
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("multipart/form-data");
            int len = 0;
            while((len = bis.read()) != -1){
                out.write(len);
                out.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("导出数据异常！",e);
        }
    }
}
