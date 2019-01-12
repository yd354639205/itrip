package cn.itrip.service;

import cn.itrip.beans.SearchHotelVO;
import cn.itrip.beans.vo.hotel.ItripHotelVO;
import cn.itrip.common.Page;

import java.util.List;

public interface SearchHotelService {

    public Page<ItripHotelVO> searchItripHotelPage(SearchHotelVO vo, Integer pageNo, Integer pageSize)throws Exception;

    public List<ItripHotelVO> searchItripHotelListByHotCity(Integer cityId, Integer pageSize)throws Exception;
}
