package com.tianqiauto.textile.weaving.controller.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("kindeditor")
public class KindEditorController {
    @Value("${com.shanghai.uploadpath}")
    private String rootPath_;

    @SuppressWarnings({"unchecked","rawtypes"})
    @RequestMapping("uploadfile")
    public String upload_json(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath()
                + "/";
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            String e = rootPath_ + "images/";
            String saveUrl = basePath + "images/";
            long maxSize = 102400000L;
            response.setContentType("text/html; charset=UTF-8");
            if (!ServletFileUpload.isMultipartContent(request)) {
                out.println(this.getError("请选择文件。"));
                throw new Exception("未选择上传文件！");
            }

            File uploadDir = new File(e);
            if (!uploadDir.isDirectory()) {
                uploadDir.mkdir();
            }

            if (!uploadDir.canWrite()) {
                out.println(this.getError("上传目录没有写权限。"));
                throw new Exception("上传目录没有写权限。");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String ymd = sdf.format(new Date());
            e = e + ymd + "/";
            saveUrl = saveUrl + ymd + "/";
            File dirFile = new File(e);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            HashMap extMap = new HashMap();
            extMap.put("image", "gif,jpg,jpeg,png,bmp");
            extMap.put("flash", "swf,flv");
            extMap.put("media",
                    "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");
            extMap.put("file",
                    "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
            String dirName = request.getParameter("dir");
            StandardMultipartHttpServletRequest mRequest = (StandardMultipartHttpServletRequest) request;
            Map map = mRequest.getFileMap();
            Collection c = map.values();
            Iterator itr = c.iterator();

            while (itr.hasNext()) {
                MultipartFile file = (MultipartFile) itr.next();
                if (!file.isEmpty()) {
                    long fileSize = file.getSize();
                    String fileName = file.getOriginalFilename();
                    String fileExt = fileName.substring(
                            fileName.lastIndexOf(".") + 1).toLowerCase();
                    if (fileSize > maxSize) {
                        out.println("上传文件大小超过限制");
                        return null;
                    }

                    if (!Arrays.asList(
                            ((String) extMap.get(dirName)).split(","))
                            .contains(fileExt)) {
                        out.println(this.getError("上传文件扩展名是不允许的扩展名。\n只允许"
                                + (String) extMap.get(dirName) + "格式。"));
                        return null;
                    }

                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newFileName = df.format(new Date()) + "_"
                            + (new Random()).nextInt(1000) + "." + fileExt;

                    try {
                        File obj = new File(e, newFileName);
                        file.transferTo(obj);
                    } catch (Exception arg26) {
                        out.println(this.getError("上传文件失败。"));
                        return null;
                    }

                    Map<String,Object> errMap = new HashMap<>();
                    errMap.put("error", Integer.valueOf(0));
                    errMap.put("url", saveUrl + newFileName);
                    errMap.put("dir", e+newFileName);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(errMap);
                    out.println(json);

                    /*JSONObject obj1 = new JSONObject();
                    obj1.put("error", Integer.valueOf(0));
                    obj1.put("url", saveUrl + newFileName);
                    obj1.put("dir", e+newFileName);
                    out.println(obj1.toJSONString());*/
                }
            }
        } catch (Exception arg27) {
            out.println("文件上传失败！");
        }

        return null;
    }

    /*public void setRoot(HttpServletRequest request) {
        if (rootPath_ == null) {
            rootPath_ = request.getSession().getServletContext()
                    .getRealPath("/");
        }

    }*/

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping({"fileManager"})
    public String file_manager_json(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();

        try {
            String e = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + e + "/";
            String rootPath = rootPath_ + "images/";
            String rootUrl = basePath + "images/";
            String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png",
                    "bmp"};
            String path = request.getParameter("path") != null ? request
                    .getParameter("path") : "";
            String currentPath = rootPath + path;
            String currentUrl = rootUrl + path;
            String moveupDirPath = "";
            String order;
            if (!"".equals(path)) {
                order = path.substring(0, path.length() - 1);
                moveupDirPath = order.lastIndexOf("/") >= 0 ? order.substring(
                        0, order.lastIndexOf("/") + 1) : "";
            }

            order = request.getParameter("order") != null ? request
                    .getParameter("order").toLowerCase() : "name";
            if (path.indexOf("..") >= 0) {
                out.println("Access is not allowed.");
                throw new Exception("Access is not allowed.");
            }

            if (!"".equals(path) && !path.endsWith("/")) {
                out.println("Parameter is not valid.");
                throw new Exception("Parameter is not valid.");
            }

            File currentPathFile = new File(currentPath);
            if (!currentPathFile.isDirectory()) {
                out.println("Directory does not exist.");
                throw new Exception("Directory does not exist.");
            }

            ArrayList fileList = new ArrayList();
            if (currentPathFile.listFiles() != null) {
                File[] arg20;
                int arg19 = (arg20 = currentPathFile.listFiles()).length;

                for (int arg18 = 0; arg18 < arg19; ++arg18) {
                    File file = arg20[arg18];
                    Hashtable hash = new Hashtable();
                    String fileName = file.getName();
                    if (file.isDirectory()) {
                        hash.put("is_dir", Boolean.valueOf(true));
                        hash.put("has_file",
                                Boolean.valueOf(file.listFiles() != null));
                        hash.put("filesize", Long.valueOf(0L));
                        hash.put("is_photo", Boolean.valueOf(false));
                        hash.put("filetype", "");
                    } else if (file.isFile()) {
                        String fileExt = fileName.substring(
                                fileName.lastIndexOf(".") + 1).toLowerCase();
                        hash.put("is_dir", Boolean.valueOf(false));
                        hash.put("has_file", Boolean.valueOf(false));
                        hash.put("filesize", Long.valueOf(file.length()));
                        hash.put("is_photo", Boolean.valueOf(Arrays.asList(
                                fileTypes).contains(fileExt)));
                        hash.put("filetype", fileExt);
                    }

                    hash.put("filename", fileName);
                    hash.put("datetime", (new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss")).format(Long.valueOf(file
                            .lastModified())));
                    fileList.add(hash);
                }
            }


            Map<String,Object> result = new HashMap<>();
            result.put("moveup_dir_path", moveupDirPath);
            result.put("current_dir_path", path);
            result.put("current_url", currentUrl);
            result.put("total_count", Integer.valueOf(fileList.size()));
            result.put("file_list", fileList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(result);
            out.println(json);

            /*JSONObject result = new JSONObject();
            result.put("moveup_dir_path", moveupDirPath);
            result.put("current_dir_path", path);
            result.put("current_url", currentUrl);
            result.put("total_count", Integer.valueOf(fileList.size()));
            result.put("file_list", fileList);
            out.println(result.toJSONString());*/
            response.setContentType("application/json; charset=UTF-8");
        } catch (Exception arg24) {
            arg24.printStackTrace();
        }

        out.flush();
        out.close();
        return null;
    }

    private String getError(String message) {

        Map<String,Object> result = new HashMap<>();
        result.put("error", Integer.valueOf(1));
        result.put("message", message);
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            String json = objectMapper.writeValueAsString(result);
            return json;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        /*JSONObject obj = new JSONObject();
        obj.put("error", Integer.valueOf(1));
        obj.put("message", message);
        return obj.toJSONString();*/
    }
}
