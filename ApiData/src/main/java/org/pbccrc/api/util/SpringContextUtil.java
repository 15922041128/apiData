package org.pbccrc.api.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringContextUtil {

	private static ApplicationContext applicationContext = new FileSystemXmlApplicationContext(  
            "classpath:conf/spring/*.xml");
	
	public static Object getBean(String name) throws BeansException {  
	    return applicationContext.getBean(name);  
	 }  
}
