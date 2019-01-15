package cn.itrip.biz.service.labeldic;
import cn.itrip.beans.pojo.ItripLabelDic;
import java.util.List;
import java.util.Map;
import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.common.Page;

/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripLabelDicService {

    /**
     * 根据parentId查询数据字典
     * @param parentId
     * @return
     * @throws Exception
     * add by hanlu 2017-5-11
     */
    public List<ItripLabelDicVO> getItripLabelDicByParentId(Long parentId)throws Exception;

}
