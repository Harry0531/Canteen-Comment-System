package com.uml_review.uml.Like.Controller;

import com.uml_review.uml.Like.Mapper.LikeMapper;
import com.uml_review.uml.Utils.Annotation.UserLoginToken;
import com.uml_review.uml.Utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("like")
public class LikeController {

    @Autowired
    LikeMapper likeMapper;

    Map<String ,Object> data = new HashMap<>();

    @UserLoginToken
    @RequestMapping("add")
    public  Object add_like(
            @RequestParam Integer userId,
            @RequestParam Integer type,
            @RequestParam Integer typeId,
            HttpServletRequest request
    )throws Exception{
        data.clear();
        if(userId == null || type ==null || typeId==null) return ResultUtil.error(505,"传入参数不正确");
        Integer n;
        if(type == 1){
            n=0;
            n= likeMapper.query_dish(userId,typeId);
            if(n!= null) {
                return ResultUtil.error(505, "已经点过赞了!");
            }else{
                n=likeMapper.add_like(userId,typeId,type);
                if(n==0) return ResultUtil.error(500,"未知错误");
                else{
                    likeMapper.update_dish_add(typeId);
                    data.put("like_id",n);
                    return  ResultUtil.success(data);
                }
            }
        }else if(type == 2){
            n=0;
            n=likeMapper.query_comment(userId,typeId);
            if(n!= null) {
                return ResultUtil.error(505, "已经点过赞了");
            }else{
                n=likeMapper.add_like(userId,typeId,type);
                if(n==0) return ResultUtil.error(500,"未知错误.");
                else{
                    data.put("like_id",n);
                    likeMapper.update_comment_add(typeId);
                    return  ResultUtil.success(data);
                }
            }
        }else{
            return ResultUtil.error(505,"type参数有误");
        }

    }

    @UserLoginToken
    @RequestMapping("cancel")
    public  Object cancel_like(
            @RequestParam Integer userId,
            @RequestParam Integer type,
            @RequestParam Integer typeId,
            HttpServletRequest request
    )throws  Exception{
        if(userId == null || type ==null || typeId==null) return ResultUtil.error(505,"传入参数不正确");
        Integer n;
        data.clear();

        if(type == 1){
            n=likeMapper.query_dish(userId,typeId);
            if(n == null) {
                return ResultUtil.error(505, "未点过赞");
            }else{
                n=0;
                n=likeMapper.delete_like(userId, typeId, type);
                if(n==0) return ResultUtil.error(500,"未知错误.");
                else{
                    likeMapper.update_dish_sub(typeId);
                    data.put("status","删除成功");
                    return  ResultUtil.success(data);
                }
            }
        }else if(type == 2){
            n=likeMapper.query_comment(userId,typeId);
            if(n == null) {
                return ResultUtil.error(505, "此餐品未点过赞");
            }else{
                n=0;
                n=likeMapper.delete_like(userId, typeId, type);
                if(n==0) return ResultUtil.error(500,"未知错误");
                else{
                    likeMapper.update_comment_sub(typeId);
                    data.put("status","删除成功");
                    return  ResultUtil.success(data);
                }
            }

        }else{
            return ResultUtil.error(505,"type参数有误");
        }
    }
}
