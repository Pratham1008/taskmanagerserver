package com.prathamesh.taskmanager.Controller;

import com.prathamesh.taskmanager.Model.Tasks;
import com.prathamesh.taskmanager.Model.UserDto;
import com.prathamesh.taskmanager.Service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public List<Tasks> getTasks(HttpServletRequest req){
        try {
            List<Tasks> tasks = service.getTasksByUsers(req);
            for(Tasks task : tasks){
                UserDto userDto = new UserDto();
                userDto.setId(task.getId());
                task.setUser(userDto);
            }
            return tasks;
        } catch (Exception e) {
            throw new RuntimeException("No tasks found");
        }
    }

    @GetMapping("/{id}")
    public Tasks getTaskById(@PathVariable String id){
        Tasks task = service.getTaskById(id);
        UserDto userDto = new UserDto();
        userDto.setId(task.getId());
        task.setUser(userDto);
        return task;
    }

    @PostMapping
    public Tasks createTask(@RequestBody Tasks task, HttpServletRequest request) throws SchedulerException {
        return service.addTask(task,request);
    }

    @PutMapping("/{id}")
    public Tasks updateTask(@RequestBody Tasks task, @PathVariable String id) throws SchedulerException {
        return service.updateTask(id,task);
    }

    @PutMapping("/status/{id}")
    public Tasks updateTaskStatus(@PathVariable String id, @RequestBody Tasks task){
        return service.updateStatus(id,task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id){
        service.deleteTask(id);
    }
}
