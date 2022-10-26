package learn.dubbo.provider.scan.zhouyu;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ZhouyuImportBeanDefintionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
//        AbstractBeanDefinition Beandefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        Beandefinition.setBeanClass();
         //扫描mapper

    }
}
