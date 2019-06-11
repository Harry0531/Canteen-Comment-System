package com.uml_review.uml.Menus.Controller;

import com.auth0.jwt.JWT;
import com.uml_review.uml.Like.Mapper.LikeMapper;
import com.uml_review.uml.Utils.Annotation.UserLoginToken;
import com.uml_review.uml.Menus.Entity.Dish;
import com.uml_review.uml.Menus.Entity.Str;
import com.uml_review.uml.Menus.Mapper.MenuMapper;
import com.uml_review.uml.Utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
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
            String token = request.getHeader("token");
            String userId= JWT.decode(token).getAudience().get(0);
            dish.setLike_me(0);
            List<Integer> a= likeMapper.query_like(Integer.parseInt(userId),1);

            for(int i:a){
                if(i == Integer.parseInt(userId)){
                    dish.setLike_me(1);
                    break;
                }
            }

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

        if(dish.getName() != null &&!dish.getName().equals("")) old.setName(dish.getName());
        if(dish.getAvatar() != null &&!dish.getAvatar().equals("")) old.setAvatar(dish.getAvatar());
        if(dish.getDiscription() != null &&!dish.getDiscription().equals("")) old.setDiscription(dish.getDiscription());
        if(dish.getLevel() != null) old.setLevel(dish.getLevel());
        if(dish.getLoc_Canteen() != null &&!dish.getLoc_Canteen().equals("")) old.setLoc_Canteen(dish.getLoc_Canteen());
        if(dish.getLoc_Floor() != null &&!dish.getLoc_Floor().equals("")) old.setLoc_Floor(dish.getLoc_Floor());
        if(dish.getLoc_Window() != null && !dish.getLoc_Window().equals("") ) old.setLoc_Window(dish.getLoc_Window());
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
    @RequestMapping(path="query",method = RequestMethod.POST )
    public Object dish_query(
            @RequestParam(value="keywords") String keywords,
            HttpServletRequest request
    )throws  Exception{
        String token = request.getHeader("token");

        Str s = new Str();
        s.setWords("%"+keywords+"%");
        List<Dish>dishes = menuMapper.query(s);
        if(dishes == null ){
            return ResultUtil.error(500,"未知错误");
        }else {
            dishes = dish_convert(dishes,token,1);
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


    @UserLoginToken
    @RequestMapping("recommand")
    public  Object dish_recommand(
            HttpServletRequest request
    )throws Exception{
        data.clear();
        String token = request.getHeader("token");

        long currentTime = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
        Date date = new Date(currentTime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Dish> ans= menuMapper.recommand(df.format(date));

        ans = dish_convert(ans,token,1);
        if(ans == null) return ResultUtil.error(500,"未知错误");
        else return ResultUtil.success(ans);
    }


    public List<Dish> dish_convert(List<Dish>old,String token,Integer type){

        String userId= JWT.decode(token).getAudience().get(0);
        List<Integer> b = likeMapper.query_like(Integer.parseInt(userId),type);

        Integer ios=0;
        for(Dish i:old){
            ios=1;
            for(Integer j:b){
                if(i.getDishId().equals(j)){
                    i.setLike_me(1);
                    ios=0;
                    break;
                }
            }
            if(ios == 1) i.setLike_me(0);
        }
        return  old;
    }
}
