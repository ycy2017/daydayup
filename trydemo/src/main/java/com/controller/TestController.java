package com.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("hello")
public class TestController {
	
	public TestController(){
		System.out.println("..........................");
	}
	
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getData(){
		System.out.println("............getData.............");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("现在的日期", System.currentTimeMillis());
		map.put("nameproxy_set_header   Host $host:$server_port;", "name");
		return map;
	}
	
	@RequestMapping(value="/hello1",method=RequestMethod.GET)
	public String getData1(){
		System.out.println("............getData1.............");
		return "现在的日期" + System.currentTimeMillis();
	}
	
	
	/**
	 * 转发测试
	 * @return
	 */
	@RequestMapping(value="/forward",method=RequestMethod.GET)
//	@ResponseBody
	public String forward(){
		System.out.println("............forward.............");
		return "forward:/hello";
	}
	
	@RequestMapping(value="/dologin",method=RequestMethod.GET)
	public void dologin(HttpServletRequest request,@RequestParam String username){
//		ServletActionContext.getServletContext().getInitParameter("...")
		//绑定session
//		try {
//			username = URLDecoder.decode(username,"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		try {
			username = new String(username.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		HttpSession session= request.getSession();
//		System.out.println("dologin..." + username);
//		session.setAttribute("username", username);
	}
	
	@RequestMapping(value="/loginout",method=RequestMethod.GET)
	public void loginout(HttpServletRequest request){
		HttpSession session= request.getSession();
		System.out.println("dologin..." + session.getAttribute("username"));
		session.removeAttribute("username");
		
	}
	
}
