package com.hihi.square.domain.partnership.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "partnership_stop")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class PartnershipStop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="spa_id")
    private Partnership partnership;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sto_id")
    private Store store;

    private boolean isFinished; // 정지가 취소되었는지.
    private LocalDateTime finishedTime; // 정지 취소 시간. null 가능

    public void updateToFinish() {
        this.isFinished = true;
        this.finishedTime = LocalDateTime.now();
    }
}
