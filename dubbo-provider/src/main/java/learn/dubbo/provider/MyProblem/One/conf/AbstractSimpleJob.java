package learn.dubbo.provider.MyProblem.One.conf;


import learn.dubbo.provider.MyProblem.One.Interface.IFlowTool;
import learn.dubbo.provider.MyProblem.One.Interface.IJobWithFlowTool;
import learn.dubbo.provider.MyProblem.One.Interface.ISimpleJobHandler;
import learn.dubbo.provider.MyProblem.One.JobExecuteContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;


public  class AbstractSimpleJob implements ISimpleJobHandler, IJobWithFlowTool {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private IFlowTool flowTool;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setFlowTool(IFlowTool flowTool) {
        this.flowTool = flowTool;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ThreadPoolExecutor getThreadPool() {
        return null;
    }

    @Override
    public void handle(JobExecuteContext jobExecuteContext) throws Exception {
        //写共同代码。执行flow流程
//        flowTool.execute();
        LOGGER.info("开始执行SimpleJob任务：{}", getName());

//        IBusinessContext context = new BusinessContext(jobExecuteContext.getCustomParams());
        flowTool.execute(new HashMap<>(),"");
    }

}
