package com.hihi.square.domain.buyer.entity;

import com.hihi.square.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "buyer")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder   //부모클래스의 필드를 빌더에 포함
@DiscriminatorValue("BUYER")
@ToString
public class Buyer extends User {
    @Enumerated(EnumType.STRING)
    private LoginMethod method;
    private String profileImage;

    @Override
    public String toString() {
        return "Buyer(id="+this.getUid()+", method="+this.method+")";
    }
}
