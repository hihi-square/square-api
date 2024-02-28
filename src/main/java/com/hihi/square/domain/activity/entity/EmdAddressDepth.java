package com.hihi.square.domain.activity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="emd_address_depth")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class EmdAddressDepth {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "emd_id1")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmdAddress emdAddress1;

    @JoinColumn(name = "emd_id2")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmdAddress emdAddress2;

    private int depth;
}
