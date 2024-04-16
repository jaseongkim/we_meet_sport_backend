package com.sport.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "apiUser")
@Table(name = "board")
@SequenceGenerator(
        name = "SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ",
        allocationSize = 1
)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long boardNo;

    @ManyToOne(fetch = FetchType.LAZY)
    private APIUser apiUser;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 4000)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private LocalDate matchDate;

    public void change(String title, String content, boolean status, LocalDate matchDate) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.matchDate = matchDate;
    }

}