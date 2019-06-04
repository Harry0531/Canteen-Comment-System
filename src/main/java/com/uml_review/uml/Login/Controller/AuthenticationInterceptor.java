package com.uml_review.uml.Login.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.uml_review.uml.Annotation.PassToken;
import com.uml_review.uml.Annotation.UserLoginToken;
import com.uml_review.uml.Login.Entity.User;
import com.uml_review.uml.Login.Mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    LoginMapper loginMapper;

    @Override
    public  boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object object) throws Exception{
        String token = httpServletRequest.getHeader("token");
        //不是映射到方法直接跳过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method = handlerMethod.getMethod();
        //检查注解
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method .getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        //检查需要验证的注解
        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required()){
                //执行认证
                if(token == null){
                    throw new Exception("无token，请重新登录");
                }
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                }catch (JWTDecodeException e){
                    throw new Exception("401");
                }

                User user = loginMapper.findUserById(Integer.parseInt(userId));
                if(user == null) throw new Exception("用户不存在，请重新登录");

                JWTVerifier jwtVerifier =JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                }catch (JWTVerificationException e){
                        throw new Exception("401");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
