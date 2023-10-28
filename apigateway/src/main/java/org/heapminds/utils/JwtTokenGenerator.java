package org.heapminds.utils;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Component
public class JwtTokenGenerator {



    public String getUsername(String token){
        Claims username = Jwts.parser().setSigningKey(JwtConstants.SECRET_KEY).parseClaimsJws(token).getBody();
        return username.getSubject();
    }

    public Boolean isValidToken(String token){
        try{
            System.out.println(token);
        Jwts.parser().setSigningKey(JwtConstants.SECRET_KEY).parseClaimsJws(token);
    return true;
    }
        catch(Exception e){

            return false;
        }

    }
    
}
