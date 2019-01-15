package cn.itrip.biz.controller;

import cn.itrip.beans.HotelVideoDescVO;
import cn.itrip.beans.ItripSearchDetailsHotelVO;
import cn.itrip.beans.ItripSearchFacilitiesHotelVO;
import cn.itrip.beans.ItripSearchPolicyHotelVO;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.beans.vo.ItripAreaDicVO;
import cn.itrip.biz.service.hotel.ItripHotelService;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/hotel")
@Api(tags = "hotel-controller")
public class HotelController {

    @Resource
    private ItripHotelService itripHotelService;

    @ApiImplicitParam(name = "hotelId",value = "酒店id",paramType = "path")
    @ApiOperation(value = "根据酒店id查询酒店特色、商圈、酒店全名", httpMethod = "get")
    @RequestMapping(value = "/getvideodesc/{hotelId}",method = RequestMethod.GET)
    public @ResponseBody
    Dto getvideodesc(@PathVariable String hotelId){
        Dto<Object> dto = new Dto<>();
        if (null!=hotelId&&!"".equals(hotelId)){
            HotelVideoDescVO vo = null;
            try {
                vo = itripHotelService.getVideoDescByHotelId(Long.valueOf(hotelId));
                dto = DtoUtil.returnSuccess("获取酒店视频文字描述成功",vo);
            }catch (Exception e){
                e.printStackTrace();
                dto = DtoUtil.returnFail("获取酒店视频文字描述失败","100214");
            }
        }else {
            dto = DtoUtil.returnFail("酒店id不能为空","100215");
        }
        return dto;
    }

    @ApiOperation(value = "根据酒店id查询酒店特色和介绍", httpMethod = "GET")
    @RequestMapping(value = "/queryhoteldetails/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Dto<ItripSearchFacilitiesHotelVO> queryHotelDetails(
            @ApiParam(required = true, name = "id", value = "酒店ID")
            @PathVariable Long id) {
        List<ItripSearchDetailsHotelVO> itripSearchDetailsHotelVOList = null;
        try {
            if (EmptyUtils.isNotEmpty(id)) {
                itripSearchDetailsHotelVOList = itripHotelService.queryHotelDetails(id);
                return DtoUtil.returnDataSuccess(itripSearchDetailsHotelVOList);
            } else {
                return DtoUtil.returnFail("酒店id不能为空", "10210");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", "10211");
        }
    }

    @ApiOperation(value = "根据酒店id查询酒店政策", httpMethod = "GET")
    @RequestMapping(value = "/queryhotelpolicy/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Dto<ItripSearchFacilitiesHotelVO> queryHotelPolicy(
            @ApiParam(required = true, name = "id", value = "酒店ID")
            @PathVariable Long id) {
        ItripSearchPolicyHotelVO itripSearchPolicyHotelVO = null;
        try {
            if (EmptyUtils.isNotEmpty(id)) {
                itripSearchPolicyHotelVO = itripHotelService.queryHotelPolicy(id);
                return DtoUtil.returnDataSuccess(itripSearchPolicyHotelVO.getHotelPolicy());
            } else {
                return DtoUtil.returnFail("酒店id不能为空", "10208");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", "10209");
        }
    }

    @ApiOperation(value = "根据酒店id查询酒店设施", httpMethod = "GET",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "根据酒店id查询酒店设施" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>10206: 酒店id不能为空</p>" +
            "<p>10207: 系统异常,获取失败</p>")
    @RequestMapping(value = "/queryhotelfacilities/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Dto<ItripSearchFacilitiesHotelVO> queryHotelFacilities(
            @ApiParam(required = true, name = "id", value = "酒店ID")
            @PathVariable Long id) {
        ItripSearchFacilitiesHotelVO itripSearchFacilitiesHotelVO = null;
        try {
            if (EmptyUtils.isNotEmpty(id)) {
                itripSearchFacilitiesHotelVO = itripHotelService.getItripHotelFacilitiesById(id);
                return DtoUtil.returnDataSuccess(itripSearchFacilitiesHotelVO.getFacilities());
            } else {
                return DtoUtil.returnFail("酒店id不能为空", "10206");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", "10207");
        }
    }
}
