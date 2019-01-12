package cn.itrip.biz.controller;

import cn.itrip.beans.dto.Dto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequestMapping("/api/hotel")
@Api(tags = "hotel-controller")
public class HotelController {

    @ApiImplicitParam(name = "hotelId",value = "酒店id",paramType = "path")
    @ApiOperation(value = "根据酒店id查询酒店特色、商圈、酒店全名", httpMethod = "get",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "根据酒店id查询酒店特色、商圈、酒店全名(视频文字描述)" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码: </p>" +
            "<p>100214: 获取酒店视频文字描述失败</p>" +
            "<p>100215: 酒店id不能为空</p>")
    @RequestMapping(value = "/getvideodesc/{gotelId}",method = RequestMethod.GET)
    public @ResponseBody
    Dto getvideodesc(){
        return null;
    }

    @ApiImplicitParam(name = "type",value = "type",paramType = "path",dataType = "int")
    @ApiOperation(value = "查询热门城市", httpMethod = "get",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "查询国内、国外的热门城市（1.国内 2.国外）" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码: </p>" +
            "<p>100201: hotelId不能为空</p>" +
            "<p>100202: 系统异常，获取失败</p>")
    @RequestMapping(value = "/queryhotcity/{type}",method = RequestMethod.GET)
    public @ResponseBody
    Dto queryhotcity(){
        return null;
    }

    @ApiImplicitParam(name = "cityId",value = "cityId",paramType = "path",dataType = "long")
    @ApiOperation(value = "查询商圈", httpMethod = "get",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "根据城市查询商圈" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码: </p>" +
            "<p>100203: cityId不能为空</p>" +
            "<p>100204: 系统异常，获取失败</p>")
    @RequestMapping(value = "/querytradearea/{cityId}",method = RequestMethod.GET)
    public @ResponseBody
    Dto querytradearea(){
        return null;
    }

    @ApiOperation(value = "查询酒店特色列表", httpMethod = "get",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "查询酒店特色（用于查询页列表）" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码: </p>" +
            "<p>100205: 系统异常，获取失败</p>")
    @RequestMapping(value = "/queryhotelfeature",method = RequestMethod.GET)
    public @ResponseBody
    Dto queryhotelfeature(){
        return null;
    }
}
