package learn.dubbo.provider.MyProblem.One.Interface;

import learn.dubbo.provider.MyProblem.One.JobExecuteContext;

public interface ISimpleJobHandler extends IJobHandler{
    void handle(JobExecuteContext jobExecuteContext) throws Exception;
}
