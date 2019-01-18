package cn.itrip.trade.service;

import cn.itrip.beans.pojo.ItripHotelOrder;

public interface OrderService {
    public ItripHotelOrder loadItripHotelOrder(String orderNo)throws Exception;

    public void paySuccess(String orderNo,Integer payType,String tradeNo)throws Exception;

    public void payFailed(String orderNo,Integer payType,String tradeNo)throws Exception;
}
