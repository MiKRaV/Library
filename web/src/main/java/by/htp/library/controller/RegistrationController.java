package by.htp.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @RequestMapping(value = "/registration-page", method = RequestMethod.GET)
    public String registrationPage(ModelAndView model) {
        return "registration";
    }
}
