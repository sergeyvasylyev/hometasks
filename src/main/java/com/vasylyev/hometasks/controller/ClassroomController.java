package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.classroom.ClassroomService;
import com.vasylyev.hometasks.telegram.TelegramNotifier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ClassroomController {

    private final TelegramNotifier telegramNotifier;
    private final ClassroomService classroomService;

    @GetMapping("/")
    public String index() {
        System.out.println("start app");
        return "index";
    }
    //TODO: add UI for subscribers
}
