package com.sport.domain;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alarm", indexes = {
        @Index(name = "idx_alarm_board_no", columnList = "board_board_no")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@SequenceGenerator(
        name = "SEQ_GENERATOR",
        sequenceName = "ALARM_SEQ",
        allocationSize = 1
)
@EntityListeners(value = {AuditingEntityListener.class})
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long alarmNo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column
    private Boolean bStatus;

    @Column
    private String title;

    @Column
    private String writer;

    @Column
    private String applicant;

    @Column
    private String applicantName;

    @Column
    private String type;

    @Column
    private String category;

    @Column
    private String status;

    @Column
    private String AlarmType;

    @Column(nullable = false, length = 4000)
    private String message;

    @CreatedDate
    @Column(name="createdAt", updatable = false)
    private LocalDateTime createdAt;



}