package com.sport.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

//    @Test
//    public void testInsert(){
//        Long boardNo = 1L;
//
//        Board board = Board.builder()
//                .boardNo(boardNo).build();
//
//        Reply reply = Reply.builder()
//                .board(board)
//                .replyText("댓글")
//                .replyer("replyer1")
//                .build();
//
//        replyRepository.save(reply);
//
//    }
//
//    @Test
//    public void testBoardReplies(){
//        Long boardNo = 1L;
//
//        Pageable pageable = PageRequest.of(0,10, Sort.by("replyNo").descending());
//
//        Page<Reply> result = replyRepository.listOfBoard(boardNo, pageable);
//
//        result.getContent().forEach(reply->{
//            log.info(reply);
//        });
//    }




}