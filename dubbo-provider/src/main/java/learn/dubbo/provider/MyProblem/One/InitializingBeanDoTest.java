package learn.dubbo.provider.MyProblem.One;

import learn.dubbo.provider.MyProblem.One.Interface.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitializingBeanDoTest implements InitializingBean , ApplicationContextAware {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        //看是否在这里能拿到文件对应的bean
        Map<String, IJobHandler> jobHandlerMap = applicationContext.getBeansOfType(IJobHandler.class);
        for (String key:
        jobHandlerMap.keySet()) {
            LOGGER.info("bean名字是：{}",key);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
