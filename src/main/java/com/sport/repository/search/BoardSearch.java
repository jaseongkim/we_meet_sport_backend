package com.sport.repository.search;

import com.sport.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> searchAll(String searchOption, String search, Pageable pageable);

}
