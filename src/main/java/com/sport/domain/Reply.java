package com.sport.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_no", columnList = "board_board_no")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@SequenceGenerator(
        name = "SEQ_GENERATOR",
        sequenceName = "REPLY_SEQ",
        allocationSize = 1
)
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long replyNo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;

    private String nickName;

    private String email;

    public void change(String replyText, String nickName){
        this.replyText = replyText;
        this.nickName = nickName;
    }

}
