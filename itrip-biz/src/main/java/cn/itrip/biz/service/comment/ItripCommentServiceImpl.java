package cn.itrip.biz.service.comment;

import cn.itrip.beans.vo.comment.ItripListCommentVO;
import cn.itrip.beans.vo.comment.ItripScoreCommentVO;
import cn.itrip.common.Constants;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import cn.itrip.dao.comment.ItripCommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ItripCommentServiceImpl implements ItripCommentService {

    @Resource
    ItripCommentMapper itripCommentMapper;

    public ItripScoreCommentVO getAvgAndTotalScore(Long hotelId) throws Exception {
        return itripCommentMapper.getCommentAvgScore(hotelId);
    }

    @Override
    public Page<ItripListCommentVO> queryItripCommentPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripCommentMapper.getItripCommentCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo)? Constants.DEFAULT_PAGE_NO:pageNo;
        pageSize=EmptyUtils.isEmpty(pageSize)?Constants.DEFAULT_PAGE_SIZE:pageSize;
        Page page = new Page(pageNo,pageSize,total);
        param.put("beginPos",page.getBeginPos());
        param.put("pageSize",page.getPageSize());
        List<ItripListCommentVO> itripCommentList = itripCommentMapper.getItripCommentListByMap(param);
        page.setRows(itripCommentList);
        return page;
    }

    @Override
    public Integer getItirpCommentCountByMap(Map<String, Object> param) throws Exception {
        return itripCommentMapper.getItripCommentCountByMap(param);
    }
}
