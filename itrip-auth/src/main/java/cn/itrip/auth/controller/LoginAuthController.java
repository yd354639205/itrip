package cn.itrip.auth.controller;

import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.beans.vo.userinfo.ItripUserVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Api(tags = "loginController",description = "登录controller")
@Controller
@RequestMapping("/api")
public class LoginAuthController {

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "/dologin",method = RequestMethod.POST,produces= "application/json")
    @ApiOperation(value="登录",httpMethod = "post",protocols = "http")
    @ApiImplicitParams({ @ApiImplicitParam(required = true,name = "name" ,value = "用户名",paramType = "form"),
            @ApiImplicitParam(required = true,name = "password",value = "密码",paramType = "form")})
    public @ResponseBody
    Dto tologin(String name, String password,HttpServletRequest request){
        try{
            ItripUser user = userService.login(name, MD5.getMd5(password,32));
            if (EmptyUtils.isNotEmpty(user)){
                String userAgent = request.getHeader("user-agent");
                String token = tokenService.generateToken(userAgent,user);
                tokenService.save(token,user);
                ItripTokenVO vo  = new ItripTokenVO(token,Calendar.getInstance().getTimeInMillis()+2*60*60*1000,
                        Calendar.getInstance().getTimeInMillis());
                return DtoUtil.returnDataSuccess(vo);
            }else{
                return DtoUtil.returnFail("用户名或密码错误", ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_AUTHENTICATION_FAILED);
        }
    }

    @RequestMapping(value="logout",method = RequestMethod.GET,produces="application/json",headers="token")
    @ApiOperation(value = "用户注销")
    @ApiImplicitParam(required = true,name = "token",value = "用户认证凭证",paramType = "header")
    public @ResponseBody Dto logout(HttpServletRequest request){
        String token = request.getHeader("token");
        try{
            if (tokenService.vaildate(request.getHeader("user-agent"),token)) {
                tokenService.delete(token);
                return DtoUtil.returnSuccess();
            }else
                return DtoUtil.returnFail("token无效",ErrorCode.AUTH_TOKEN_INVALID);
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail("退出失败",ErrorCode.AUTH_TOKEN_INVALID);
        }
    }
}
