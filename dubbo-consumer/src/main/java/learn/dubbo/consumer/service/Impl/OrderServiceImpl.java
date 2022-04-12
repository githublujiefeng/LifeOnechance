package learn.dubbo.consumer.service.Impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.bean.UserAddress;
import com.dubbo.service.OrderService;
import com.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 1、将服务提供者注册到注册中心
 *      1）导入dubbo依赖(2.6.2)\操作zookeeper的客户段(curator)
 *      2）配置服务提供者
 * 2、让服务消费者去注册中心订阅服务提供者的服务地址
 */
@Service
public class OrderServiceImpl implements OrderService {

    //@Autowired
    @Reference
    UserService userService;

//    public void initOrder(String userId) {
//        System.out.println("用户id："+userId);
//        //1、查询用户收货地址
//        List<UserAddress> addressList = userService.getUserAddressList(userId);
//        for (UserAddress address:addressList
//        ) {
//            System.out.println(address.getUserAddress());
//        }
//    }
    public List<UserAddress> initOrder(String userId) {
        System.out.println("用户id："+userId);
        //1、查询用户收货地址
        List<UserAddress> addressList = userService.getUserAddressList(userId);
        for (UserAddress address:addressList
        ) {
            System.out.println(address.getUserAddress());
        }
        return addressList;
    }
}
