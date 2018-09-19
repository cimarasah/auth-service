package com.cimarasah.auth.security.jwt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

import com.cimarasah.auth.config.JwtSecurityProperties;
import com.cimarasah.auth.domain.model.User;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

@Component("encrypted")
public class JwtEncryptedTokenFactory implements JwtFactory, Serializable {

	private static final long serialVersionUID = 3042637815598923446L;

    @Value("${security.jwt.keyStore}")
    private File keyStore;

	@Inject
	private JwtSecurityProperties jwtSecurityProperties;

    @Inject
    private AudienceProvider audienceProvider;

    @Override
    public String getUsernameFromToken(String token) {
        try {
            JWTClaimsSet jwtClaims = this.getClaimsFromToken(token);

            return jwtClaims.getSubject();
        } catch (Exception e) {           	
        }
        
        return null;
    }

    @Override
    public String generateToken(User user, Device device) {
        JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
                .issuer(jwtSecurityProperties.getName())
                .issueTime(generateCurrentDate())
                .expirationTime(generateExpirationDate())
                .claim("userInfo", UserInfoFactory.create(user))
                .claim("audience", audienceProvider.generateAudience(device))
                .claim("created", new Date())
                .build();

        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
        EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);

        RSAEncrypter encrypter = new RSAEncrypter((RSAPublicKey) publicKey());
        try {
            jwt.encrypt(encrypter);
            return jwt.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new JwtTokenException("Não foi possível gerar o token.");
        }
    }

    @Override
    public Boolean canTokenBeRefreshed(String token) {
        try {
            final Date expirationDate = getClaimsFromToken(token).getExpirationTime();

            return expirationDate.compareTo(generateCurrentDate()) > 0;
        } catch (Exception e) {
        }

        return false;
    }

    @Override
    public Date generateInactiveDate() {
        return new Date(getCurrentTimeMillis() + jwtSecurityProperties.getInactiveInMinutes() * 60000);
    }

    @Override
    public Date generateExpirationDate() {
        return new Date(getCurrentTimeMillis() + jwtSecurityProperties.getExpiresInHours() * 3600000);
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(jwtSecurityProperties.getHeader());

        if (authHeader != null && (authHeader.startsWith("Bearer ") || authHeader.startsWith("bearer "))) {
            return authHeader.substring(7);
        }

        return null;
    }

    private JWTClaimsSet getClaimsFromToken(String token) {
        try {
            EncryptedJWT jwt = EncryptedJWT.parse(token);
            RSADecrypter decrypter = new RSADecrypter((PrivateKey) privateKey());
            jwt.decrypt(decrypter);
            return jwt.getJWTClaimsSet();
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            throw new JwtTokenException("Não foi possível recuperar as informações do token");
        }
    }

    private long getCurrentTimeMillis() {
        return DateTime.now().getMillis();
    }

    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    private PublicKey publicKey() {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(keyStore);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(fin, jwtSecurityProperties.getKeyStorePassword().toCharArray());

            final Certificate cert = ks.getCertificate(jwtSecurityProperties.getAlias());
            return cert.getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new JwtTokenException("Não foi possível recuperar a chave para a criação do token.");
    }

    private Key privateKey() {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(keyStore);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(fin, jwtSecurityProperties.getKeyStorePassword().toCharArray());
            return ks.getKey(jwtSecurityProperties.getAlias(),
                    jwtSecurityProperties.getPrivateKeyPassword().toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new JwtTokenException("Não foi possível recuperar a chave para a criação do token.");
    }
}