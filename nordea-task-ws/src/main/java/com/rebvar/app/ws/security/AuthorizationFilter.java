package com.rebvar.app.ws.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.rebvar.app.ws.AppConstants;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    	
	static SecurityUtils sutils = new SecurityUtils();
	
    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
     }
    
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        
        String header = req.getHeader(AppConstants.HEADER_STRING);
        
        if (header == null || !header.startsWith(AppConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }   
    
    
    /**
     * @param request
     * @return Validates the request header for sign in process.
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AppConstants.HEADER_STRING);
        
        if (token != null) {
            
            String user = sutils.getUserIdFromToken(token);
            
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            
            return null;
        }
        
        return null;
    }
}
 