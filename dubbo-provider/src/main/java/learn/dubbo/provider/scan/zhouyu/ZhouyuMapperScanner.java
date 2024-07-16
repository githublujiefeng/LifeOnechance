package learn.dubbo.provider.scan.zhouyu;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ZhouyuMapperScanner extends ClassPathBeanDefinitionScanner {

    public ZhouyuMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        return true;
    }

    //如果是接口
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        new ConcurrentHashMap<>();
        return beanDefinition.getMetadata().isInterface();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders =  super.doScan(basePackages);

        return beanDefinitionHolders;
    }
}
