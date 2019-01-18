package cn.itrip.biz.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.beans.vo.userinfo.ItripSearchUserLinkUserVO;
import cn.itrip.biz.service.userlinkuser.ItripUserLinkUserService;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ValidationToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(value = "userinfo-controller")
@RequestMapping(value="/api/userinfo")
public class UserInfoController {

    @Resource
    private ItripUserLinkUserService itripUserLinkUserService;

    @Resource
    private ValidationToken validationToken;
    /**
     * 根据UserId,联系人姓名查询常用联系人-add by donghai
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询常用联系人接口", httpMethod = "POST",
            protocols = "HTTP",produces = "application/json",
            response = Dto.class,notes = "查询常用联系人信息(可根据联系人姓名进行模糊查询)"+
            "<p>若不根据联系人姓名进行查询，不输入参数即可 | 若根据联系人姓名进行查询，须进行相应的入参，比如：{\"linkUserName\":\"张三\"}</p>" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码：</p>"+
            "<p>100401 : 获取常用联系人信息失败 </p>"+
            "<p>100000 : token失效，请重登录</p>")
    @RequestMapping(value = "/queryuserlinkuser",method= RequestMethod.POST)
    @ResponseBody
    public Dto<ItripUserLinkUser> queryUserLinkUser(@RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO, HttpServletRequest request){
        String tokenString  = request.getHeader("token");
        ItripUser currentUser = validationToken.getCurrentUser(tokenString);
        List<ItripUserLinkUser> userLinkUserList = new ArrayList<ItripUserLinkUser>();
        String linkUserName = (null == itripSearchUserLinkUserVO)?null:itripSearchUserLinkUserVO.getLinkUserName();
        Dto dto = null;
        if(null != currentUser){
            Map param = new HashMap();
            param.put("userId", currentUser.getId());
            param.put("linkUserName", linkUserName);
            try {
                userLinkUserList = itripUserLinkUserService.getItripUserLinkUserListByMap(param);
                return DtoUtil.returnSuccess("获取常用联系人信息成功",userLinkUserList);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("获取常用联系人信息失败","100401");
            }
        }else{
            return DtoUtil.returnFail("token失效，请重新登录","100000");
        }
    }
}
