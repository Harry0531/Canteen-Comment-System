package com.uml_review.uml.Comment.Controller;

import com.auth0.jwt.JWT;
import com.uml_review.uml.Like.Mapper.LikeMapper;
import com.uml_review.uml.Utils.Annotation.UserLoginToken;
import com.uml_review.uml.Comment.Entity.Comment;
import com.uml_review.uml.Comment.Entity.TComment;
import com.uml_review.uml.Comment.Mapper.CommentMapper;
import com.uml_review.uml.Utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    LikeMapper likeMapper;
    Map<String,Object> data = new HashMap<>();

    @UserLoginToken
    @RequestMapping("add")
    public Object comment_Add(
            @Valid Comment comment,
            HttpServletRequest request
            )throws  Exception{
        data.clear();
        if(comment == null){
            return ResultUtil.error(505,"传入参数为空");
        }
        Integer n =commentMapper.add(comment);
        if(n==0) {
            return ResultUtil.error(500,"未知错误");
        }else{
            data.put("status","插入成功");
            data.put("comment_id",n);
            return ResultUtil.success(data);
        }
    }

    @UserLoginToken
    @RequestMapping("delete")
    public  Object comment_Delete(
            @RequestParam Integer userId,
            @RequestParam Integer commentId,
            HttpServletRequest request
            )throws  Exception{

        data.clear();
        Integer m=commentMapper.verification(userId,commentId);
        if(m==0){
            return ResultUtil.error(505,"传入参数不匹配");
        }else{
            m = commentMapper.delete(commentId,m);
            if(m==0){
                return ResultUtil.error(500,"未知错误");
            }else{
                data.put("status","删除成功");
                return ResultUtil.success(data);
            }
        }

    }

    @UserLoginToken
    @RequestMapping("query")
    public Object comment_Query(
            @RequestParam Integer status,
            @RequestParam Integer statusId,
            HttpServletRequest request
    )throws Exception{
        String token = request.getHeader("token");
        String userId= JWT.decode(token).getAudience().get(0);
        List<Integer> a = likeMapper.query_like(Integer.parseInt(userId),2);


        data.clear();
        List<TComment>result = null;
        if(statusId == null){
            return ResultUtil.error(505,"传入参数为空");
        }
        if(status ==1){
         result = commentMapper.query_User(statusId);
        if(result == null){
            return ResultUtil.error(500,"未知错误");
        }else{
            Integer ios=0;
            for(TComment i:result){
                ios=1;
                for(Integer z:a){
                    if(i.getCommentId().equals(z)){
                        i.setLike_me(1);
                        ios=0;
                        break;
                    }
                }
                if(ios == 1) i.setLike_me(0);
            }
            return ResultUtil.success(result);
        }

        }//按userId查找
        else if (status == 2){
        result = commentMapper.query_Dish(statusId);
            if(result == null){
                return ResultUtil.error(500,"未知错误");
            }else{
                Integer flag=0;
                for(TComment i:result){
                    flag=1;
                    for(Integer z:a){
                        if(i.getCommentId().equals(z)){
                            i.setLike_me(1);
                            flag=0;
                            break;
                        }
                    }
                    if(flag == 1) i.setLike_me(0);
                }
                return ResultUtil.success(result);
            }

        }//按dishId查找
        else{
            return ResultUtil.error(505,"状态码错误");
        }
    }
}
