package com.hihi.square.domain.address.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "emd_address")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmdAddress {
    @Id
    private Long id; // bcode
    private String name;
    @ManyToOne
    @JoinColumn(name = "sigg_id")
    private SiggAddress siggAddress;

}
