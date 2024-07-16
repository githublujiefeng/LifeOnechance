package learn.dubbo.provider.MyProblem.One.Interface;

import learn.dubbo.provider.MyProblem.One.JobExecuteContext;

import java.util.concurrent.ThreadPoolExecutor;

public interface IJobHandler {
    String getName();
    ThreadPoolExecutor getThreadPool();
}
