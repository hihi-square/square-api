package com.hihi.square.domain.activity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="sigg_address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class SiggAddress {
    @Id
    private Long id; // 행정코드
    private String name;

}
