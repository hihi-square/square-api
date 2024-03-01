package com.hihi.square.domain.store.repository;

import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.dibs.entity.QDibs;
import com.hihi.square.domain.menu.entity.QMenu;
import com.hihi.square.domain.order.entity.OrderStatus;
import com.hihi.square.domain.order.entity.QOrders;
import com.hihi.square.domain.partnership.entity.PartnershipAcceptState;
import com.hihi.square.domain.partnership.entity.QPartnership;
import com.hihi.square.domain.store.dto.response.StoreSearchInfoDto;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.timesale.entity.QTimeSale;
import com.hihi.square.domain.timesale.entity.TimeSaleStatus;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.JpaSubQuery;
import org.springframework.stereotype.Repository;
import static com.hihi.square.domain.store.entity.QStore.store;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StoreCustomRepositoryImpl implements StoreCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Store> searchStoreList(List<EmdAddress> emdAddressList, Buyer buyer, Integer category, String orderBy, Boolean timesale, Boolean partnership, Boolean dibs, Double longitude, Double latitude) {

        JPQLQuery<Store> query = queryFactory.selectDistinct(store)
                .from(store)
                .where(
                        getCategory(category),
                        emdAddressIn(emdAddressList),
                        existsTimesale(timesale),
                        existsPartnership(partnership),
                        isDibs(dibs, buyer)
                )
                ;
        if (!orderBy.equals("default")) query.orderBy(searchOrderBy(orderBy, longitude, latitude));
        return query.fetch();
    }

    @Override
    public List<StoreSearchInfoDto> searchStoreDtoList(List<EmdAddress> emdAddressList, Buyer buyer, Integer category, String orderBy, Boolean timesale, Boolean partnership, Boolean dibs, Double longitude, Double latitude) {
//        return null;
        JPQLQuery<StoreSearchInfoDto> query = queryFactory.selectDistinct(
                Projections.constructor(StoreSearchInfoDto.class, // Projections.constructor를 사용하여 DTO를 생성
                        store.usrId.as("usrId"),
                        store.name.as("name"),
                        store.category.id.as("category"),
                        getTimesale().as("isTs"),
                        getPartnership().as("isPn"),
                        getDibs(buyer).as("isDibs"),
                        store.profileImage.as("profileImage"),
                        store.latitude.as("latitude"),
                        store.longitude.as("longitude")
                )
                )
                .from(store);
        if (orderBy.equals("popularity")) query.leftJoin(store.ordersList, QOrders.orders).on(QOrders.orders.status.eq(OrderStatus.ACCEPT));

                query.where(
                        getCategory(category),
                        emdAddressIn(emdAddressList),
                        existsTimesale(timesale),
                        existsPartnership(partnership),
                        isDibs(dibs, buyer)
                )
                ;
        if (!orderBy.equals("default")) {
            if (orderBy.equals("popularity")) query.groupBy(store.usrId);
            query.orderBy(searchOrderBy(orderBy, longitude, latitude));
        }
        return query.fetch();
    }

    private BooleanExpression getCategory(Integer category) {
        if (category == null) return null;
        else return store.category.id.eq(category);
    }

    private BooleanExpression emdAddressIn(List<EmdAddress> emdAddressList) {
        return store.address.in(emdAddressList);
    }

    private OrderSpecifier<?> searchOrderBy(String orderBy, double longitude, double latitude) {
        if (orderBy.equals("distance")) {
            return new OrderSpecifier<>(Order.ASC, Expressions.numberTemplate(Double.class,
                    "6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1})))",
                    store.latitude, store.longitude, latitude, longitude));
        } else if (orderBy.equals("popularity")) {
            LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
//            return store.ordersList.any().createdAt.after(lastMonth).count().desc(); // 최근 주문수 많은 순대로 정렬
            NumberTemplate<Long> orderCount = Expressions.numberTemplate(Long.class,
                    "{0}", store.ordersList.any().createdAt.after(lastMonth).count());
            return new OrderSpecifier<>(Order.DESC, orderCount);
        }
        return null;
    }

    private BooleanExpression getTimesale() {
        QTimeSale timeSale = QTimeSale.timeSale;
        LocalDateTime now = LocalDateTime.now();

        return JPAExpressions.selectOne()
                .from(timeSale)
                .where(
                        timeSale.store.eq(store),
                        timeSale.startedAt.before(now),
                        timeSale.finishedAt.after(now),
                        timeSale.realFinishedAt.isNull(),
                        timeSale.status.eq(TimeSaleStatus.ONGOING)
                )
                .exists();
    }

    private BooleanExpression existsTimesale(boolean isTimesale) {
        if (!isTimesale) return null;
        else return getTimesale();
    }

    private BooleanExpression getPartnership() {
        QPartnership partnership = QPartnership.partnership;
        LocalDateTime now = LocalDateTime.now();
        return
                JPAExpressions.selectOne().from(partnership)
                        .where(
                        partnership.issStore.eq(store), // 현재 제휴 쿠폰을 발급중이면서
                        partnership.startTime.before(now),
                        partnership.finishTime.after(now),
                        partnership.acceptState.eq(PartnershipAcceptState.NORMAL))
                        .exists()
                ;
    }

    private BooleanExpression existsPartnership(boolean isPartnership) {
        if (!isPartnership) return null;
        else return getPartnership();
    }

    private BooleanExpression getDibs(Buyer buyer) { // 좋아요는 추후 추가
        QDibs dibs = QDibs.dibs;
        return
                JPAExpressions.selectOne().from(dibs)
                                .where(dibs.buyer.eq(buyer),
                                        dibs.store.eq(store)).exists();
    }

    private BooleanExpression isDibs(boolean isDibs, Buyer buyer) {
        if (!isDibs) return null;
        else return getDibs(buyer);
    }

}
