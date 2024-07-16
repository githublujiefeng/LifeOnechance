package learn.dubbo.provider.service.Impl;

import com.dubbo.bean.UserAddress;
import com.dubbo.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
//@Service//暴露dubbo服务
//@Component
public class UserServiceImpl implements UserService {


    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress address1 = new UserAddress(1,"杭州市萧山区宁东北苑","1","李老师","010-11111111","Y");
        UserAddress address2 = new UserAddress(2,"深圳市宝安区尚硅谷大厦","1","王老师","010-22222222","N");
        return Arrays.asList(address1,address2);
    }
}
