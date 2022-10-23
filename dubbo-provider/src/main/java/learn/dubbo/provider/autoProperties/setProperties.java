package learn.dubbo.provider.autoProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
public class setProperties {

    @Autowired
    Environment environment;

    @RequestMapping("/setPerporties")
    public void setPerportiesByNmae(String name,String value){
        Properties properties = new Properties();
        String k = environment.getProperty("dubbo.application.name");
        
        System.out.println(k);
    }
}
