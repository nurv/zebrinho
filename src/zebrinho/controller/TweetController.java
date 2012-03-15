/* Handles tweet and search related controllers
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import zebrinho.domain.Tweet;
import zebrinho.domain.User;

@Controller
public class TweetController extends ZebrinhoController {
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public static class ResourceNotFoundException extends RuntimeException {
	}
	
	@RequestMapping(value = "/tweet", method = RequestMethod.POST)
	public ModelAndView tweet(HttpServletRequest request, @RequestParam("tweet") String tweet) {
		User user = getLoggedUser(request);
		ModelAndView mv = new ModelAndView("redirect:/");
		if (user == null){
			return mv;	
		}else{
			user.tweet(tweet);
			return mv;
		}
		
	}
	
	@RequestMapping(value = "/tweetAsync", method = RequestMethod.POST)
	@ResponseBody
	public String tweetAsync(HttpServletRequest request, @RequestParam("tweet") String tweet) {
		User user = getLoggedUser(request);
		if (user == null){
			return "ERROR";	
		}else{
			user.tweet(tweet);
			return "OK";
		}
	}
	
	@RequestMapping(value = "/getTweetsAsync", method = RequestMethod.GET)
	@ResponseBody
	public String getTweetsAsync(HttpServletRequest request,HttpServletResponse response) {
		
		User user = getLoggedUser(request);
		if (user == null){
			return "ERROR";	
		}else{
			List<Tweet> wall = Tweet.forUserWall(user);
			JSONArray list = new JSONArray();
			for(Tweet tweet : wall){
				JSONObject obj = new JSONObject();
				obj.put("content", tweet.getContent());
				obj.put("date", tweet.getFormatedDate());
				obj.put("user", tweet.getUser().getUsername());
				list.add(obj);
			}
			return list.toJSONString();
		}
	}
	
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public ModelAndView user(HttpServletRequest request, @PathVariable("username") String username) {
		User user = getLoggedUser(request); 
		
		if (user == null){
			return new ModelAndView("redirect:/");	
		}else{
			User requestUser = User.fromUsername(username);
			
			if (requestUser == null){
				throw new ResourceNotFoundException();
			}
			ModelAndView mv = new ModelAndView("user");
			mv.addObject("user",requestUser);
			mv.addObject("username",user.getUsername());
			mv.addObject("isOwn", user.getId() == requestUser.getId());
			mv.addObject("follows",user.follows(requestUser));
			return 	mv;
		}
		
	}
	
	@RequestMapping(value = "/follow/{username}", method = RequestMethod.GET)
	public ModelAndView follow(HttpServletRequest request, @PathVariable("username") String username) {
		User user = getLoggedUser(request); 
		if (user == null){
			return new ModelAndView("redirect:/");	
		}else{
			User requestUser = User.fromUsername(username);
			user.follow(requestUser);
			ModelAndView mv = new ModelAndView("redirect:/user/" + requestUser.getUsername());
			return 	mv;
		}
	}
	
	@RequestMapping(value = "/unfollow/{username}", method = RequestMethod.GET)
	public ModelAndView unfollow(HttpServletRequest request, @PathVariable("username") String username) {
		User user = getLoggedUser(request); 
		if (user == null){
			return new ModelAndView("redirect:/");	
		}else{
			User requestUser = User.fromUsername(username);
			user.unfollow(requestUser);
			ModelAndView mv = new ModelAndView("redirect:/user/" + requestUser.getUsername());
			return 	mv;
		}
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request) {
		User user = getLoggedUser(request); 
		if (user == null){
			return new ModelAndView("redirect:/");	
		}else{
			ModelAndView mv = new ModelAndView("search");
			mv.addObject("username", user.getUsername());
			return mv;	
		}
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchDo(HttpServletRequest request, @RequestParam("username") String username) {
		User user = getLoggedUser(request); 
		if (user == null){
			return new ModelAndView("redirect:/");	
		}else{
			ModelAndView mv = new ModelAndView("search");
			List<User> users = User.all();
			List<User> result = new ArrayList<User>();
			for (User requestUser : users) {
				if (requestUser.getUsername().contains(username)){
					result.add(requestUser);
				}
			}
			mv.addObject("username", user.getUsername());
			mv.addObject("searchUsername", username);
			mv.addObject("users", result);
			return mv;	
		}
	}
}
