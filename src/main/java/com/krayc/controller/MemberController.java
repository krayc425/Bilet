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
    private BookService bookService;

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
        MemberInfoVO memberInfoVO = new MemberInfoVO(memberEntity1,
                levelService.findLevelEntityWithPoint(memberEntity1.getTotalPoint()).getDescription(),
                memberService.findBalance(memberEntity1.getBankAccount()));
        modelMap.addAttribute("member", memberInfoVO);

        ArrayList<EventVO> eventVOS = new ArrayList<EventVO>();
        for (EventEntity eventEntity : eventService.findAvailableEvents()) {
            eventVOS.add(new EventVO(eventEntity));
        }
        modelMap.addAttribute("events", eventVOS);

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

    @RequestMapping(value = "charge/{mid}", method = RequestMethod.GET)
    public String charge(@PathVariable("mid") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        modelMap.addAttribute("member", memberEntity);
        modelMap.addAttribute("bankAccount", new BankAccountVO(memberService.findBankAccountEntity(memberEntity.getBankAccount())));
        return "member/memberCharge";
    }

    @RequestMapping(value = "chargePost/{mid}", method = RequestMethod.GET)
    public String chargePost(@PathVariable("mid") Integer mid, HttpServletRequest request) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        Double amount = Double.parseDouble(request.getParameter("chargeAmount"));
        memberService.chargeAmount(memberEntity, amount);
        return "redirect:/member/show/" + mid;
    }

    @RequestMapping(value = "book/{mid}", method = RequestMethod.GET)
    public String book(@PathVariable("mid") Integer mid, ModelMap modelMap) {
        MemberEntity memberEntity = memberService.findByMid(mid);
        modelMap.addAttribute("member", memberEntity);

        ArrayList<MemberBookVO> memberBookVOS = new ArrayList<MemberBookVO>();
        for (MemberBookEntity memberBookEntity : memberEntity.getMemberBooks()) {
            memberBookVOS.add(new MemberBookVO(memberBookEntity));
        }
        modelMap.addAttribute("books", memberBookVOS);

        modelMap.addAttribute("total", String.format("%.2f", bookService.totalMemberBookAmount(memberEntity)));

        return "member/memberBooks";
    }

}
