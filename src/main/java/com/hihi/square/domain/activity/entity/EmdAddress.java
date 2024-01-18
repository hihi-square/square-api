package com.hihi.square.domain.activity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name="emd_address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class EmdAddress {
    @Id
    private Long id; // bcode
    private String name;
    @ManyToOne
    @JoinColumn(name = "sigg_id")
    private SiggAddress siggAddress;

}
