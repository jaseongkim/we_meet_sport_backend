package com.sport.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class JWTUtilTests {

    @Autowired
    private JWTUtil jwtUtil;

//    @Test
//    public void testGenerate() {
//
//        Map<String, Object> claimMap = Map.of("mid", "ABCDE");
//
//        String jwtStr = jwtUtil.generateToken(claimMap,1);
//
//        log.info(jwtStr);
//    }
//
//    @Test
//    public void testValidate() {
//        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTE1NDc2NjAsIm1pZCI6IkFCQ0RFIiwiaWF0IjoxNzExNDYxMjYwfQ.YAglX5Kv3OJMX7TnL-8riYmAzl99IP0IuGgqsA3HIUI";
//
//        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
//
//        log.info(claim);
//    }
//
//    @Test
//    public void testAll(){
//        String jwtStr = jwtUtil.generateToken(Map.of("mid", "AAAA", "email", "aaaa@bbb.com"), 1);
//
//        log.info(jwtStr);
//
//        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
//
//        log.info("MID: " + claim.get("mid"));
//
//        log.info("EMAIL: " + claim.get("email"));
//    }

}
