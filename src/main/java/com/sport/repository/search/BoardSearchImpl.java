package com.sport.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.sport.domain.Board;
import com.sport.domain.QBoard;
import com.sport.domain.QReply;
import com.sport.dto.BoardListReplyCountDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class);
    }

//    @Override
//    public Page<Board> searchAll(String searchOption, String search, String category, String type, Pageable pageable) {
//
//        QBoard board = QBoard.board;
//
//        JPQLQuery<Board> query = from(board);
//
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//        if(category != null) {
//
//            booleanBuilder.and(board.category.contains(category));
//        }
//
//        if(type != null ) {
//
//            booleanBuilder.and(board.type.contains(type));
//        }
//
//        if(searchOption != null && search != null) {
//
//
//            if(searchOption.equals("title")) {
//                booleanBuilder.or(board.title.contains(search));
//            } else if(searchOption.equals("nickName")) {
//                booleanBuilder.or(board.nickName.contains(search));
//            } else if(searchOption.equals("content")) {
//                booleanBuilder.or(board.content.contains(search));
//            }
//        }
//
//        query.where(booleanBuilder);
//
//        query.where(board.boardNo.gt(0L));
//
//        this.getQuerydsl().applyPagination(pageable, query);
//
//        List<Board> list = query.fetch();
//
//        long count = query.fetchCount();
//
//        return new PageImpl<>(list, pageable, count);
//    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String searchOption, String search, String category, String type,
                                                             Boolean status, LocalDate from, LocalDate to, Pageable pageable) {


        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));

        query.groupBy(
                board.boardNo,
                board.title,
                board.content,
                board.nickName,
                board.apiUser,
                board.category,
                board.type,
                board.status,
                board.matchDate,
                board.createdAt,
                board.updatedAt
        );

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(category != null) {

            booleanBuilder.and(board.category.contains(category));
        }

        if(type != null ) {

            booleanBuilder.and(board.type.contains(type));
        }

        if(status != null ) {
            booleanBuilder.and(board.status.eq(status));
        }

        if(from != null && to !=null) {
            booleanBuilder.and(board.matchDate.goe(from));
            booleanBuilder.and(board.matchDate.loe(to));
        }

        if(searchOption != null && search != null) {


            if(searchOption.equals("title")) {
                booleanBuilder.and(board.title.contains(search));
            } else if(searchOption.equals("nickName")) {
                booleanBuilder.and(board.nickName.contains(search));
            } else if(searchOption.equals("content")) {
                booleanBuilder.and(board.content.contains(search));
            }
        }

        query.where(booleanBuilder);

        query.where(board.boardNo.gt(0L));

        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.bean(BoardListReplyCountDTO.class,
                board.boardNo,
                board.title,
                board.nickName,
                board.category,
                board.type,
                board.status,
                board.matchDate,
                board.createdAt,
                board.updatedAt,
                reply.count().as("replyCount")
        ));

        this.getQuerydsl().applyPagination(pageable,dtoQuery);

        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();

        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList,pageable,count);
    }
}

