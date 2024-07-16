package learn.dubbo.provider.scan.zhouyu;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ZhouyuSpringApplication {


    public static void run(Class clazz){
        //创建一个Spring容器
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(clazz);
        applicationContext.refresh();
        //启动tomcat-》DispatcherServlet
        
    }
}
