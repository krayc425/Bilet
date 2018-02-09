package com.krayc.controller;

import com.krayc.model.*;
import com.krayc.service.*;
import com.krayc.util.OrderStatus;
import com.krayc.util.OrderType;
import com.krayc.vo.EventSeatVO;
import com.krayc.vo.MessageVO;
import com.krayc.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "member/order")
public class MemberOrderController extends BaseController {

    @Autowired
    private EventService eventService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "{mid}/event/{eid}/chooseSeat", method = RequestMethod.GET)
    public ModelAndView orderChooseSeat(@PathVariable("eid") Integer eid, @PathVariable("mid") Integer mid) {
        ModelAndView modelAndView = new ModelAndView("member/order/memberOrderChooseSeat");

        MemberEntity memberEntity = memberService.findByMid(mid);
        modelAndView.addObject("member", memberEntity);

        EventEntity eventEntity = eventService.findByEid(eid);
        modelAndView.addObject("event", eventEntity);

        ArrayList<EventSeatVO> eventSeatVOS = new ArrayList<EventSeatVO>();
        for (EventSeatEntity eventSeatEntity : eventService.findEventSeatsByEid(eventEntity)) {
            EventSeatVO eventSeatVO = new EventSeatVO(eventSeatEntity);
            eventSeatVO.setNumber(eventSeatVO.getNumber() - eventService.unavailableSeatNumberByEvent(eventSeatEntity));
            eventSeatVOS.add(eventSeatVO);
        }
        modelAndView.addObject("eventSeats", eventSeatVOS);

        modelAndView.addObject("coupons", couponService.findAvailableCouponsByMember(memberEntity));

        return modelAndView;
    }

    @RequestMapping(value = "{mid}/event/{eid}/chooseSeatPost", method = RequestMethod.GET)
    public ModelAndView orderChooseSeatPost(@PathVariable("eid") Integer eid, @PathVariable("mid") Integer mid, HttpServletRequest request) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        EventEntity eventEntity = eventService.findByEid(eid);

        List<EventSeatEntity> eventSeatEntities = eventService.findEventSeatsByEid(eventEntity);

        Integer totalSeatNumber = 0;
        for (EventSeatEntity eventSeatEntity : eventSeatEntities) {
            totalSeatNumber += Integer.parseInt(request.getParameter("eventSeatNumber" + eventSeatEntity.getEsid()));
        }

        if (totalSeatNumber == 0) {
            MessageVO messageVO = new MessageVO(false, "请至少请购买 1 张票");
            return this.handleMessage(messageVO, "redirect:/member/" + mid + "/order/" + eid + "/chooseSeat");
        }

        if (totalSeatNumber > 6) {
            MessageVO messageVO = new MessageVO(false, "总购票数量不能大于 6 张");
            return this.handleMessage(messageVO, "redirect:/member/" + mid + "/order/" + eid + "/chooseSeat");
        }

        // 没有问题，下订单
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setEventByEid(eventEntity);
        orderEntity.setMemberByMid(memberEntity);
        orderEntity.setStatus(OrderStatus.ORDER_CREATED);
        orderEntity.setType(OrderType.CHOOSE_SEAT);
        String couponString = request.getParameter("memberCouponCid");
        if (couponString != null && !couponString.equals("")) {
            try {
                orderEntity.setMemberCouponEntity(couponService.findByMcid(Integer.parseInt(couponString)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        ArrayList<OrderEventSeatEntity> orderEventSeatEntities = new ArrayList<OrderEventSeatEntity>();
        for (EventSeatEntity eventSeatEntity : eventSeatEntities) {
            Integer eventSeatCount = Integer.parseInt(request.getParameter("eventSeatNumber" + eventSeatEntity.getEsid()));
            if (eventSeatCount > 0) {
                for (int i = 0; i < eventSeatCount; i++) {
                    OrderEventSeatEntity orderEventSeatEntity = new OrderEventSeatEntity();
                    orderEventSeatEntity.setIsValid(0);
                    orderEventSeatEntity.setEventSeatByEsid(eventSeatEntity);
                    orderEventSeatEntities.add(orderEventSeatEntity);
                }
            }
        }

        orderService.createOrder(orderEntity, orderEventSeatEntities);

        return new ModelAndView("redirect:/member/order/" + mid);
    }

    @RequestMapping(value = "{mid}/event/{eid}/randomSeat", method = RequestMethod.GET)
    public ModelAndView orderRandomSeat(@PathVariable("eid") Integer eid, @PathVariable("mid") Integer mid) {
        ModelAndView modelAndView = new ModelAndView("member/order/memberOrderRandomSeat");

        MemberEntity memberEntity = memberService.findByMid(mid);
        modelAndView.addObject("member", memberEntity);

        EventEntity eventEntity = eventService.findByEid(eid);
        modelAndView.addObject("event", eventEntity);

        modelAndView.addObject("eventSeats", eventService.findEventSeatsByEid(eventEntity));

        modelAndView.addObject("coupons", couponService.findAvailableCouponsByMember(memberEntity));

        return modelAndView;
    }

    @RequestMapping(value = "{mid}", method = RequestMethod.GET)
    public String orders(@PathVariable("mid") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        modelMap.addAttribute("member", memberEntity);

        ArrayList<OrderVO> orderVOS = new ArrayList<OrderVO>();
        for (OrderEntity orderEntity : memberEntity.getOrders()) {
            orderVOS.add(new OrderVO(orderEntity));
        }
        modelMap.addAttribute("orders", orderVOS);

        return "member/order/memberOrders";
    }

    @RequestMapping(value = "{mid}/pay/{oid}", method = RequestMethod.GET)
    public ModelAndView payOrder(@PathVariable("mid") Integer mid, @PathVariable("oid") Integer oid) {
        Boolean result = orderService.payOrder(orderService.findByOid(oid), memberService.findByMid(mid));
        if (result) {
            return new ModelAndView("redirect:/member/order/" + mid);
        } else {
            ModelAndView modelAndView = new ModelAndView("member/memberCharge");
            MemberEntity memberEntity = memberService.findByMid(mid);
            modelAndView.addObject("member", memberEntity);
            modelAndView.addObject("bankAccount", memberService.findBankAccountEntity(memberEntity.getBankAccount()));
            modelAndView.addObject("error", "您的余额不足，请先充值");
            return modelAndView;
        }
    }

    @RequestMapping(value = "{mid}/cancel/{oid}", method = RequestMethod.GET)
    public String cancelOrder(@PathVariable("mid") Integer mid, @PathVariable("oid") Integer oid) {
        OrderEntity orderEntity = orderService.findByOid(oid);
        orderService.cancelOrder(orderEntity);
        return "redirect:/member/order/" + mid;
    }

    @RequestMapping(value = "{mid}/refund/{oid}", method = RequestMethod.GET)
    public String refundOrder(@PathVariable("mid") Integer mid, @PathVariable("oid") Integer oid) {
        orderService.refundOrder(orderService.findByOid(oid), memberService.findByMid(mid));
        return "redirect:/member/order/" + mid;
    }

    @RequestMapping(value = "{mid}/detail/{oid}", method = RequestMethod.GET)
    public String orderDetail(@PathVariable("mid") Integer mid, @PathVariable("oid") Integer oid, ModelMap modelMap) {
        modelMap.addAttribute("member", memberService.findByMid(mid));

        OrderEntity orderEntity = orderService.findByOid(oid);
        modelMap.addAttribute("order", new OrderVO(orderEntity));

        modelMap.addAttribute("seats", orderEntity.getOrderEventSeats());

        return "member/order/memberOrderDetail";
    }

}
