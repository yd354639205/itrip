package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import cn.itrip.common.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisAPI redisAPI;

    /**
     *生成token
     * @param userAgent
     * @param user
     * @return
     * @throws Exception
     */
    public String generateToken(String userAgent, ItripUser user) throws Exception {
        StringBuilder str = new StringBuilder();
        str.append("token:");
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        if(agent.getOperatingSystem().isMobileDevice())
            str.append("MOBILE-");
        else
            str.append("PC-");
        str.append(MD5.getMd5(user.getUserCode(),32)+"-");
        str.append(user.getId().toString()+"-");
        str.append(new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date())+"-");
        str.append(MD5.getMd5(userAgent,6));
        return str.toString();
    }

    private long protectenTime = 30*60;
    private int delay = 2*60;
    @Override
    public String reloadToken(String userAgent, String token)throws Exception {
        //验证token是否有效
        if (!redisAPI.exist(token))
            throw new Exception("token无效");
        //能不能狗置换
        Date genTime = new SimpleDateFormat("yyyyMMddHHmmsss").parse(token.split("-")[3]);
        long passed = Calendar.getInstance().getTimeInMillis()-genTime.getTime();
        if (passed<protectenTime)
            throw new Exception("token置换保护期内，你能置换。剩余"+(protectenTime-passed)/1000);
        //进行转换
        String user = redisAPI.get(token);
        ItripUser itripUser = JSON.parseObject(user,ItripUser.class);
        String newToken = this.generateToken(userAgent,itripUser);
        //老的token延时过期
        redisAPI.set(token,delay,user);
        //新的token保存到redis
        this.save(newToken,itripUser);
        return newToken;
    }

    /**
     *
     * @param userAgent
     * @param token
     * @return
     * @throws Exception
     */
    public boolean vaildate(String userAgent, String token) throws Exception {
        if(!redisAPI.exist(token))
            return false;
        String agentMD5 = token.split("-")[4];
        if (!MD5.getMd5(userAgent,6).equals(agentMD5))
            return false;
        return true;
    }

    /**
     * token保存到redis
     * @param token
     * @param user
     * @throws Exception
     */
    public void save(String token,ItripUser user) throws Exception {
        if (token.startsWith("token:PC-"))
            redisAPI.set(token,2*60*60, JSON.toJSONString(user));
        else
            redisAPI.set(token,JSON.toJSONString(user));
    }

    public void delete(String token) {
        redisAPI.delete(token);
    }
}
