/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clawsonanalytics.MAX.App.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.clawsonanalytics.MAX.App.ModelLayer.User;
/**
 *
 * @author andrewclawson
*/
@Controller
@SessionAttributes
public class DashboardController {
    public DashboardController(){
        
    }
    
    @RequestMapping(value="/dashboard", method=RequestMethod.POST)
    public ModelAndView MainDashboardControl(HttpServletRequest request){
        HttpSession session = request.getSession();
        
        String email = request.getParameter("loginEmail");
        String password = request.getParameter("loginPassword");
        
        ModelAndView modelView = new ModelAndView("loginUser");
        try{
            
            User loginUser = User.GetByCredentials(email, password);
            if (loginUser != null){
                modelView.setViewName("dashboard/dashboard");
                modelView.addObject("loginUser",loginUser);
                
                session.setAttribute("loginUser", loginUser);
                
            }else{
                modelView.setViewName("redirect:startup.htm");
                modelView.addObject("loginError","Invalid credentials");
                session.setAttribute("loginError", "Invalid credentials");
            }
            
        }catch(Exception e){
            modelView.setViewName("redirect:startup.htm");
            
            
        }
        //return "dashboard/dashboard";
        return modelView;
    }
    
    @RequestMapping(value="/dashboard")
    public ModelAndView Logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        session.removeAttribute("loginError");
        ModelAndView modelView = new ModelAndView("logout");
        modelView.setViewName("redirect:startup.htm");
        return modelView;
    }
    
}
