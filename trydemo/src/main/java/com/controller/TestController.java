package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
}
