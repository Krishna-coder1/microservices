package org.heapminds.security;

import java.io.IOException;

import org.heapminds.exception.HMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    private  CustomDetailUserService customDetailUserService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        try{
           
            String token = this.getJwtToken(request);
            
            if(StringUtils.hasText(token)&&jwtTokenGenerator.isValidToken(token)){
                String userName= jwtTokenGenerator.getUsername(token);
                UserDetails userDetails = customDetailUserService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);}
            else  {
                  if (request.getRequestURL().toString().contains("auth")||request.getRequestURL().toString().contains("test")) {
                    filterChain.doFilter(request, response);
                }
            filterChain.doFilter(request, response);
                
            }}
             catch(Exception e){
                throw new HMException(e.getMessage());
        }
    }

    private String getJwtToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token)){
            return token.substring(7);
        }
        return null;
    }

}
