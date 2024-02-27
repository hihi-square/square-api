package com.hihi.square.domain.store.repository;

import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.partnership.entity.PartnershipAcceptState;
import com.hihi.square.domain.partnership.entity.QPartnership;
import com.hihi.square.domain.store.dto.response.StoreSearchInfoDto;
import com.hihi.square.domain.store.entity.Store;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.hihi.square.domain.store.entity.QStore.store;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreCustomRepositoryImpl implements StoreCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Store> searchStoreList(List<EmdAddress> emdAddressList, Buyer buyer, String orderBy, Boolean timesale, Boolean partnership, Boolean dibs, Double longitude, Double latitude) {

        JPQLQuery<Store> query = queryFactory.selectDistinct(store)
                .from(store)
                .where(
                        emdAddressIn(emdAddressList),
                        existsTimesale(timesale),
                        existsPartnership(partnership),
                        isDibs(dibs, buyer)
                ).orderBy(
                        searchOrderBy(orderBy, longitude, latitude)
                )
                ;
        return query.fetch();
    }

    @Override
    public List<StoreSearchInfoDto> searchStoreDtoList(List<EmdAddress> emdAddressList, Buyer buyer, String orderBy, Boolean timesale, Boolean partnership, Boolean dibs, Double longitude, Double latitude) {

        JPQLQuery<StoreSearchInfoDto> query = queryFactory.selectDistinct(
                Projections.constructor(StoreSearchInfoDto.class, // Projections.constructor를 사용하여 DTO를 생성
                        store.usrId.as("usrId"),
                        store.name.as("name"),
                        existsTimesale().as("isTs"),
                        existsPartnership().as("isPn"),
                        isDibs(dibs, buyer).as("isDibs"),
                        store.profileImage.as("profileImage"),
                        store.latitude.as("latitude"),
                        store.longitude.as("longitude")
                ))
                .from(store)
                .where(
                        emdAddressIn(emdAddressList),
                        existsTimesale(timesale),
                        existsPartnership(partnership),
                        isDibs(dibs, buyer)
                ).orderBy(
                        searchOrderBy(orderBy, longitude, latitude)
                )
                ;
        return query.fetch();
    }


    private BooleanExpression emdAddressIn(List<EmdAddress> emdAddressList) {

        return store.address.in(emdAddressList);
    }

    private OrderSpecifier<Long> searchOrderBy(String orderBy, double longitude, double latitude) {
        if (orderBy.equals("distance")) {
//            return Expressions.asNumber(NumberTemplate.create("FUNCTION('earth_distance', {0}, {1}, {2}, {3})", store.latitude, store.longitude, latitude, longitude)).as(Number.class).asc();
        } else if (orderBy.equals("popularity")) {
            LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
            return store.ordersList.any().createdAt.after(lastMonth).count().desc(); // 최근 주문수 많은 순대로 정렬
        }
        return null;
    }
    private BooleanExpression existsTimesale() { // 타임세일 추후 추가
        return null;
    }
    private BooleanExpression existsPartnership() {
        LocalDateTime now = LocalDateTime.now();
        return QPartnership.partnership.issStore.isNotNull().and(QPartnership.partnership.startTime.before(now).and(QPartnership.partnership.finishTime.after(now).and(QPartnership.partnership.acceptState.eq(PartnershipAcceptState.NORMAL))));
    }

    private BooleanExpression existsTimesale(boolean timesale) { // 타임세일 추후 추가
        if (!timesale) return null;
        return null;
    }
    private BooleanExpression existsPartnership(boolean partnership) {
        if (!partnership) return null;
        LocalDateTime now = LocalDateTime.now();
        return QPartnership.partnership.issStore.isNotNull().and(QPartnership.partnership.startTime.before(now).and(QPartnership.partnership.finishTime.after(now).and(QPartnership.partnership.acceptState.eq(PartnershipAcceptState.NORMAL))));
    }

    private BooleanExpression isDibs(boolean isDibs, Buyer buyer) { // 좋아요는 추후 추가
        if (!isDibs) return null;
        return null;
    }
    private BooleanExpression isDibs(Buyer buyer) { // 좋아요는 추후 추가
        return null;
    }
}
