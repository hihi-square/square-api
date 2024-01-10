package com.hihi.square.domain.address.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sigg_address")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SiggAddress {
    @Id
    private Long id; // 행정코드
    private String name;

}
