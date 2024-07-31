package com.learn.restful.controller;

import com.learn.restful.bean.User;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    //创建线程安全的Map，模拟users信息的存储
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    /**
     * 处理“/users/”的get请求，用来获取用户列表
     * @return
     */
    @GetMapping("/")
    public List<User> getUserList(){
        List<User> r = new ArrayList<>(users.values());
        return r;
    }

    /**
     * 处理“/user/”的post请求，用来创建User
     * @param user
     * @return
     */
    @PostMapping("/")
    public String postUser(@RequestBody User user){
        users.put(user.getId(), user);
        return "success";
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        User user = users.get(id);
        return user;
    }
    /**
     * 处理“/users/{id}”的put请求，用来更新User信息
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public String putUser(@PathVariable Long id, @RequestBody User user){
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id,u);
        return "success";
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        users.remove(id);
        return "success";
    }
}
