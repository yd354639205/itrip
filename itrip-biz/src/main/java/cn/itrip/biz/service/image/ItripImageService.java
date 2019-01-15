package cn.itrip.biz.service.image;
import cn.itrip.beans.pojo.ItripImage;
import java.util.List;
import java.util.Map;

import cn.itrip.beans.vo.ItripImageVO;

/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripImageService {

    public List<ItripImageVO> getItripImageListByMap(Map<String, Object> param)throws Exception;

}
