package cn.itrip.biz.service.hotelroom;
import cn.itrip.beans.pojo.ItripHotelRoom;
import java.util.List;
import java.util.Map;

import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.itrip.common.Page;
/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripHotelRoomService {

    public List<ItripHotelRoomVO>	getItripHotelRoomListByMap(Map<String, Object> param)throws Exception;

}
