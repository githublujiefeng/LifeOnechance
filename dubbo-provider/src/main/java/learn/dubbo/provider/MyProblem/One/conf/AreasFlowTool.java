package learn.dubbo.provider.MyProblem.One.conf;

import learn.dubbo.provider.MyProblem.One.Interface.IFlowTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AreasFlowTool implements IFlowTool {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${eagle.path:ljfFlow}")
    private String path;

    @Override
    public void execute(HashMap map, String a) {
        LOGGER.info("执行{}flow流程！",a);
    }
}
