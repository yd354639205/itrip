package cn.itrip.biz.service.hotel;
import cn.itrip.beans.HotelVideoDescVO;
import cn.itrip.beans.ItripSearchDetailsHotelVO;
import cn.itrip.beans.ItripSearchFacilitiesHotelVO;
import cn.itrip.beans.ItripSearchPolicyHotelVO;
import java.util.List;



/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripHotelService {

    /**
     * 根据酒店id查询酒店特色、商圈、酒店名称
     * @param id
     * @return
     * @throws Exception
     */
    public HotelVideoDescVO getVideoDescByHotelId(Long id)throws Exception;


    /**
     * 根据酒店的id查询酒店的设施 -add by donghai
     * @param id
     * @return
     * @throws Exception
     */
    public ItripSearchFacilitiesHotelVO getItripHotelFacilitiesById(Long id)throws Exception;

    /**
     * 根据酒店的id查询酒店的政策 -add by donghai
     * @param id
     * @return
     * @throws Exception
     */
    public ItripSearchPolicyHotelVO queryHotelPolicy(Long id)throws Exception;

    /**
     * 根据酒店的id查询酒店的特色和介绍 -add by donghai
     * @param id
     * @return
     * @throws Exception
     */
    public List<ItripSearchDetailsHotelVO> queryHotelDetails(Long id)throws Exception;

}
