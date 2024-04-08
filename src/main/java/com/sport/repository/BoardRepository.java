package com.sport.repository;

import com.sport.domain.Board;
import com.sport.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {



}
