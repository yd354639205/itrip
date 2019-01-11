package cn.itrip.auth.controller;

import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.userinfo.ItripUserVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import com.wordnik.swagger.annotations.ApiModel;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@Api(tags = "user-controller",description = "userController")
@Controller
@RequestMapping("/api")
public class UserController {

    @Resource
    private UserService userService;

    @ApiIgnore
    @RequestMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "/doregister",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value="使用邮箱注册",httpMethod = "post",protocols = "http")
    public Dto doregister(@RequestBody ItripUserVO itripUserVO){
        //邮箱验证
        if (!this.validEmail(itripUserVO.getUserCode()))
            return DtoUtil.returnFail("请使用正确的邮箱", ErrorCode.AUTH_ILLEGAL_USERCODE);
        //调用service
        ItripUser user = new ItripUser();
        user.setUserCode(itripUserVO.getUserCode());
        user.setUserName(itripUserVO.getUserName());
        try {
            if (null!=userService.findUserByUserCode(user.getUserCode()))
                return DtoUtil.returnFail("用户已存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);
            else {
                //调用业务层createUser
                user.setUserPassword(MD5.getMd5(itripUserVO.getUserPassword(),32));
                userService.itriptxCreateUser(user);
                return DtoUtil.returnSuccess();
            }
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/registerbyphone",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value="使用手机注册",httpMethod = "post",protocols = "http")
    public Dto registerbyphone(@RequestBody ItripUserVO itripUserVO){
        //手机号验证
        if (!this.validPhone(itripUserVO.getUserCode()))
            return DtoUtil.returnFail("请使用正确的手机号", ErrorCode.AUTH_ILLEGAL_USERCODE);
        //调用service
        ItripUser user = new ItripUser();
        user.setUserCode(itripUserVO.getUserCode());
        user.setUserName(itripUserVO.getUserName());
        try {
            if (null!=userService.findUserByUserCode(user.getUserCode()))
                return DtoUtil.returnFail("用户已存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);
            else {
                //调用业务层createUser
                user.setUserPassword(MD5.getMd5(itripUserVO.getUserPassword(),32));
                userService.itriptxCreateUserByPhone(user);
                return DtoUtil.returnSuccess();
            }
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(),ErrorCode.AUTH_UNKNOWN);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/validatephone",method = RequestMethod.PUT,produces = "application/json")
    @ApiImplicitParams({ @ApiImplicitParam(required = true,name = "user",value = "手机号码",paramType = "query"),
            @ApiImplicitParam(required = true,name = "code",value = "验证码",paramType = "query")})
    @ApiOperation(value="手机注册短信验证",httpMethod = "put",protocols = "http")
    public Dto validatephone( String user
            ,String code){
        try{
            if (userService.vaildatePhone(user,code))
                return DtoUtil.returnSuccess("激活成功");
            else
                return DtoUtil.returnSuccess("激活失败");
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail("激活失败",ErrorCode.AUTH_UNKNOWN);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/activate",method = RequestMethod.PUT,produces = "application/json")
    @ApiImplicitParams({ @ApiImplicitParam(required = true,name = "user",value = "注册邮箱地址",paramType = "query"),
            @ApiImplicitParam(required = true,name = "code",value = "激活码",paramType = "query")})
    @ApiOperation(value="邮箱注册用户激活",httpMethod = "put",protocols = "http")
    public Dto activate(String user,String code){
        try{
            if (userService.activate(user,code))
                return DtoUtil.returnSuccess("激活成功");
            else
                return DtoUtil.returnSuccess("激活失败");
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail("激活失败",ErrorCode.AUTH_UNKNOWN);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ckusr",method = RequestMethod.GET,produces = "application/json")
    @ApiOperation(value="用户名验证",httpMethod = "get",protocols = "http")
    @ApiImplicitParam(required = true,name = "name",value = "被检查的用户名",paramType = "query")
    public Dto ckusr(String name){
        try{
            if (userService.findUserByUserCode(name)==null)
                return DtoUtil.returnSuccess("有效");
            else
                return DtoUtil.returnSuccess("用户名已存在");
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail("无效",ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**			 *
     * 合法E-mail地址：
     * 1. 必须包含一个并且只有一个符号“@”
     * 2. 第一个字符不得是“@”或者“.”
     * 3. 不允许出现“@.”或者.@
     * 4. 结尾不得是字符“@”或者“.”
     * 5. 允许“@”前的字符中出现“＋”
     * 6. 不允许“＋”在最前面，或者“＋@”
     */
    private boolean validEmail(String email){

        String regex="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"  ;
        return Pattern.compile(regex).matcher(email).find();
    }
    /**
     * 验证是否合法的手机号
     * @param phone
     * @return
     */
    private boolean validPhone(String phone) {
        String regex="^1[3578]{1}\\d{9}$";
        return Pattern.compile(regex).matcher(phone).find();
    }
}
