package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private ItripUserMapper itripUserMapper;

    @Resource
    private RedisAPI redisAPI;

    @Resource
    private MailService mailService;

    @Resource
    private SmsService smsService;

    @Override
    public void itriptxCreateUser(ItripUser user) throws Exception {
        //添加用户信息
        itripUserMapper.insertItripUser(user);
        //激活码
        String activationCode = MD5.getMd5(new Date().toLocaleString(), 30);
        //发送激活邮件
        mailService.sendAccountActivateEmail(user.getUserCode(), activationCode);
        //激活码存入Redis
        redisAPI.set("activation:" + user.getUserCode(), 30 * 60, activationCode);
    }

    public void itriptxCreateUserByPhone(ItripUser user) throws Exception {
        //1.创建用户
        itripUserMapper.insertItripUser(user);
        //2.生成验证码（1111-9999）
        int code = MD5.getRandomCode();
        //3.发送验证码
        smsService.send(user.getUserCode(),"1",new String[]{String.valueOf(code) ,"3"});
        //4.缓存验证码到Redis
        redisAPI.set("activation:"+user.getUserCode(),3*60,String.valueOf(code));
    }

    //短信验证
    public boolean vaildatePhone(String phoneNum,String code)throws Exception{
        //1.对比验证码
        String key="activation:"+phoneNum;
        String value = redisAPI.get(key);
        if (value!=null&&value.equals(code)){
            //2.更新用户激活状态
            ItripUser user = this.findUserByUserCode(phoneNum);
            if (null!=user){
                user.setActivated(1);
                user.setFlatID(user.getId());
                user.setUserType(0);
                itripUserMapper.updateItripUser(user);
                return true;
            }
        }
        return false;
    }

    //激活码验证
    public boolean activate(String mail, String code) throws Exception {
        //1.对比激活码
        String key="activation:"+mail;
        String value = redisAPI.get(key);
        if (value!=null&&value.equals(code)){
            //2.更新用户激活状态
            ItripUser user = this.findUserByUserCode(mail);
            if (null!=user){
                user.setActivated(1);
                user.setFlatID(user.getId());
                user.setUserType(0);
                itripUserMapper.updateItripUser(user);
                return true;
            }
        }
        return false;
    }

    public ItripUser findUserByUserCode(String userCode) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userCode",userCode);
        ItripUser user = null;
        List<ItripUser> userList = itripUserMapper.getItripUserListByMap(map);
        if(userList.size()!=0){
            user = userList.get(0);
        }
        return user;
    }

    /**
     * 用户名密码的验证
     * @param userCode
     * @param userPassword
     * @return
     * @throws Exception
     */
    public ItripUser login(String userCode, String userPassword) throws Exception {
        ItripUser user = this.findUserByUserCode(userCode);
        if (null!=user&&user.getUserPassword().equals(userPassword)){
            if (user.getActivated()!=1)
                throw new Exception("用户未激活");
            return user;
        }
        else
            return null;
    }
}
