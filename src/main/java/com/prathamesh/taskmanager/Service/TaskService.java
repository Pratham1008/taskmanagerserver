package com.prathamesh.taskmanager.Service;

import com.prathamesh.taskmanager.Config.EmailJob;
import com.prathamesh.taskmanager.Model.Tasks;
import com.prathamesh.taskmanager.Model.Users;
import com.prathamesh.taskmanager.Repository.TaskRepository;
import com.prathamesh.taskmanager.Repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private Scheduler scheduler;

    public List<Tasks> getTasksByUsers(HttpServletRequest request) {
        Users user = new Users();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    String token = cookie.getValue();
                    String username = jwtService.extractUsername(token);
                    user = userRepository.findByEmail(username).orElseThrow();
                }
            }
        }
        return repository.findByUserOrderByDueDateAsc(user);
    }


    public Tasks getTaskById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Tasks addTask(Tasks task, HttpServletRequest request) throws SchedulerException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    String token = cookie.getValue();
                    String username = jwtService.extractUsername(token);
                    Users user = userRepository.findByEmail(username).orElseThrow();
                    task.setUser(user);
                }
            }
        }
        String job = UUID.randomUUID().toString();
        scheduleEmail(task,job);
        return repository.save(task);
    }

    public Tasks updateTask(String id, Tasks task) throws SchedulerException {
        Tasks oldTask =  repository.findById(id).orElseThrow();
        oldTask.setTask(task.getTask());
        oldTask.setPriority(task.getPriority());
        oldTask.setDueDate(task.getDueDate());
        String job = UUID.randomUUID().toString();
        scheduleEmail(oldTask,job);
        return repository.save(oldTask);
    }

    public Tasks updateStatus(String id, Tasks task) {
        Tasks oldTask =  repository.findById(id).orElseThrow();
        oldTask.setDone(task.getDone());
        return repository.save(oldTask);
    }

    public void deleteTask(String id) {
        repository.deleteById(id);
    }

    private void scheduleEmail(Tasks task, String job) throws SchedulerException {
        Date dueDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dueDate = dateFormat.parse(task.getDueDate());
        } catch (ParseException e) {
            throw new SchedulerException("Invalid date format", e);
        }

        JobDetail jobDetail = JobBuilder.newJob(EmailJob.class)
                .withIdentity(job + task.getId())
                .usingJobData("email", task.getUser().getEmail())
                .usingJobData("task", task.getTask())
                .usingJobData("dueDate", task.getDueDate())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(job + task.getId())
                .startAt(dueDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
