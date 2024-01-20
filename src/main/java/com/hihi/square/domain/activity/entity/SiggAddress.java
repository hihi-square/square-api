package com.hihi.square.domain.activity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="sigg_address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class SiggAddress {
    @Id
    private Long id; // 행정코드
    private String name;
}
