package cn.itrip.service;

import cn.itrip.beans.SearchHotelVO;
import cn.itrip.beans.vo.hotel.ItripHotelVO;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.dao.BaseQuery;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchHotelServiceImpl implements SearchHotelService {

    private BaseQuery<ItripHotelVO> baseQuery = new BaseQuery("http://192.168.171.131:8080/solr/hotel/");

    @Override
    public Page<ItripHotelVO> searchItripHotelPage(SearchHotelVO vo, Integer pageNo, Integer pageSize) throws Exception {
        SolrQuery query = new SolrQuery("*:*");
        StringBuffer temp = new StringBuffer();
        int tempFlag = 0;
        if (EmptyUtils.isNotEmpty(vo)){
            //目的地
            if (EmptyUtils.isNotEmpty(vo.getDestination())){
                temp.append("destination:"+vo.getDestination());
                tempFlag = 1;
            }
            //酒店级别(1-5)
            if (EmptyUtils.isNotEmpty(vo.getHotelLevel())){
                query.addFilterQuery("hotelLevel:"+vo.getHotelLevel());
            }
            //关键词
            if (EmptyUtils.isNotEmpty(vo.getKeywords())){
                if (tempFlag ==1)
                    temp.append(" AND keyword :"+vo.getKeywords());
                else
                    temp.append(" keyword :"+vo.getKeywords());
            }
            //酒店特色id(每次只能选一个)
            if (EmptyUtils.isNotEmpty(vo.getFeatureIds())) {
                StringBuffer buffer = new StringBuffer("(");
                int flag = 0;
                String featureIdArray[] = vo.getFeatureIds().split(",");
                for (String featureId : featureIdArray) {
                    if (flag == 0) {
                        buffer.append(" featureIds:" + "*," + featureId + ",*");
                    } else {
                        buffer.append(" OR featureIds:" + "*," + featureId + ",*");
                    }
                    flag++;
                }
                buffer.append(")");
                query.addFilterQuery(buffer.toString());
            }
            //商圈id(每次只能选一个)
            if (EmptyUtils.isNotEmpty(vo.getTradeAreaIds())) {
                StringBuffer buffer = new StringBuffer("(");
                int flag = 0;
                String tradeAreaIdArray[] = vo.getTradeAreaIds().split(",");
                for (String tradeAreaId : tradeAreaIdArray) {
                    if (flag == 0) {
                        buffer.append(" tradingAreaIds:" + "*," + tradeAreaId + ",*");
                    } else {
                        buffer.append(" OR tradingAreaIds:" + "*," + tradeAreaId + ",*");
                    }
                    flag++;
                }
                buffer.append(")");
                query.addFilterQuery(buffer.toString());
            }
            //最高价
            if (EmptyUtils.isNotEmpty(vo.getMaxPrice())) {
                query.addFilterQuery("minPrice:" + "[* TO " + vo.getMaxPrice() + "]");
            }
            //最低价
            if (EmptyUtils.isNotEmpty(vo.getMinPrice())) {
                query.addFilterQuery("minPrice:" + "[" + vo.getMinPrice() + " TO *]");
            }
            //按照某个字段升序
            if (EmptyUtils.isNotEmpty(vo.getAscSort())) {
                query.addSort(vo.getAscSort(), SolrQuery.ORDER.asc);
            }
            //按照某个字段降序
            if (EmptyUtils.isNotEmpty(vo.getDescSort())) {
                query.addSort(vo.getDescSort(), SolrQuery.ORDER.desc);
            }
        }
        if (EmptyUtils.isNotEmpty(temp.toString())) {
            query.setQuery(temp.toString());
        }
        Page<ItripHotelVO> page = baseQuery.queryPage(query, pageNo, pageSize, ItripHotelVO.class);
        return page;
    }

    @Override
    public List<ItripHotelVO> searchItripHotelListByHotCity(Integer cityId, Integer count) throws Exception {
        SolrQuery query = new SolrQuery("*:*");
        if (EmptyUtils.isNotEmpty(cityId))
            query.addFilterQuery("cityId:"+cityId);
        else
            return null;
        List<ItripHotelVO> list = baseQuery.queryList(query,count,ItripHotelVO.class);
        return list;
    }
}
