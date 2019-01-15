package cn.itrip.biz.service.comment;

import cn.itrip.beans.vo.comment.ItripListCommentVO;
import cn.itrip.beans.vo.comment.ItripScoreCommentVO;
import cn.itrip.common.Page;

import java.util.Map;

public interface ItripCommentService {
    public ItripScoreCommentVO getAvgAndTotalScore(Long hotelId)throws Exception;
    public Integer getItirpCommentCountByMap(Map<String,Object> param)throws Exception;
    public Page<ItripListCommentVO> queryItripCommentPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception;
}
