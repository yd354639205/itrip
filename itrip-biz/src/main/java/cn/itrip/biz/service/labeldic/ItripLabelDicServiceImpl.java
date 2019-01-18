package cn.itrip.biz.service.labeldic;
import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.dao.labeldic.ItripLabelDicMapper;
import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import cn.itrip.common.Constants;
@Service
public class ItripLabelDicServiceImpl implements ItripLabelDicService {

    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;

    /**
     * 根据parentId查询数据字典
     * @param parentId
     * @return
     * @throws Exception
     * add by hanlu 2017-5-11
     */
    public List<ItripLabelDicVO> getItripLabelDicByParentId(Long parentId)throws Exception{
        return itripLabelDicMapper.getItripLabelDicByParentId(parentId);
    }

    public List<ItripLabelDic> getItripLabelDicListByMap(Map<String,Object> param)throws Exception{
        return itripLabelDicMapper.getItripLabelDicListByMap(param);
    }

}
