package com.prathamesh.taskmanager.Config;

import com.prathamesh.taskmanager.Service.EmailService;
import jakarta.mail.MessagingException;
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
        System.out.println(context.getScheduledFireTime());

        String subject = "🔔✨ Reminder: Your Task ‘" + task + "’ is Due on " + dueDate + " 📅✅";
        String text = "<p><strong>Dear " + to + ",</strong></p>" +
                "<p>I hope this message finds you well! 😊</p>" +
                "<p>This is a friendly reminder that your task:</p>" +
                "<p><strong>📝 ‘" + task + "’</strong></p>" +
                "<p>is scheduled to be completed by <strong>📅 " + dueDate + "</strong>.</p>" +
                "<p><strong>🔍 Task Details:</strong></p>" +
                "<ul>" +
                "<li><strong>Task:</strong> " + task + "</li>" +
                "<li><strong>Due Date:</strong> " + dueDate + "</li>" +
                "</ul>" +
                "<p><strong>🌟 Tips to Complete Your Task:</strong></p>" +
                "<ul>" +
                "<li><strong>Prioritize:</strong> Focus on high-impact activities first.</li>" +
                "<li><strong>Plan:</strong> Break down the task into manageable steps.</li>" +
                "<li><strong>Reach Out:</strong> Don’t hesitate to ask for assistance if needed.</li>" +
                "</ul>" +
                "<p>Looking forward to your continued progress! 🚀</p>" +
                "<p><strong>Best regards,</strong><br/>" +
                "<strong>Prathamesh Corporations Pvt. Ltd. </strong><br/>" +
                "📞 Contact Us: +919359452534<br/>" +
                "🌐 Visit Our Website: <a href='[Your Website URL]'>[Your Website URL]</a></p>" +
                "<hr/>" +
                "<p><em>This is an automated reminder. If you've already completed this task, please disregard this email. Thank you! 🙏</em></p>";

        try {
            emailService.sendEmail(to, subject, text, task, dueDate);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
