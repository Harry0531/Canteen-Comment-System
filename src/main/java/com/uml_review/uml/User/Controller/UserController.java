package com.uml_review.uml.User.Controller;

import com.auth0.jwt.JWT;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.uml_review.uml.User.Entity.User;
import com.uml_review.uml.User.Mapper.UserMapper;
import com.uml_review.uml.Utils.Annotation.UserLoginToken;
import com.uml_review.uml.Utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserMapper userMapper;
    Map<String ,Object> data = new HashMap<>();

    @UserLoginToken
    @RequestMapping("info")
    public  Object user_info(
            @RequestParam("userId") Integer userId,
            HttpServletRequest request
    )throws  Exception{
        data.clear();
        String token = request.getHeader("token");
        String Id = JWT.decode(token).getAudience().get(0);
        User user = null;
        if(userId == null){
            user = userMapper.user_info(Integer.parseInt(Id));
            if(user ==null ) return ResultUtil.error(500,"未知错误");
            else  return  ResultUtil.success(user);
        }else{
            user = userMapper.user_info(userId);
            user = userMapper.user_info(Integer.parseInt(Id));
            if(user ==null ) return ResultUtil.error(500,"未知错误");
            else  return ResultUtil.success(user);
        }
    }

    @UserLoginToken
    @RequestMapping("update")
    public Object user_update(
//            @RequestParam("userId")Integer userId,
//            @RequestParam("nickname")String nickname,
//            @RequestParam("avatar")String avatar,
//            @RequestParam("motto")String motto,
            @Valid User user_old,
            HttpServletRequest request
    )throws  Exception{
        User user =userMapper.user_info(user_old.getUserId());
        if(user_old.getNickname() != null) user.setNickname(user_old.getNickname());
        if(user_old.getAvatar() != null) user.setAvatar(user_old.getAvatar());
        if(user_old.getMotto() != null) user.setMotto(user_old.getMotto());
        Integer m = null;
        m = userMapper.user_Update(user);
        if(m ==null ) return ResultUtil.error(500,"未知错误");
        else {
            data.clear();
            data.put("status","更新成功");
            return ResultUtil.success(data);
        }

    }

}
