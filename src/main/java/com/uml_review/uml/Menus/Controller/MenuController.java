package com.uml_review.uml.Menus.Controller;

import com.auth0.jwt.JWT;
import com.uml_review.uml.Like.Mapper.LikeMapper;
import com.uml_review.uml.Utils.Annotation.UserLoginToken;
import com.uml_review.uml.Menus.Entity.Dish;
import com.uml_review.uml.Menus.Entity.Str;
import com.uml_review.uml.Menus.Mapper.MenuMapper;
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
@RequestMapping("menu")
public class MenuController {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    LikeMapper likeMapper;

    Map<String,Object> data = new HashMap<>();

    @UserLoginToken
    @RequestMapping("add")
    public Object dish_Add(
            @Valid Dish dish,
            HttpServletRequest request
    )throws Exception{
        data.clear();
        if(dish == null){
            return ResultUtil.error(505,"传入参数为空");
        }
        Integer n = menuMapper.add(dish);
        if(n==0) {
            return ResultUtil.error(500,"未知错误");
        }else{
            data.put("status","插入成功");
            data.put("dish_id",n);
            return ResultUtil.success(data);
        }
    }

    @UserLoginToken
    @RequestMapping("info")
    public Object dish_Info(
            @RequestParam Integer dishId,
            HttpServletRequest request
    )throws  Exception{
        if(dishId ==null){
            return ResultUtil.error(505,"传入参数为空");
        }
        Dish dish = menuMapper.info(dishId);

        if(dish==null) {
            return ResultUtil.error(500,"未知错误");
        }else{
            return ResultUtil.success(dish);
        }
    }

    @UserLoginToken
    @RequestMapping("update")
    public Object dish_Update(
            @Valid Dish dish,
            HttpServletRequest request
    )throws Exception{
        data.clear();
        if(dish == null){
            return ResultUtil.error(505,"传入参数为空");
        }
        Dish old=menuMapper.info(dish.getDishId());

        if(dish.getName() != null) old.setName(dish.getName());
        if(dish.getAvatar() != null) old.setAvatar(dish.getAvatar());
        if(dish.getDiscription() != null) old.setDiscription(dish.getDiscription());
        if(dish.getLevel() != null) old.setLevel(dish.getLevel());
        if(dish.getLoc_Canteen() != null) old.setLoc_Canteen(dish.getLoc_Canteen());
        if(dish.getLoc_Floor() != null) old.setLoc_Floor(dish.getLoc_Floor());
        if(dish.getLoc_Window() != null) old.setLoc_Window(dish.getLoc_Window());
        if(dish.getPrice() != null) old.setPrice(dish.getPrice());

        Integer m=menuMapper.update(old);
        if(m==0) {
            return ResultUtil.error(500,"未知错误");
        }else{
            data.put("status","更新成功");
            return ResultUtil.success(data);
        }
    }

    @UserLoginToken
    @RequestMapping("query")
    public Object dish_query(
            @RequestParam(value="keywords") String keywords,
            HttpServletRequest request
    )throws  Exception{
        String token = request.getHeader("token");
        String userId= JWT.decode(token).getAudience().get(0);

        List<Integer> a = likeMapper.query_like(Integer.parseInt(userId),1);

        Str s = new Str();
        s.setWords("%"+keywords+"%");
        List<Dish>dishes = menuMapper.query(s);
        if(dishes == null ){
            return ResultUtil.error(500,"未知错误");
        }else {
            Integer flag=0;
            for(Dish i:dishes){
                flag=1;
                for(Integer j:a){
                    if(i.getDishId().equals(j)){
                        i.setLike_me(1);
                        flag=0;
                        break;
                    }
                }
                if(flag == 1) i.setLike_me(0);
            }
            return ResultUtil.success(dishes);
        }
    }

    @UserLoginToken
    @RequestMapping("delete")
    public  Object dish_Delete(
            @RequestParam Integer dishId,
            HttpServletRequest request
    )throws  Exception{
        data.clear();
        Integer m = menuMapper.dish_delete(dishId);
        if(m==0){
                return ResultUtil.error(500,"未知错误");
            }else{
                data.put("status","删除成功");
                return ResultUtil.success(data);
            }
        }
}
