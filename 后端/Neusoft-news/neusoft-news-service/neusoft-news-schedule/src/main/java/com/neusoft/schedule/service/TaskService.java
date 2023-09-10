package com.neusoft.schedule.service;

import com.neusoft.model.common.dtos.ResponseResult;
import com.neusoft.model.schedule.dtos.Task;

public interface TaskService {

    /**
     * 添加延迟任务
     * @param task
     * @return
     */
    public ResponseResult addTask(Task task);

    /**
     * 取消任务
     * @param taskId
     * @return
     */
    public ResponseResult cancelTask(long taskId);

    /**
     * 按照类型和优先级拉取任务
     * @param type
     * @param priority
     * @return
     */
    public ResponseResult poll(int type, int priority);
}
