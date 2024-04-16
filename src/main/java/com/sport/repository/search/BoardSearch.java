package com.sport.repository.search;

import com.sport.dto.BoardListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface BoardSearch {

//    Page<Board> searchAll(String searchOption, String search, String category, String type, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount(String searchOption, String search, String category, String type,
                                                      Boolean status, LocalDate from, LocalDate to, Pageable pageable);

}
