package com.hihi.square.domain.menu.dto;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menu.entity.SaleType;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import com.hihi.square.domain.partnership.dto.response.PartnershipCouponDto;
import com.hihi.square.domain.partnership.dto.response.PartnershipRes;
import com.hihi.square.domain.timesale.dto.TimeSaleDto;
import com.hihi.square.domain.timesale.dto.TimeSaleMenuDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerMenuDto {
    Integer id;
    String name;
    Integer price;
    String image;
    Integer sequence;
    CommonStatus status;
    Boolean isPopular;
    Boolean isRepresentative;
    SaleType saleType;
    TimeSaleMenuDto saleInfo;
    Boolean isCoupon;
    PartnershipCouponDto coupon;

    public static BuyerMenuDto toRes(Menu menu, SaleType saleType, TimeSaleMenuDto saleInfo, Boolean isCoupon, PartnershipCouponDto coupon){
        return BuyerMenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .image(menu.getImage())
                .sequence(menu.getSequence())
                .status(menu.getStatus())
                .isPopular(menu.getIsPopular())
                .isRepresentative(menu.getIsRepresentative())
                .saleType(saleType)
                .saleInfo(saleInfo)
                .isCoupon(isCoupon)
                .coupon(coupon)
                .build();
    }
}
