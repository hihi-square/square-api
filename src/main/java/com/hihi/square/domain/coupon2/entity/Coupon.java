//package com.hihi.square.domain.coupon2.entity;
//
//import com.hihi.square.common.BaseEntity;
//import com.hihi.square.common.CommonStatus;
//import com.hihi.square.domain.store.entity.Store;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "coupon")
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Getter
//@ToString
//@EntityListeners(AuditingEntityListener.class)
//public class Coupon extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Integer id;
//    String name;
//    @Column(name = "start_at")
//    LocalDateTime startAt;
//    @Column(name="expired_at")
//    LocalDateTime expiredAt;
//    Double rate;
//    @Column(name = "min_disount_price")
//    Integer minPrice;
//    @Column(name = "max_disount_price")
//    Integer maxPrice;
//    @Enumerated(EnumType.STRING)
//    CommonStatus status;
//    @Column(name = "used_time")
//    LocalDateTime usedTime;
//
//    @ManyToOne
//    @JoinColumn(name = "sto_id")
//    Store store;
////    @ManyToOne
////    @JoinColumn(name = "sac_id")
////    Associate associate;
//
//}
