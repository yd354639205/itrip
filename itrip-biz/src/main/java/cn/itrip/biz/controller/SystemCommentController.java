package cn.itrip.biz.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.vo.comment.ItripScoreCommentVO;
import cn.itrip.beans.vo.comment.ItripSearchCommentVO;
import cn.itrip.biz.service.comment.ItripCommentService;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/comment")
@Api(tags = "comment-controller")
public class SystemCommentController {

    @Resource
    ItripCommentService itripCommentService;

    @ApiOperation(value = "据酒店id查询酒店平均分")
    @RequestMapping(value = "/gethotelscore/{hotelId}",method = RequestMethod.GET)
    public @ResponseBody
    Dto gethotelscore(@PathVariable String hotelId){
        Dto<Object> dto = new Dto<>();
        if (null!=hotelId&&!"".equals(hotelId)){
            ItripScoreCommentVO itripScoreCommentVO = new ItripScoreCommentVO();
            try {
                itripScoreCommentVO = itripCommentService.getAvgAndTotalScore(Long.valueOf(hotelId));
                dto = DtoUtil.returnSuccess("获取评分成功",itripScoreCommentVO);
            }catch (Exception e){
                e.printStackTrace();
                dto = DtoUtil.returnFail("获取评分失败","100001");
            }
        }else{
            dto = DtoUtil.returnFail("hotelId不能为空","100002");
        }
        return dto;
    }

    @ApiOperation(value = "根据酒店id查询各类评论数量")
    @RequestMapping(value = "/getcount/{hotelId}",method = RequestMethod.GET)
    public @ResponseBody
    Dto getcount(@PathVariable String hotelId){
        Dto<Object> dto = new Dto<>();
        Integer count = 0;
        Map<String,Integer> countMap = new HashMap<>();
        Map<String,Object> param = new HashMap<>();
        if (null != hotelId&&!"".equals(hotelId)){
            param.put("hotelId",hotelId);
            count = getItripCommentCountByMap(param);
            if (count!=1)
                countMap.put("allcomment",count);
            else
                return DtoUtil.returnFail("获取酒店总评论数失败","100014");
            param.put("isOK",1);
            count = getItripCommentCountByMap(param);
            if (count!=-1)
                countMap.put("havingimg",count);
            else
                return DtoUtil.returnFail("获取酒店有图片评论数失败","100015");

        }else{
            return DtoUtil.returnFail("参数hotelId为空","100018");
        }
        dto = DtoUtil.returnSuccess("获取酒店各类评论数成功",countMap);
        return dto;
    }

    @ApiIgnore
    public Integer getItripCommentCountByMap(Map<String,Object> param){
        Integer count = -1;
        try {
            count = itripCommentService.getItirpCommentCountByMap(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @ApiOperation(value = "根据评论类型查询评论列表，并分页显示")
    @RequestMapping(value = "/getcommentlist",method = RequestMethod.POST)
    public @ResponseBody
    Dto getcommentlist(@RequestBody ItripSearchCommentVO vo){
        Dto<Object> dto = new Dto<>();
        Map<String,Object> param = new HashMap<>();
        if (vo.getIsOk() == -1)
            vo.setIsOk(null);
        if (vo.getIsHavingImg() == -1)
            vo.setIsHavingImg(null);
        param.put("hotelId",vo.getHotelId());
        param.put("isHavingImg",vo.getIsHavingImg());
        param.put("isOK",vo.getIsOk());
        try {
            Page page = itripCommentService.queryItripCommentPageByMap(param,vo.getPageNo(),vo.getPageSize());
            dto = DtoUtil.returnDataSuccess(page);
        }catch (Exception e){
            e.printStackTrace();
            dto = DtoUtil.returnFail("获取评论列表错误","100020");
        }
        return dto;
    }
}
