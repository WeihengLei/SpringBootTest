package com.example.test.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {Exception.class}) //表示捕捉到所有的异常，你也可以捕捉一个你自定义的异常
    public ModelAndView exception(Exception exception, WebRequest request){
        ModelAndView modelAndView = new ModelAndView("/error");//error页面
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("stackTrace", exception.getStackTrace());
        return  modelAndView;
    }

}
