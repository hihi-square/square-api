package com.hihi.square.domain.order.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.order.dto.OrderDto;
import com.hihi.square.domain.partnership.entity.UserCoupon;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ord_id")
    Integer id;
    Integer totalPrice;
    Integer finalPrice;
    @Column(name = "request")
    String requestInfo;
    @Enumerated(EnumType.STRING)
    OrderStatus status;
    Integer totalCnt;
    String rejectReason;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    Buyer buyer;
    @ManyToOne
    @JoinColumn(name = "sto_id")
    Store store;
    @OneToOne
    @JoinColumn(name = "uc_id")
    UserCoupon userCoupon;

    @OneToMany
    @JoinColumn(name="order_ord_id")
    List<OrderMenu> menus = new ArrayList<>();

    public static Orders toEntity(OrderDto orderDto, Buyer buyer, Store store, UserCoupon userCoupon){
        return Orders.builder()
                .id(orderDto.getId())
                .totalPrice(orderDto.getTotalPrice())
                .finalPrice(orderDto.getFinalPrice())
                .requestInfo(orderDto.getRequest())
                .totalCnt(orderDto.getTotalCnt())
                .rejectReason(orderDto.getRejectReason())
                .buyer(buyer)
                .store(store)
                .userCoupon(userCoupon)
                .build();
    }
}
