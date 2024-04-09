package com.sport.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.sport.domain.Board;
import com.sport.domain.QBoard;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> searchAll(String searchOption, String search, String category, String type, Pageable pageable) {

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(category != null) {

            booleanBuilder.and(board.category.contains(category));
        }

        if(type != null ) {

            booleanBuilder.and(board.type.contains(type));
        }

        if(searchOption != null && search != null) {


            if(searchOption.equals("title")) {
                booleanBuilder.or(board.title.contains(search));
            } else if(searchOption.equals("nickName")) {
                booleanBuilder.or(board.nickName.contains(search));
            } else if(searchOption.equals("content")) {
                booleanBuilder.or(board.content.contains(search));
            }
        }

        query.where(booleanBuilder);

        query.where(board.boardNo.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}

