package com.sport.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class APIUser {

    @Id
    private String email;

    private String mpw;

    private String nickName;

    private String mobile;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<APIUserRole> roleSet = new HashSet<>();

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date regDate;

    public void changeMpw(String mpw){
        this.mpw = mpw;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeMobile(String mobile) {
        this.mobile = mobile;
    }

    public void addRole(APIUserRole apiUserRole) {
        this.roleSet.add(apiUserRole);
    }
    public void clearRole() {
        this.roleSet.clear();
    }







}
