package com.rebvar.app.ws;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rebvar.app.ws.security.SecurityUtils;

@SpringBootApplication
public class NordeaTaskWsApplication {

	public static void main(String[] args) {
		
		NordeaTaskWsApplication.LoadApplicationConstants();
		SpringApplication.run(NordeaTaskWsApplication.class, args);
		
	}
	
	public static void LoadApplicationConstants() {
		    
	    try {
	    	
	    	File resource = new ClassPathResource(
	  		      "application.properties").getFile();
	  		    
	    	FileInputStream reader = new FileInputStream(resource);
	    
	       
	    	byte[] data = new byte[(int) resource.length()];
	    	reader.read(data);
	    	reader.close();

	    	String lines[] = new String(data, "UTF-8").split("\n");
	        
		    for (String line : lines)
		    {
		    	line  = line.replace("\n", "").replace("\r", "");
		    	
		    	if (line.startsWith("token_secret="))
		    	{
		    	 	AppConstants.TOKEN_SECRET = line.split("=")[1];
		    	}
		    	else if (line.startsWith("ow_api_key="))
		    	{
		    	 	AppConstants.OW_API_KEY = line.split("=")[1];
		    	}
		    	else if (line.startsWith("base_external_url="))
		    	{
		    	 	AppConstants.OW_BASE_EXTERNAL_URL = line.split("=")[1];
		    	} 
		    	else if (line.startsWith("forecast_external_url="))
		    	{
		    	 	AppConstants.OW_FORECAST_EXTERNAL_URL = line.split("=")[1];
		    	}
		    }
	    }
	    catch(Exception ex)
	    {
	    	System.err.println("Failed Reading Conf...");
	    	AppConstants.OW_BASE_EXTERNAL_URL = "";
	    	AppConstants.OW_FORECAST_EXTERNAL_URL = "";
	    	AppConstants.OW_API_KEY = "";
	    	AppConstants.TOKEN_SECRET = "";
	    }
	}

	
	@Bean
	public BCryptPasswordEncoder bCryptEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	
	@Bean 
	public SpringApplicationContext springApplicationContext()
	{
		return new SpringApplicationContext();
	}
	
	@Bean
	public SecurityUtils securityUtils()
	{
		return new SecurityUtils();
	}
	
}
