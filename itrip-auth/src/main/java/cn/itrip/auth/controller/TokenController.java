package cn.itrip.auth.controller;

import cn.itrip.auth.service.TokenService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Api(tags = "tokenr-controller",description = "tokenController")
@Controller
@RequestMapping("/api")
public class TokenController {

    @Resource
    private TokenService tokenService;

    @ApiImplicitParam(name = "token",value = "用户认证凭据",paramType = "header",required = true)
    @RequestMapping(value = "retoken",method = RequestMethod.POST,produces= "application/json",headers = "token")
    @ApiOperation(value = "客户端置换token")
    public @ResponseBody
    Dto retoken(HttpServletRequest request){
        String token;
        try {
            token = tokenService.reloadToken(request.getHeader("user-agent"),request.getHeader("token"));
            ItripTokenVO vo = new ItripTokenVO(token,
                    Calendar.getInstance().getTimeInMillis()+2*60*60*1000,
                    Calendar.getInstance().getTimeInMillis());
            return DtoUtil.returnDataSuccess(vo);
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail(e.getLocalizedMessage(), ErrorCode.AUTH_UNKNOWN);
        }
    }
}
