package com.sport.repository;

import com.sport.domain.APIUser;
import com.sport.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void testSearchAll() {
        String searchOption = "title";

        String search = "title";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("boardNo").descending());

        Page<Board> result = boardRepository.searchAll(searchOption, search, pageable);

        log.info(result.getTotalPages());

        log.info(result.getSize());

        log.info(result.getNumber());

        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testRegister() {

        IntStream.rangeClosed(6,100).forEach(i -> {
            Board board = Board.builder()
                    .title("title..." +i)
                    .content("content..." + i)
                    .nickName("테스트"+ (i%10))
                    .category("농구")
                    .type("구인/구팀")
                    .apiUser(APIUser.builder().email("jasung427@naver.com").build())
                    .build();

            boardRepository.save(board);
        });
    }

}
