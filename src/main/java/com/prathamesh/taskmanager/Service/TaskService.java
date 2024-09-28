package com.prathamesh.taskmanager.Service;

import com.prathamesh.taskmanager.Model.Tasks;
import com.prathamesh.taskmanager.Model.Users;
import com.prathamesh.taskmanager.Repository.TaskRepository;
import com.prathamesh.taskmanager.Repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

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

    public Tasks addTask(Tasks task, HttpServletRequest request) {
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
        return repository.save(task);
    }

    public Tasks updateTask(String id, Tasks task) {
        Tasks oldTask =  repository.findById(id).orElseThrow();
        oldTask.setTask(task.getTask());
        oldTask.setPriority(task.getPriority());
        oldTask.setDueDate(task.getDueDate());
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
}
