package com.neusoft.schedule.feign;

import com.neusoft.apis.schedule.IScheduleClient;
import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.schedule.dtos.Task;
import com.neusoft.schedule.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScheduleClient implements IScheduleClient {

    @Autowired
    private TaskService taskService;

    /**
     * 添加延迟任务
     *
     * @param task
     * @return
     */
    @Override
    @PostMapping("/api/v1/task/add")
    public ResponseResult addTask(@RequestBody Task task){
        return taskService.addTask(task);
    }

    /**
     * 取消任务
     *
     * @param taskId
     * @return
     */
    @Override
    @GetMapping("api/v1/task/{taskId}")
    public ResponseResult cancelTask(@PathVariable("taskId") long taskId){
        return taskService.cancelTask(taskId);
    }

    /**
     * 按照类型和优先级拉取任务
     *
     * @param type
     * @param priority
     * @return
     */
    @Override
    @GetMapping("/api/v1/task/{type}/{priority}")
    public ResponseResult poll(@PathVariable("type") int type,@PathVariable("priority") int priority){
        return taskService.poll(type,priority);
    }
}
