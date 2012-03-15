/* Handles login, logout and home controllers
   Copyright (C) 2012  Artur Ventura

Author: Artur Ventura

This file is part of Zebrinho.

Zebrinho is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Zebrinho is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Emacs.  If not, see <http://www.gnu.org/licenses/>.  */
package zebrinho.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import zebrinho.domain.DB;
import zebrinho.domain.Tweet;
import zebrinho.domain.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends ZebrinhoController{

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		User user = getLoggedUser(request);
		if (user == null){
			ModelAndView mv = new ModelAndView("home-pre");
			return mv;	
		}else{
			ModelAndView mv = new ModelAndView("home");
			mv.addObject("username", user.getUsername());
			mv.addObject("tweets", Tweet.forUserWall(user));
			return mv;
		}
		
	}

	@RequestMapping(value = "/reload", method = RequestMethod.GET)
	public ModelAndView reload() {
		DB.load();
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(HttpServletRequest request) {
		User user = getLoggedUser(request);
		ModelAndView m = new ModelAndView("home");
		if (user != null){
			return m;	
		}
		
		ModelAndView mv =  new ModelAndView("register");
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerSave(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		User user = getLoggedUser(request);
		ModelAndView m = new ModelAndView("home");
		if (user != null){
			return m;	
		}
		
		if (User.fromUsername(username) != null) {
			ModelAndView mv = new ModelAndView("register");
			mv.addObject("username", username);
			mv.addObject("message", "The '" + username + "'username already exists.");
			return mv;
		}
		Pattern pattern = Pattern.compile("([a-z0-9_]{1,20})",Pattern.CASE_INSENSITIVE);
		if (!pattern.matcher(username).matches()) {
			ModelAndView mv = new ModelAndView("register");
			mv.addObject("username", username);
			mv.addObject("message", "Invalid username. A username can only have letters, numbers and _");
			return mv;
		}
		user = new User();
		user.setUsername(username);
		user.setPassword(User.genHash(password));
		user.save();
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request){
		User user = getLoggedUser(request);
		ModelAndView mv = new ModelAndView("home");
		if (user != null){
			return mv;	
		}
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginSave(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password){
		User u = getLoggedUser(request);
		if (u != null){
			ModelAndView mv = new ModelAndView("home");
			return mv;	
		}
		User user = User.fromUsername(username);
		if (user == null) {
			ModelAndView mv = new ModelAndView("login");
			mv.addObject("username", username);
			return mv;
		}
		
		if (user.getPassword().equals(User.genHash(password))){
			request.getSession().setAttribute("loggedUser", user.getId());
			return new ModelAndView("redirect:/");
		}else{
			ModelAndView mv = new ModelAndView("login");
			mv.addObject("username", username);
			return mv;			
		}
	}
	
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request){
		request.getSession().setAttribute("loggedUser", null);
		 return new ModelAndView("redirect:/");
	}
}
