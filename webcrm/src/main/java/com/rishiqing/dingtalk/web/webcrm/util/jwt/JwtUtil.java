package com.rishiqing.dingtalk.web.webcrm.util.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-12-18 16:03
 */
public class JwtUtil {
    private static final String JWT_SECRET = "abcd";
    private static final String JWT_ISSUER = "www.workbei.com";
    private static final Long JWT_EXPIRE_MILLS = 1000 * 60 * 60 * 24L;

    public static String sign(CorpStaffVO staffVO) {
        long now = new Date().getTime();
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        return JWT.create()
                .withIssuer(JWT_ISSUER)
                .withExpiresAt(new Date(now + JWT_EXPIRE_MILLS))
                .withSubject(JSON.toJSONString(staffVO))
                .sign(algorithm);
    }

    public static CorpStaffVO check(String token) {
        CorpStaffVO staffVO = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            staffVO = JSONObject.parseObject(jwt.getSubject(), CorpStaffVO.class);
        } catch (JWTVerificationException e) {
            // 如果验证失败，那么就不做处理
        }
        return staffVO;
    }

    /**
     * 获得过期日期时间
     * @param token
     * @return
     */
    public static Date getExpireDate(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(JWT_ISSUER)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Date expiresAt = jwt.getExpiresAt();
        return expiresAt;
    }
}
