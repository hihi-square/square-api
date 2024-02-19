package com.hihi.square.domain.order.event;

import com.hihi.square.domain.order.entity.Orders;
import com.hihi.square.domain.user.entity.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.hihi.square.domain.order.entity.OrderStatus;
import com.hihi.square.domain.order.service.OrderService;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.global.sse.SseService;
import com.hihi.square.global.sse.entity.NotificationType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener implements ApplicationListener<OrderEvent> {

	private final SseService sseService;
	private final OrderService orderService;

	@Override
	@Transactional
	public void onApplicationEvent(OrderEvent event) {
		Orders order = event.getOrder();
		OrderStatus status = event.getStatus();
		log.info("status : {}", status);
		//결제 완료 시에 가게에 알림 전송
		if (status == OrderStatus.WAIT) {
			Store store = order.getStore();
			sseService.send(store.getUsrId(), NotificationType.READY, "order", event.getContent(),
				store.getStoreName());
		}
		//가게에서 주문 수락 or 취소 시
		else if (status == OrderStatus.ACCEPT) {
			User user = order.getBuyer();
			sseService.send(user.getUsrId(), NotificationType.ACCEPT, "order", event.getContent(),
				order.getStore().getStoreName());
		} else if (status == OrderStatus.REJECT) {
			User user = order.getBuyer();
			sseService.send(user.getUsrId(), NotificationType.REJECT, "order", event.getContent(),
				order.getStore().getStoreName());
		}
		//고객이 픽업을 완료했을 때
//		else if (status == OrderStatus.PICKUP_COMPLETE) {
//			Customer customer = order.getCustomer();
//			sseService.send(customer, NotificationType.COMPLETED, "pickup", event.getContent(),
//				order.getStore().getStoreName());
//		}
	}
}
