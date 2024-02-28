package com.hihi.square.domain.timesale.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.timesale.dto.TimeSaleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "sale")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class TimeSale extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "started_at")
    LocalDateTime startedAt;
    @Column(name = "finished_at")
    LocalDateTime finishedAt;
    @Column(name = "real_finished_at")
    LocalDateTime realFinishedAt;
    @Enumerated(EnumType.STRING)
    TimeSaleStatus status;

    @ManyToOne
    @JoinColumn(name = "sto_id")
    Store store;

    public static TimeSale toEntity(TimeSaleDto timeSaleDto, Store store){
        return TimeSale.builder()
                .id(timeSaleDto.getId())
                .startedAt(timeSaleDto.getStartedAt())
                .finishedAt(timeSaleDto.getFinishedAt())
                .realFinishedAt(timeSaleDto.getRealFinishedAt())
                .status(timeSaleDto.getStatus() == null ? TimeSaleStatus.PREPARATION : timeSaleDto.getStatus())
                .store(store)
                .build();
    }

}
