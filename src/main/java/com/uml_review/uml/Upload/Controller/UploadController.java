package com.uml_review.uml.Upload.Controller;

import com.uml_review.uml.Utils.FtpUtils;
import com.uml_review.uml.Utils.IdUtils;
import com.uml_review.uml.Utils.ResultUtil;
import lombok.Value;
import org.apache.catalina.User;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("upload")
public class UploadController {

    Map<String,Object> data = new HashMap<>();

    @RequestMapping("icon")
    public Object icon_img(
            @RequestParam("file")MultipartFile file,
            HttpServletRequest request
    )throws  Exception{
        data.clear();
        if(file.isEmpty()){
            return ResultUtil.error(505,"传入图片为空");
        }

        String filename =  IdUtils.genImageName()+file.getOriginalFilename();
       // System.out.println("/home/admin/icon/"+filename);
        byte[] buffer = new byte[(int)file.getSize()];
        file.getInputStream().read(buffer);

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("/home/admin/icon/"+filename));
        try{
        out.write(file.getBytes());
        out.flush();
        out.close();
        }catch (Exception e){
            return ResultUtil.error(500,"未知错误");
        }
        data.put("status","上传成功");
        data.put("filename",filename);
        return ResultUtil.success(data);
    }

    @RequestMapping("comment")
    public Object comment_img(
            @RequestParam("file")MultipartFile file,
            HttpServletRequest request
    )throws  Exception{
        data.clear();
        if(file.isEmpty()){
            return ResultUtil.error(505,"传入图片为空");
        }

        String filename =  IdUtils.genImageName()+file.getOriginalFilename();
        //System.out.println("/home/admin//"+filename);
        byte[] buffer = new byte[(int)file.getSize()];
        file.getInputStream().read(buffer);

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("/home/admin/comment/"+filename));
        try{
            out.write(file.getBytes());
            out.flush();
            out.close();
        }catch (Exception e){
            return ResultUtil.error(500,"未知错误");
        }
        data.put("status","上传成功");
        data.put("filename",filename);
        return ResultUtil.success(data);
    }


}



