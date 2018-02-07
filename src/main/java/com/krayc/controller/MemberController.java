package com.krayc.controller;

import com.krayc.model.*;
import com.krayc.service.*;
import com.krayc.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "member")
public class MemberController extends BaseController {

    @Autowired
    private EventService eventService;

    @Autowired
    private LevelService levelService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addMember() {
        return "member/addMember";
    }

    @RequestMapping(value = "addPost", method = RequestMethod.POST)
    public String addMemberPost(@ModelAttribute("member") MemberEntity memberEntity) {
        memberService.addMember(memberEntity);
        return "redirect:/";
    }

    @RequestMapping(value = "show/{id}", method = RequestMethod.GET)
    public String showMember(@PathVariable("id") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity1 = memberService.findByMid(mid);
        MemberInfoVO memberInfoVO = new MemberInfoVO(
                memberEntity1.getMid(),
                memberEntity1.getEmail(),
                memberEntity1.getBankAccount(),
                levelService.findLevelEntityWithPoint(memberEntity1.getTotalPoint()).getDescription(),
                memberEntity1.getTotalPoint(),
                memberEntity1.getCurrentPoint(),
                memberEntity1.getIsTerminated() == Byte.valueOf("1") ? "已被注销" : "可以使用",
                memberEntity1.getIsEmailPassed() == Byte.valueOf("1") ? "已经激活" : "尚未激活",
                memberService.findBalance(memberEntity1.getBankAccount()));
        modelMap.addAttribute("member", memberInfoVO);

        modelMap.addAttribute("events", eventService.findAllEvents());

        return "member/memberDetail";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView loginMember() {
        return new ModelAndView("member/loginMember");
    }

    @RequestMapping(value = "loginPost", method = RequestMethod.POST)
    public ModelAndView loginMemberPost(@ModelAttribute("member") MemberEntity memberEntity, HttpServletRequest request) {
        switch (memberService.login(memberEntity)) {
            case LOGIN_SUCCESS:
                MemberEntity memberEntity1 = memberService.findByEmail(memberEntity.getEmail());

                HttpSession session = request.getSession(false);
                session.setAttribute("member", memberEntity1);

                return new ModelAndView("redirect:/member/show/" + memberEntity1.getMid());
            case LOGIN_NOT_ACTIVATED: {
                MessageVO messageVO = new MessageVO(false, "账户尚未被激活");
                return this.handleMessage(messageVO, "member/loginMember");
            }
            case LOGIN_WRONG_EMAIL_PASSWORD: {
                MessageVO messageVO = new MessageVO(false, "邮箱或密码错误");
                return this.handleMessage(messageVO, "member/loginMember");
            }
            case LOGIN_TERMINATED: {
                MessageVO messageVO = new MessageVO(false, "账户已经被取消");
                return this.handleMessage(messageVO, "member/loginMember");
            }
            default:
                return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logoutMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("member", null);
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "activate/{id}", method = RequestMethod.GET)
    public String activateMember(@PathVariable("id") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        if (memberEntity.getIsEmailPassed() == 1) {
            System.out.println("激活过了");
        } else {
            memberService.activateMember(mid);
        }
        return "redirect:/member/show/" + mid;
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Integer userId, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(userId);
        MemberUpdateVO memberUpdateVO = new MemberUpdateVO(memberEntity.getMid(), memberEntity.getBankAccount(), memberEntity.getPassword());

        modelMap.addAttribute("member", memberUpdateVO);
        return "member/updateMember";
    }

    @RequestMapping(value = "updatePost/{id}", method = RequestMethod.POST)
    public String updateUserPost(@PathVariable("id") Integer userId, @ModelAttribute("memberP") MemberEntity user) {
        user.setMid(userId);
        memberService.updateMember(user);
        return "redirect:/member/show/" + userId;
    }

    @RequestMapping(value = "terminate/{id}", method = RequestMethod.GET)
    public String terminateUser(@PathVariable("id") Integer mid) {
        memberService.terminateMember(mid);
        return "redirect:/";
    }

    @RequestMapping(value = "{mid}/order/{eid}/chooseSeat", method = RequestMethod.GET)
    public ModelAndView orderChooseSeat(@PathVariable("eid") Integer eid, @PathVariable("mid") Integer mid) {
        ModelAndView modelAndView = new ModelAndView("member/order/memberOrderChooseSeat");

        MemberEntity memberEntity = memberService.findByMid(mid);
        modelAndView.addObject("member", memberEntity);

        EventEntity eventEntity = eventService.findByEid(eid);
        modelAndView.addObject("event", eventEntity);

        modelAndView.addObject("coupons", couponService.findAvailableCouponsByMember(memberEntity));

        return modelAndView;
    }

    @RequestMapping(value = "{mid}/order/{eid}/chooseSeatPost", method = RequestMethod.GET)
    public ModelAndView orderChooseSeatPost(@PathVariable("eid") Integer eid, @PathVariable("mid") Integer mid, HttpServletRequest request) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        EventEntity eventEntity = eventService.findByEid(eid);

        Integer totalSeatNumber = 0;
        for (EventSeatEntity eventSeatEntity : eventEntity.getEventSeats()) {
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
        orderEntity.setStatus(Byte.valueOf("0"));

        String couponString = request.getParameter("memberCouponCid");
        if (couponString != null && !couponString.equals("")) {
            try {
                orderEntity.setMemberCouponEntity(couponService.findByMcid(Integer.parseInt(couponString)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        ArrayList<OrderEventSeatEntity> orderEventSeatEntities = new ArrayList<OrderEventSeatEntity>();
        for (EventSeatEntity eventSeatEntity : eventEntity.getEventSeats()) {
            Integer eventSeatCount = Integer.parseInt(request.getParameter("eventSeatNumber" + eventSeatEntity.getEsid()));
            if (eventSeatCount > 0) {
                for (int i = 0; i < eventSeatCount; i++) {
                    OrderEventSeatEntity orderEventSeatEntity = new OrderEventSeatEntity();
                    orderEventSeatEntity.setIsValid(1);
                    orderEventSeatEntity.setEventSeatByEsid(eventSeatEntity);
                    orderEventSeatEntities.add(orderEventSeatEntity);
                }
            }
        }

        orderService.createOrder(orderEntity, orderEventSeatEntities);

        return new ModelAndView("redirect:/member/order/" + mid);
    }

    @RequestMapping(value = "order/{mid}", method = RequestMethod.GET)
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

    @RequestMapping(value = "order/{mid}/pay/{oid}", method = RequestMethod.GET)
    public String payOrder(@PathVariable("mid") Integer mid, @PathVariable("oid") Integer oid) {
        Boolean result = orderService.payOrder(orderService.findByOid(oid), memberService.findByMid(mid).getBankAccount());
        if (result) {
            return "redirect:/member/order/" + mid;
        } else {
            return "redirect:/member/charge/" + mid;
        }
    }

    @RequestMapping(value = "order/{mid}/cancel/{oid}", method = RequestMethod.GET)
    public String cancelOrder(@PathVariable("mid") Integer mid, @PathVariable("oid") Integer oid) {
        OrderEntity orderEntity = orderService.findByOid(oid);
        orderService.cancelOrder(orderEntity);
        return "redirect:/member/order/" + mid;
    }

    @RequestMapping(value = "order/{mid}/refund/{oid}", method = RequestMethod.GET)
    public String refundOrder(@PathVariable("mid") Integer mid, @PathVariable("oid") Integer oid) {
        orderService.refundOrder(orderService.findByOid(oid), memberService.findByMid(mid).getBankAccount());
        return "redirect:/member/order/" + mid;
    }

    @RequestMapping(value = "order/{mid}/detail/{oid}", method = RequestMethod.GET)
    public String orderDetail(@PathVariable("mid") Integer mid, @PathVariable("oid") Integer oid, ModelMap modelMap) {
        modelMap.addAttribute("member", memberService.findByMid(mid));

        OrderEntity orderEntity = orderService.findByOid(oid);
        modelMap.addAttribute("order", new OrderVO(orderEntity));

        modelMap.addAttribute("seats", orderEntity.getOrderEventSeats());

        return "member/order/memberOrderDetail";
    }

    @RequestMapping(value = "charge/{mid}")
    public String charge(@PathVariable("mid") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        modelMap.addAttribute("member", memberEntity);
        modelMap.addAttribute("bankAccount", memberService.findBankAccountEntity(memberEntity.getBankAccount()));
        return "member/memberCharge";
    }

    @RequestMapping(value = "chargePost/{mid}")
    public String chargePost(@PathVariable("mid") Integer mid, HttpServletRequest request) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        Double amount = Double.parseDouble(request.getParameter("chargeAmount"));
        memberService.chargeAmount(memberEntity, amount);
        return "redirect:/member/show/" + mid;
    }

}
