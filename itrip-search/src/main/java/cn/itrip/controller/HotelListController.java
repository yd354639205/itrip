package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.SearchHotCityVO;
import cn.itrip.beans.SearchHotelVO;
import cn.itrip.beans.vo.hotel.ItripHotelVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.service.SearchHotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api/hotellist")
@Api(tags = "hotel-list-controller")
public class HotelListController {

    @Resource
    private SearchHotelService searchHotelService;

    @ApiOperation(value = "查询酒店分页", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "查询酒店分页(用于查询酒店列表)" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码: </p>" +
            "<p>20001: 系统异常,获取失败</p>" +
            "<p>20002: 目的地不能为空</p>")
    @RequestMapping(value = "/searchItripHotelPage",method = RequestMethod.POST)
    public @ResponseBody
    Dto<Page<ItripHotelVO>> searchItripHotelPage(@RequestBody SearchHotelVO vo){
        Page page = null;
        if (EmptyUtils.isEmpty(vo)||EmptyUtils.isEmpty(vo.getDestination()))
            return DtoUtil.returnFail("目的地不能为空", "20002");
        try {
            page = searchHotelService.searchItripHotelPage(vo,vo.getPageNo(),vo.getPageSize());
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", "20001");
        }
        return DtoUtil.returnDataSuccess(page);
    }

    @ApiOperation(value = "根据热门城市查询酒店", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "根据热门城市查询酒店" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码: </p>" +
            "<p>20003: 系统异常,获取失败</p>" +
            "<p>20004: 城市id不能为空</p>")
    @RequestMapping(value = "/searchItripHotelListByHotCity",method = RequestMethod.POST)
    public @ResponseBody
    Dto<Page<ItripHotelVO>> searchItripHotelListByHotCity(@RequestBody SearchHotCityVO vo){
        if (EmptyUtils.isEmpty(vo)||EmptyUtils.isEmpty(vo.getCityId()))
            return DtoUtil.returnFail("城市id不能为空", "20004");
        List list = null;
        try {
            list = searchHotelService.searchItripHotelListByHotCity(vo.getCityId(),vo.getCount());
        }catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,获取失败", "20003");
        }
        return DtoUtil.returnDataSuccess(list);
    }
}
