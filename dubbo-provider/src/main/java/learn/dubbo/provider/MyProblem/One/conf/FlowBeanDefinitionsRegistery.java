package learn.dubbo.provider.MyProblem.One.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

@Component
public class FlowBeanDefinitionsRegistery extends DefaultListableBeanFactory
        implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, ResourceLoaderAware {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private Environment environment;
    private ResourceLoader resourceLoader;
    private String simpleJobFlowPath;
    private Integer simpleJobCount=0;
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        LOGGER.info("开始加载flow生成bean");
        simpleJobFlowPath = System.getProperty("eagle.scheduler.simpleJob.dir");
        if (StringUtils.isEmpty(simpleJobFlowPath)) {
            simpleJobFlowPath = environment.getProperty("eagle.scheduler.simpleJob.dir");
        }
        loadBeanDefinitions(simpleJobFlowPath,beanDefinitionRegistry);
        LOGGER.info("生成simpleJob对象：{}个",simpleJobCount);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        LOGGER.info("postProcessBeanFactory");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private void loadBeanDefinitions(String flowPath, BeanDefinitionRegistry beanDefinitionRegistry) {
        if(StringUtils.isEmpty(flowPath)){
            LOGGER.info("flowPath is empty");
            return;
        }

        //后续多种job类型进行处理

        //扫描对应文件，进行beanDefinition的生成
        try {
            Resource resource = resourceLoader.getResource("flow/"+flowPath);
            scanFileAndCreateBeanDefinition(resource.getFile(),beanDefinitionRegistry);
        } catch (IOException e) {
            LOGGER.error("不存在此文件夹！");
            throw new RuntimeException(e);
        }

    }

    //扫描文件下所有flw结尾文件
    private void scanFileAndCreateBeanDefinition(File f, BeanDefinitionRegistry beanDefinitionRegistry) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (null == files) {
                return;
            }
            for (File file : files) {
                this.scanFileAndCreateBeanDefinition(file,beanDefinitionRegistry);
            }
        } else {
            if (f.getName().endsWith("flw")) {
                String flowName = f.getName();
                flowName = flowName.split("\\.")[0];
                this.createBeanDefinition(flowName,beanDefinitionRegistry);
                simpleJobCount++;
            }
        }
    }
    private void createBeanDefinition(String flowName, BeanDefinitionRegistry beanDefinitionRegistry) {

        //第一种实现，是准备新建BeanDefinition对象，来实现每一个文件对应一个GenericBeanDefinition，可惜这样实现跟直接Component注解生成的BeanDefinition差距很大，在afterPropertiesSet里获取不到对应的bean
//        GenericBeanDefinition flowBeanDefinition = new GenericBeanDefinition();
//        flowBeanDefinition.setBeanClass(AbstractSimpleJob.class);
//        BeanDefinition aresFlowTool = beanDefinitionRegistry.getBeanDefinition("aresFlowTool");
//        flowBeanDefinition.getPropertyValues().add("aresFlowTool",aresFlowTool);
//        flowBeanDefinition.getPropertyValues().add("name",flowName);
//        flowBeanDefinition.setMethodOverrides(new MethodOverrides());

        //第二种实现，下面的写法有问题，因为是以abstractSimpleJob模板来创建bean实例，导致属性name都相同，实际上name应该都是各自的flowName
        BeanDefinition abstractSimpleJob = beanDefinitionRegistry.getBeanDefinition("abstractSimpleJob");
        abstractSimpleJob.getPropertyValues().add("name",flowName);
        beanDefinitionRegistry.registerBeanDefinition(flowName, abstractSimpleJob);
    }

    public Integer getSimpleJobCount() {
        return simpleJobCount;
    }
}
