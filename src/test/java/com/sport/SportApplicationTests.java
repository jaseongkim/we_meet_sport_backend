package com.sport;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
@Log4j2
class SportApplicationTests {

    @Autowired
    private DataSource dataSource;

//    @Test
//    public void testConnection() throws SQLException {
//
//        @Cleanup
//        Connection con = dataSource.getConnection();
//
//        log.info(con);
//    }


}
