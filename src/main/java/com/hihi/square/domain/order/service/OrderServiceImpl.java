package com.hihi.square.domain.order.service;

import com.hihi.square.common.CommonStatus;
//import com.hihi.square.domain.coupon2.entity.Coupon;
//import com.hihi.square.domain.coupon2.entity.UserCoupon;
//import com.hihi.square.domain.coupon2.repository.CouponRepository;
//import com.hihi.square.domain.coupon2.repository.UserCouponRepository;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.repository.BuyerRepository;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menu.entity.MenuOption;
import com.hihi.square.domain.menu.repository.MenuOptionRepository;
import com.hihi.square.domain.menu.repository.MenuRepository;
import com.hihi.square.domain.order.dto.OrderDto;
import com.hihi.square.domain.order.dto.OrderMenuDto;
import com.hihi.square.domain.order.entity.OrderMenu;
import com.hihi.square.domain.order.dto.OrderMenuOptionDto;
import com.hihi.square.domain.order.entity.OrderMenuOption;
import com.hihi.square.domain.order.entity.OrderStatus;
import com.hihi.square.domain.order.entity.Orders;
import com.hihi.square.domain.order.event.OrderEvent;
import com.hihi.square.domain.order.repository.OrderMenuOptionRepository;
import com.hihi.square.domain.order.repository.OrderMenuRepository;
import com.hihi.square.domain.order.repository.OrderRepository;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.repository.PartnershipRepository;
import com.hihi.square.domain.partnership.repository.UserCouponRepository;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.entity.UserStatus;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.InvalidValueException;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hihi.square.domain.partnership.entity.UserCoupon;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuOptionRepository orderMenuOptionRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final UserCouponRepository userCouponRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PartnershipRepository partnershipRepository;
    private final BuyerRepository buyerRepository;

    @Transactional
    @Override
    public void addOrder(Integer usrId, OrderDto orderDto) {
        //1. 유저, 상점 존재 확인
        Buyer buyer = buyerRepository.findById(usrId).orElseThrow(
                () -> new UserNotFoundException("User Not Found"));
        Store store = storeRepository.findById(orderDto.getStoId(), UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));

        //2. 유저 쿠폰 존재 여부 확인
        //2-1. 현재 사용 가능한 쿠폰인지 확인
        UserCoupon userCoupon = null;
        if(orderDto.getCouponId() != null){
            userCoupon = userCouponRepository.findByIdAndBuyerAndExpiredTimeBeforeAndIsUsed(orderDto.getCouponId(), buyer, LocalDateTime.now(), false).orElseThrow(
                    () -> new EntityNotFoundException("User Coupon Not Found"));
        }

        //3. 주문 등록
        Orders order = Orders.toEntity(orderDto, buyer, store, userCoupon);
        order = orderRepository.save(order);
        orderRepository.updateStatus(order.getId(), OrderStatus.REGISTER);

        //4. 주문 메뉴 + 주문 메뉴 옵션 등록
        List<OrderMenuDto> orderMenuDtos = orderDto.getMenuList();
        
        if(orderMenuDtos != null){
            for(OrderMenuDto omd: orderMenuDtos){
                //4-1. 메뉴 존재 여부 + 저장
                Menu menu = menuRepository.findById(omd.getMenuId(), CommonStatus.ACTIVE).orElseThrow(
                        () -> new EntityNotFoundException("Menu Not Found"));
                OrderMenu orderMenu = OrderMenu.toEntity(omd, order, menu, buyer);
                orderMenuRepository.save(orderMenu);

                //4-2. 메뉴 옵션 존재 여부 + 저장
                List<OrderMenuOptionDto> options = omd.getOptions();
                if(options != null){
                    for(OrderMenuOptionDto omod : options){
                        MenuOption menuOption = menuOptionRepository.findById(omod.getOptionId(), CommonStatus.ACTIVE).orElseThrow(
                                () -> new EntityNotFoundException("Menu Option Not Found")
                        );
                        OrderMenuOption omo = OrderMenuOption.toEntity(omod, menuOption, orderMenu, buyer);
                        orderMenuOptionRepository.save(omo);
                    }
                }
            }    
        }
    }

    //유저
    @Override
    public OrderDto selectOrder(Integer usrId, Integer orderId) {
        //1. 유저 존재 확인
        Buyer buyer = buyerRepository.findById(usrId).orElseThrow(
                () -> new UserNotFoundException("User Not Found"));
        //2. 주문 존재 확인
        Orders order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order Not Found"));
        //2-1. 주문 메뉴 리스트 가져오기 --> 사라진 메뉴에 대해선..
        List<OrderMenu> orderMenus = orderMenuRepository.findAllByOrder(orderId);
        List<OrderMenuDto> orderMenuDtos = new ArrayList<>();

        if(orderMenus != null){
            for(OrderMenu om : orderMenus){
                Menu menu = om.getMenu();
                OrderMenuDto omd = OrderMenuDto.toRes(om, menu);
                
                //2-1-1. 옵션 가져오기 --> 사라진 옵션에 대해선..
                List<OrderMenuOption> orderMenuOptions = orderMenuOptionRepository.findAllByMenu(om.getId());
                List<OrderMenuOptionDto> orderMenuOptionDtos = new ArrayList<>();
                if(orderMenuOptions != null){
                    for(OrderMenuOption omo : orderMenuOptions){
                        MenuOption mo = omo.getMenuOption();
                        OrderMenuOptionDto omod = OrderMenuOptionDto.toRes(omo, mo);
                        orderMenuOptionDtos.add(omod);
                    }
                }
                omd.setOptions(orderMenuOptionDtos);
                orderMenuDtos.add(omd);
            }
        }

        //3. 주문 dto 생성
        OrderDto orderDto = OrderDto.toRes(order, orderMenuDtos);

        return orderDto;
    }

    @Override
    public List<OrderDto> selectOrdersByUserId(Integer usrId) {
        //1. 유저 존재 확인
        Buyer buyer = buyerRepository.findById(usrId).orElseThrow(
                () -> new UserNotFoundException("User Not Found"));
        
        //2. 주문 목록 조회
        List<Orders> orders = orderRepository.findAllByUserId(usrId);
        List<OrderDto> orderDtos = new ArrayList<>();

        if(orders == null) return null;
        for(Orders order : orders){
            //2-1. 주문 상품 조회
            List<OrderMenu> orderMenus = orderMenuRepository.findAllByOrder(order.getId());
            List<OrderMenuDto> orderMenuDtos = new ArrayList<>();

            if(orderMenus != null){
                for(OrderMenu om : orderMenus){
                    Menu menu = om.getMenu();
                    OrderMenuDto omd = OrderMenuDto.toRes(om, menu);

                    //2-1-1. 옵션 가져오기 --> 사라진 옵션에 대해선..
                    List<OrderMenuOption> orderMenuOptions = orderMenuOptionRepository.findAllByMenu(om.getId());
                    List<OrderMenuOptionDto> orderMenuOptionDtos = new ArrayList<>();
                    if(orderMenuOptions != null){
                        for(OrderMenuOption omo : orderMenuOptions){
                            MenuOption mo = omo.getMenuOption();
                            OrderMenuOptionDto omod = OrderMenuOptionDto.toRes(omo, mo);
                            orderMenuOptionDtos.add(omod);
                        }
                    }
                    omd.setOptions(orderMenuOptionDtos);
                    orderMenuDtos.add(omd);
                }
            }
            
            OrderDto orderDto = OrderDto.toRes(order, orderMenuDtos);
            orderDtos.add(orderDto);
        }
        
        return orderDtos;
    }
    
    //가게
    @Override
    public List<OrderDto> selectOrdersByStatus(Integer stoId, OrderStatus type) {
        //1. 유저 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("User Not Found"));
        
        //2. 타입별 주문 조회 -> 대기, 수락, 거절
        List<Orders> orders = orderRepository.findAllByUserAndType(stoId, type);
        List<OrderDto> orderDtos = new ArrayList<>();

        if(orders == null) return null;
        for(Orders order : orders){
            //2-1. 주문 상품 조회
            List<OrderMenu> orderMenus = orderMenuRepository.findAllByOrder(order.getId());
            List<OrderMenuDto> orderMenuDtos = new ArrayList<>();

            if(orderMenus != null){
                for(OrderMenu om : orderMenus){
                    Menu menu = om.getMenu();
                    OrderMenuDto omd = OrderMenuDto.toRes(om, menu);

                    //2-1-1. 옵션 가져오기 --> 사라진 옵션에 대해선..
                    List<OrderMenuOption> orderMenuOptions = orderMenuOptionRepository.findAllByMenu(om.getId());
                    List<OrderMenuOptionDto> orderMenuOptionDtos = new ArrayList<>();
                    if(orderMenuOptions != null){
                        for(OrderMenuOption omo : orderMenuOptions){
                            MenuOption mo = omo.getMenuOption();
                            OrderMenuOptionDto omod = OrderMenuOptionDto.toRes(omo, mo);
                            orderMenuOptionDtos.add(omod);
                        }
                    }
                    omd.setOptions(orderMenuOptionDtos);
                    orderMenuDtos.add(omd);
                }
            }

            OrderDto orderDto = OrderDto.toRes(order, orderMenuDtos);
            orderDtos.add(orderDto);
        }

        return orderDtos;
    }

    @Override
    @Transactional
    public void acceptOrder(Integer stoId, Integer orderId) {
        //1. 유저 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));

        //2. 주문 존재 확인
        Orders order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order Not Found"));
        if(order.getStatus() == OrderStatus.REGISTER)
            throw new InvalidValueException("Order Status Not WAIT");

        //3. 주문 상태 변경
        orderRepository.updateStatus(orderId, OrderStatus.ACCEPT);

        //3-1. 발급 가능한 쿠폰 존재시 쿠폰 발급
        for(OrderMenu m : order.getMenus()) {
            Optional<Partnership> partnership = partnershipRepository.findByMenuAndProgress(m.getMenu(), LocalDateTime.now());
            if (partnership.isPresent()) {
                userCouponRepository.save(UserCoupon.toEntity(partnership.get(), order, LocalDateTime.now().plusMinutes(partnership.get().getAvailableTime())));
            }
        }

        //4. 주문 상태 변경 시 -> 유저한테 알림
        eventPublisher.publishEvent(new OrderEvent(OrderStatus.ACCEPT, order, "주문이 수락되었습니다."));
    }

    @Override
    @Transactional
    public void rejectOrder(Integer stoId, Integer orderId, OrderDto orderDto) {
        //1. 유저 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));

        //2. 주문 존재 확인
        Orders order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order Not Found"));
        if(order.getStatus() == OrderStatus.REGISTER)
            throw new InvalidValueException("Order Status Not WAIT");

        //3. 주문 상태 변경
        orderRepository.updateStatusToReject(orderId, OrderStatus.REJECT, orderDto.getRejectReason());

        //4. 주문 상태 변경 시 -> 유저한테 알림
        eventPublisher.publishEvent(new OrderEvent(OrderStatus.REJECT, order, "주문이 거절되었습니다."));
    }
}
