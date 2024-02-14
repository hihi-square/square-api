package com.hihi.square.domain.activity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="activity_depth")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class ActivityDepth {
    @Id
    private Integer id;

    @JoinColumn(name = "emd_id1")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmdAddress emdAddress1;

    @JoinColumn(name = "emd_id2")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmdAddress emdAddress2;
}
