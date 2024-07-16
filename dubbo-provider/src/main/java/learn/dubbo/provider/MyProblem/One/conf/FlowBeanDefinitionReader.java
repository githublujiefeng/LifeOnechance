package learn.dubbo.provider.MyProblem.One.conf;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


public class FlowBeanDefinitionReader //extends AbstractBeanDefinitionReader
{

    public FlowBeanDefinitionReader(BeanDefinitionRegistry registry) {
       // super(registry);
    }

    //@Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        return 0;
    }
}
