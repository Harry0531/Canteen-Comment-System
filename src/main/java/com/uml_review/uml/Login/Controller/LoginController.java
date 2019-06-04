package com.uml_review.uml.Login.Controller;


import com.uml_review.uml.Annotation.PassToken;
import com.uml_review.uml.Annotation.UserLoginToken;
import com.uml_review.uml.Login.Entity.Key;
import com.uml_review.uml.Login.Entity.Login;
import com.uml_review.uml.Login.Entity.User;
import com.uml_review.uml.Login.Mapper.LoginMapper;
import com.uml_review.uml.Utils.MailUtils;
import com.uml_review.uml.Utils.ResultUtil;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    MailUtils mailUtils;

    @Autowired
    LoginMapper loginMapper;

    Map<String,Object> data = new HashMap<>();

    @PassToken
    @RequestMapping("verification_code")
    public  Object verification(
            @RequestParam("email")String email,
            HttpServletRequest request
    )throws  Exception{
        data.clear();
        String code = ((int)((Math.random()*9+1)*1000000))+"";
        String content="欢★迎★注★册★北★京★理★工★大★学★食★堂★点★评★系★统，您★的★验★证★码★为";
        mailUtils.sendHtmlMail(email,"这★是★一★封★验★证★邮★件",content+code);
        data.put("code",code);
        return ResultUtil.success(data);
    }

    @PassToken
    @RequestMapping("verification_username")
    public Object verification_username(
            @RequestParam("username")String username,
            HttpServletRequest request
    )throws Exception{
        data.clear();
        if(username == null)
            return ResultUtil.error(505,"用户名不能为空");

        Integer n =loginMapper.nameVeri(username);
        if(n>0){
            data.put("status","已存在");
        }
        else {
            data.put("status","可以使用");
        }
        return ResultUtil.success(data);
    }

    @PassToken
    @RequestMapping("register")
    public  Object register(
            @Valid Login param,
            HttpServletRequest request
    )throws Exception{
        data.clear();
        if(param == null ) return  ResultUtil.error(505,"传入参数不能为空");
        Integer n =loginMapper.register(param);
        if(n == 0) return ResultUtil.error(500,"注册失败");
        else {
            data.put("status","注册成功");
            data.put("user_id",n);
            return ResultUtil.success(data);
        }
    }

    @PassToken
    @RequestMapping("login")
    public Object login(
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            HttpServletRequest request
    )throws  Exception{
        data.clear();
        if(username==null || password ==null) return ResultUtil.error(505,"传入参数错误");
        Key key = loginMapper.login(username);
        if(key == null){
            data.put("result","账号或密码错误");
            return ResultUtil.success(data);
        }
        if(key.getPassword().equals(password)){
            User user  =new User();
            user.setUserId(String.valueOf(key.getUserId()));
            user.setUsername(username);
            user.setPassword(password);
            String token =user.getToken(user);

            data.put("token",token);
            data.put("result","登录成功");
            data.put("user_id",key.getUserId());
            return ResultUtil.success(data);
        }else{
            data.put("result","账号或密码错误");
            return ResultUtil.success(data);
        }
    }
}


