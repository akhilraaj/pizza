package com.seqato.pizza.controller;






import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



import com.seqato.pizza.model.User;
import com.seqato.pizza.service.UserService;

@Controller
public class LoginController {
	
	
	
	@Autowired
	private UserService service;
	
	
	
	@RequestMapping(value="/login")
	public String login()
	{
		return "login";
	}
	
	
	@RequestMapping(value="/data", method= RequestMethod.POST )
	public String data(@ModelAttribute ("user1") User user1, Model model)
	{
		
		
		String email=user1.getEmail();
		boolean x1=service.checkMail(email);
			if(x1==true)
			{
			service.saveUser(user1);
			return "regsuccess";
			}
			else{
				
             model.addAttribute("error"," Email already exists!");
             return "login";
                  
			}
			
	}
	
	@RequestMapping(value="/login2")
	public String userLogin(@RequestParam("email") String email ,@RequestParam("password") String password,Model model,HttpServletRequest request) 

	{
	//Model model=null;
		boolean x2=service.checkUser(email, password);
		if(x2==false)
		{
			String user =service.fetchUser(email,password);
			System.out.println(user);
			HttpSession session= request.getSession();
			session.setAttribute("user", user);
			session.setAttribute("emails", email);
			return "logsuccess";
			
			
		}
		else{
			System.out.println(email);
			model.addAttribute("errorlogin","Invalid E-mail or Password");
			return "login";
		}
		
	}
	
	@RequestMapping(value="/logout")
	public String userLogout(HttpSession session)
	{
		session.invalidate();
		return "home";
	}
}
	


