package com.vo;

import javax.annotation.PreDestroy;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.DisposableBean;


public class UserEntity implements DisposableBean {

	public Integer id;
	String name;
	protected Integer age;

	public UserEntity() {
		this.id = (int) (Math.random() * 10);
		System.out.println("UserEntity   Construct " + this.id);
	}

	@PostConstruct
	public void PostConstruct() {
		System.out.println("执行PostConstructInit: PostConstruct");
	}

	/**
	 * spring 的ac.close() 调用时,这个被	@PreDestroy注解的摧毁的方法不会被执行,
	 * 为什么??
	 * 
	 * 
	 * 如果是到单利的话,由spring管理
	 * 如果是多例的话由ci管理,所有关闭spring容器,并不会调用destroy的方法
	 */
	@PreDestroy
	public void destroy() throws Exception {
		System.out.println("接口-执行InitBeanAndDestroyBeanTest：destroy方法");
	}

	public void destroyMethod() {
		System.out.println("接口-执行InitBeanAndDestroyBeanTest：destroy方法");
	}
	
}
