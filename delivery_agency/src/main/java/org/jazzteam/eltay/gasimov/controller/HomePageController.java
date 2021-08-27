package org.jazzteam.eltay.gasimov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping(path = "/")
    public String getHomePage() {
        return "homePage";
    }

    @GetMapping(path = "/actionPage")
    public String addNewClient() {
        return "actionPage";
    }

}
