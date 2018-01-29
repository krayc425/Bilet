package com.krayc.controller;

import com.krayc.vo.MessageVO;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class BaseController {

    protected ModelAndView handleMessage(MessageVO MessageVO, String page) {
        ModelAndView modelAndView = new ModelAndView(page);
        if (!MessageVO.isSuccess()) {
            modelAndView.addObject("error", MessageVO.getDisplayMessage());
        }
        return modelAndView;
    }

    protected ModelAndView handleMessage(MessageVO MessageVO, String page, Map<String, Object> object) {
        ModelAndView modelAndView = new ModelAndView(page);
        if (!MessageVO.isSuccess()) {
            modelAndView.addObject("error", MessageVO.getDisplayMessage());
        }
        // 都返回相同的数据到界面
        this.addDataToMV(object, modelAndView);
        return modelAndView;
    }

    protected ModelAndView handleMessage(MessageVO MessageVO, String successPage, String errorPage) {
        ModelAndView modelAndView;
        // 处理成功
        if (MessageVO.isSuccess()) {
            modelAndView = new ModelAndView(successPage);
            // 处理失败
        } else {
            modelAndView = new ModelAndView(errorPage);
            modelAndView.addObject("error", MessageVO.getDisplayMessage());
        }
        return modelAndView;
    }

    protected ModelAndView handleMessage(MessageVO MessageVO, String successPage, String errorPage,
                                         Map<String, Object> successObject, Map<String, Object> errorObjects) {
        ModelAndView modelAndView;
        // 处理成功
        if (MessageVO.isSuccess()) {
            modelAndView = new ModelAndView(successPage);
            // 添加成功时候的数据
            this.addDataToMV(successObject, modelAndView);

            // 处理失败
        } else {
            modelAndView = new ModelAndView(errorPage);
            modelAndView.addObject("error", MessageVO.getDisplayMessage());
            // 添加错误时候的数据
            this.addDataToMV(errorObjects, modelAndView);
        }
        return modelAndView;
    }

    private void addDataToMV(Map<String, Object> inputMap, ModelAndView modelAndView) {
        if (inputMap == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
            modelAndView.addObject(entry.getKey(), entry.getValue());
        }
    }
}
