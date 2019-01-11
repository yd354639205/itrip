package cn.itrip.search.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.vo.hotel.SearchHotCityVO;
import cn.itrip.beans.vo.hotel.SearchHotelVO;
import cn.itrip.beans.vo.userinfo.ItripUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/hotellist")
@Api(tags = "hotel-list-controller")
public class HotelListController {

    @ApiOperation(value = "查询酒店分页")
    @RequestMapping(value = "/searchItripHotelPage",method = RequestMethod.POST)
    public @ResponseBody
    Dto searchItripHotelPage(SearchHotelVO vo, HttpServletRequest request){
        return null;
    }

    @ApiOperation(value = "根据热门城市查询酒店")
    @RequestMapping(value = "/searchItripHotelListByHotCity",method = RequestMethod.POST)
    public @ResponseBody
    Dto searchItripHotelListByHotCity(SearchHotCityVO vo,HttpServletRequest request){
        return null;
    }
}
