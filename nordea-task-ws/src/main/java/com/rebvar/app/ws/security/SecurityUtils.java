package com.rebvar.app.ws.security;

import java.util.UUID;

import com.rebvar.app.ws.AppConstants;

import io.jsonwebtoken.Jwts;

public class SecurityUtils {
	
	public String getUserIdFromToken(String token)
	{
		token = token.replace(AppConstants.TOKEN_PREFIX, "");
        String user;
		try
		{
			user = Jwts.parser()
                .setSigningKey(AppConstants.TOKEN_SECRET)
                .parseClaimsJws( token )
                .getBody()
                .getSubject();
			
		}
		catch(Exception invalid)
		{
			user = "";
		}
		
		return user;
	}
	
	
	/**
	 * @return
	 * Generates a Unique String Id
	 */
	public String getUUID()
	{
		return UUID.randomUUID().toString();
	}
	
}
