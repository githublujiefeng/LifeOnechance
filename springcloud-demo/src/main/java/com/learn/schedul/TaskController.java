package com.learn.schedul;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private SchedulTaskService schedulTaskService;
    /**
     * 编辑任务cron表达式
     */
    @ResponseBody
    @RequestMapping("/editTaskCron")
    public HashMap editTaskCron(@RequestBody SchedulDTO schedulDTO) {
//        if (!CronExpression.isValidExpression(schedulDTO.getCorn())) {
//            throw new IllegalArgumentException("失败,非法表达式:" + schedulDTO.getCorn());
//        }
        try {
            schedulDTO.setSchedulClass(Class.forName(schedulDTO.getName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        schedulTaskService.add(schedulDTO);
        return ResStatus.getSuccess("编辑成功！");
    }

    @GetMapping("/all")
    public List<SchedulDTO> getAllTask(){
        return schedulTaskService.taskList;
    }

    @PostMapping("/startTask")
    public HashMap startTask(@RequestBody SchedulDTO schedulDTO){
        try {
//            ApplicationContext context = new AnnotationConfigApplicationContext()
            if(!schedulTaskService.add(schedulDTO)){
                return ResStatus.getFail("启动失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResStatus.getFail("出现异常！");
        }
        return ResStatus.getSuccess("启动成功！");
    }

    @DeleteMapping("/{name}")
    public String stopDynamicTask(@PathVariable("name") String name){
        // 将这个添加到动态定时任务中去
        if(!schedulTaskService.stopTask(name)){
            return "停止失败,任务已在进行中.";
        }
        return "任务已停止";
    }

}
