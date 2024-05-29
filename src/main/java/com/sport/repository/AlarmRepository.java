package com.sport.repository;

import com.sport.domain.Alarm;
import com.sport.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByWriter(String email);

    Optional<Alarm> findAlarmByBoardAndApplicant(Board board, String applicant);


}
