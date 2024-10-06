package com.prathamesh.taskmanager.Config;

import com.prathamesh.taskmanager.Service.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailJob implements Job {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String to = context.getJobDetail().getJobDataMap().getString("email");
        String task = context.getJobDetail().getJobDataMap().getString("task");
        String dueDate = context.getJobDetail().getJobDataMap().getString("dueDate");

        emailService.sendEmail(to, "Task Reminder: " + task, "Your task \"" + task + "\" is due on " + dueDate);
    }
}
