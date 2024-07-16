package learn.dubbo.provider.MyProblem.One.conf;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.io.Resource;

//@Component
public class FlowBeanFactory extends DefaultListableBeanFactory {
    private final FlowBeanDefinitionReader reader = new FlowBeanDefinitionReader(this);

    public FlowBeanFactory(Resource resource) throws BeansException {
        initBeanDefinitionReader(reader);
        reader.loadBeanDefinitions(resource);
    }


    protected void initBeanDefinitionReader(FlowBeanDefinitionReader beanDefinitionReader) {

    }
}
