package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.stereotype.Service;

public interface UserService {
    public void itriptxCreateUser(ItripUser user)throws Exception;
    public boolean activate(String mail,String code)throws Exception;
    public ItripUser findUserByUserCode(String userCode)throws Exception;
    public void itriptxCreateUserByPhone(ItripUser user)throws Exception;
    public boolean vaildatePhone(String phoneNum,String code)throws Exception;
    public ItripUser login(String userCode,String userPassword)throws Exception;
}
