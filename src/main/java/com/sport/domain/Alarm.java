package com.sport.domain;


import lombok.*;

import javax.persistence.*;

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
    private String type;

    @Column
    private String category;

    @Column
    private String status;

    @Column(nullable = false, length = 4000)
    private String message;

}