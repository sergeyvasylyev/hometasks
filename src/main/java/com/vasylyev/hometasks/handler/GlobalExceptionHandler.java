package com.vasylyev.hometasks.handler;

import com.vasylyev.hometasks.exception.ElementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ElementNotFoundException.class})
    public final String handleException(HttpServletRequest req, RedirectAttributes redirectAttributes, Exception ex) {
        String errorMessage = ex.getMessage();
        log.error(errorMessage);
        redirectAttributes.addFlashAttribute("error", errorMessage);
        return "redirect:" + req.getRequestURI();
    }

}
