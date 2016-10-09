package org.pbccrc.api.security.datasource;

import java.util.Properties;

import org.pbccrc.api.util.DesUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static final String KEY = "0002000200020002";  
	
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {  
        try {  
            String username = props.getProperty("jdbc.username");  
            if (username != null) {  
                props.setProperty("jdbc.username", DesUtils.decrypt(username, KEY));  
            }  
              
            String password = props.getProperty("jdbc.password");  
            if (password != null) {  
                props.setProperty("jdbc.password", DesUtils.decrypt(password, KEY));  
            }  
              
            String url = props.getProperty("jdbc.url");  
            if (url != null) {  
                props.setProperty("jdbc.url", DesUtils.decrypt(url, KEY));  
            }  
              
            String driverClassName = props.getProperty("jdbc.driverClassName");  
            if(driverClassName != null){  
                props.setProperty("jdbc.driverClassName", DesUtils.decrypt(driverClassName, KEY));  
            }  
            super.processProperties(beanFactory, props);  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new BeanInitializationException(e.getMessage());  
        }  
    }  
}
