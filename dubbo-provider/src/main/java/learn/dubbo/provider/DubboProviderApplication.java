package learn.dubbo.provider;


import com.ljf.protocol.HttpServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * 1、导入依赖
 *         1）导入dubbo-starter
 *         2）导入dubbo的其他依赖
 */
//@EnableDubbo//启动dubbo服务
@SpringBootApplication
public class DubboProviderApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DubboProviderApplication.class, args);

        //上下文本对象
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        //注册启动类
        applicationContext.register(DubboProviderApplication.class);
        //读取注解并启动Springboot
        applicationContext.refresh();

        //启动tomcat
        HttpServer.start(applicationContext);
        //本地注册
        //注册中心注册
    }

}
