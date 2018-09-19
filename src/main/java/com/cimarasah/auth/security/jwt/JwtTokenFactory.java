package com.cimarasah.auth.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

import com.cimarasah.auth.config.JwtSecurityProperties;
import com.cimarasah.auth.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component("regular")
public class JwtTokenFactory implements JwtFactory, Serializable {

	private static final long serialVersionUID = 3042637815598923446L;

	@Inject
	private JwtSecurityProperties jwtSecurityProperties;

	@Inject
	private AudienceProvider audienceProvider;

    private final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    @Override
    public String getUsernameFromToken(String token) {
        try {
            final Claims claims = this.getClaimsFromToken(token);
            
            return claims.getSubject();
        } catch (Exception e) {           	
        }
        
        return null;
    }
    
    private Map<String, Object> generateClaims(User user, Device device) {
    	Map<String, Object> claims = new HashMap<>();
    	
    	claims.put("userInfo", UserInfoFactory.create(user));
        claims.put("audience", audienceProvider.generateAudience(device));
        claims.put("created", new Date());
        
        return claims;
    }

    @Override
    public String generateToken(User user, Device device) {
        return Jwts.builder()
                .setIssuer(jwtSecurityProperties.getName())
                .setClaims(generateClaims(user, device))
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, jwtSecurityProperties.getSecret())
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecurityProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    @Override
    public Boolean canTokenBeRefreshed(String token) {
        try {
            final Date expirationDate = getClaimsFromToken(token).getExpiration();
            
            return expirationDate.compareTo(generateCurrentDate()) > 0;
        } catch (Exception e) {        	
        }
        
        return false;
    }

    private long getCurrentTimeMillis() {
        return DateTime.now().getMillis();
    }

    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    @Override
    public Date generateExpirationDate() {
        return new Date(getCurrentTimeMillis() + jwtSecurityProperties.getExpiresInHours() * 3600000);
    }

    @Override
    public Date generateInactiveDate() {
        return new Date(getCurrentTimeMillis() + jwtSecurityProperties.getInactiveInMinutes() * 60000);
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(jwtSecurityProperties.getHeader());
        
        if (authHeader != null && (authHeader.startsWith("Bearer ") || authHeader.startsWith("bearer "))) {
            return authHeader.substring(7);
        }

        return null;
    }
}