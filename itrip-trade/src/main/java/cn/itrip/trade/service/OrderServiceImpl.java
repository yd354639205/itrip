package cn.itrip.trade.service;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;

    @Override
    public ItripHotelOrder loadItripHotelOrder(String orderNo) throws Exception {
        Map<String,Object> param = new HashMap<>();
        param.put("orderNo",orderNo);
        List<ItripHotelOrder> results = itripHotelOrderMapper.getItripHotelOrderListByMap(param);
        if (results.size()>0)
            return results.get(0);
        else
            return null;
    }

    @Override
    public void paySuccess(String orderNo, Integer payType, String tradeNo) throws Exception {
        ItripHotelOrder order = this.loadItripHotelOrder(orderNo);
        order.setOrderStatus(2);
        order.setPayType(payType);
        order.setTradeNo(tradeNo);
        this.itripHotelOrderMapper.updateItripHotelOrder(order);

        //减库存,由业务提供API,在这里进行调用

    }

    @Override
    public void payFailed(String orderNo, Integer payType, String tradeNo) throws Exception {
        ItripHotelOrder order = this.loadItripHotelOrder(orderNo);
        order.setOrderStatus(1);
        order.setPayType(payType);
        order.setTradeNo(tradeNo);
        this.itripHotelOrderMapper.updateItripHotelOrder(order);
    }
}
