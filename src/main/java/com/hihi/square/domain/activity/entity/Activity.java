package com.hihi.square.domain.activity.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.buyer.entity.Buyer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="activity")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "emd_id")
    private EmdAddress emdAddress;

    private Double latitude;
    private Double longitude;

    @Min(0) @Max(2)
    private Integer depth; // 해당 지역 기준으로 확장되는 칸 수

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private Buyer buyer;

}
