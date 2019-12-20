package com.yuwuquan.demo.session;

import com.yuwuquan.demo.orm.model.SysUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_PHONE = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    private static final String secret = "WeAreFoxesWeAreGoingToEatChickens";

    private static final Long expiration = 60*60*24*30l;//默认为token有效期30天。

    /**
     * 从token中获取手机号（用户名）
     * @param token
     * @return
     */
    public String getPhoneFromToken(String token) {
        String phone;
        try {
            final Claims claims = getClaimsFromToken(token);
            phone = claims.getSubject();
        } catch (Exception e) {
            phone = null;
        }
        return phone;
    }

    /**
     * 从token中获取创建时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 从token中获取过期时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * 私有方法，目前只开放从token中获取创建时间、手机号、过期时间
     * 从token中获取Clasms对象。Claims继承Map。
     * ------iss: jwt签发者
     * ------sub: jwt所面向的用户
     * ------aud: 接收jwt的一方
     * ------exp: jwt的过期时间，这个过期时间必须要大于签发时间
     * ------nbf: 定义在什么时间之前，该jwt都是不可用的.
     * ------iat: jwt的签发时间
     * j------ti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 校验token是否过期。true过期，false未过期
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 对外暴露生成token为该方法。必须含phone。
     * @param sysUserInfo
     * @return
     */
    public String generateToken(SysUserInfo sysUserInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_PHONE, sysUserInfo.getPhone());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 传入旧token，刷新该token，返回新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, SysUserInfo sysUserInfo) {
        final String phone = getPhoneFromToken(token);
        return (phone != null && phone.equals(sysUserInfo.getPhone())  && !isTokenExpired(token));
    }

    public static void main(String[] args) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        SysUserInfo sysUserInfo = new SysUserInfo("13127959029");
        String s=jwtTokenUtil.generateToken(sysUserInfo);
        System.out.println(s);
        System.out.println(jwtTokenUtil.validateToken(s+"ss",sysUserInfo));
    }
}
