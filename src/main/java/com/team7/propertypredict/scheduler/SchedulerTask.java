package com.team7.propertypredict.scheduler;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.User;
import com.team7.propertypredict.service.MailService;
import com.team7.propertypredict.service.TransactionService;
import com.team7.propertypredict.service.UserService;

@Component
public class SchedulerTask {
	   private String emailSubject;
	   private String mailTo;
	   

	    @Autowired
	    private MailService mailService;

	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private TransactionService tsService;
	    
	    @Autowired
	    private TemplateEngine templateEngine;

	    //one time in one min
//	    @Scheduled(cron="0 */1 * * * ?")
	    //one time in 20 mins
//	    @Scheduled(cron="0 */20 * * * ?")
	    //Every Sunday at 1 am
	    @Scheduled(cron="0 0 1 ? * L")
	    private void process(){
    	
	    	System.out.println("SchedulerTask");

	    	ArrayList<User> shortListUsers=userService.findUserHaveShortlist();
	    	System.out.println(shortListUsers.size());
	    	for (User user : shortListUsers) {
	    		
	    		mailTo = user.getEmail();
	    		System.out.println(mailTo);
	    		
	    		List<Project> shortListUsersListProjects=tsService.getSimilarProjectIDsByPrice(user.getUserId());
	    		
		        Context context = new Context();
		        context.setVariable("recommendresult", shortListUsersListProjects);

		        String emailContent = templateEngine.process("sendMail", context);
		        emailSubject="Hi "+user.getUsername()+", Your Recommendations Here!";

		        mailService.sendHtmlMail("mycojer@gmail.com",emailSubject,emailContent);				
			}   	
	    	
	    }
}
