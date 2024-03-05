package com.itwillbs.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CalcTest {

	public static void main(String[] args) {
		
		ApplicationContext CTX = new ClassPathXmlApplicationContext("aop.xml");
		// root-context를 사용하면 객체 생성없이 사용가능
		
		Calculator c = (Calculator)CTX.getBean("proxyCal");
		
		c.add(100, 200);
	}
}