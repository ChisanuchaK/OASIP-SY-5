package sit.int221.TimeUpBackend.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sit.int221.TimeUpBackend.dtos.Token;

@Service
public class JwtTokenUtil  {

    private String tokenSecret = "javainuse";

    @Value("${jwt.expiration}")
    private int tokenExpirationMsec;

    @Value("${jwt.refreshExpirationDateInMs}")
    private int refreshTokenExpirationMsec;


    public Token generateAccessToken(String subject) {
        Date now = new Date();
        Long duration = now.getTime() + tokenExpirationMsec;
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
        return new Token(Token.TokenType.ACCESS, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }


    public Token generateRefreshToken(String subject) {
        Date now = new Date();
        Long duration = now.getTime() + refreshTokenExpirationMsec;
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
        return new Token(Token.TokenType.REFRESH, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }


    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public LocalDateTime getExpiryDateFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parse(token);
            return true;
        } catch (SignatureException ex) {
            ex.printStackTrace();
        } catch (MalformedJwtException ex) {
            ex.printStackTrace();
        } catch (ExpiredJwtException ex) {
            ex.printStackTrace();
        } catch (UnsupportedJwtException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
