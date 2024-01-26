package com.hihi.square.domain.order.event;

import com.hihi.square.domain.order.entity.OrderStatus;
import com.hihi.square.domain.order.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
@Slf4j
public class OrderEvent extends ApplicationEvent {
	private OrderStatus status;
	private Orders order;
	private String content;

	public OrderEvent(OrderStatus status, Orders order, String content) {
		super(order);
		this.status = status;
		this.order = order;
		this.content = content;
	}
}
