package com.info.org.sid.web;

import com.info.org.sid.dao.SocieteRepository;
import com.info.org.sid.dao.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebMVCController {
    SocieteRepository societeRepository;
    TransactionRepository transactionRepository;

    public WebMVCController(SocieteRepository societeRepository, TransactionRepository transactionRepository) {
        this.societeRepository = societeRepository;
        this.transactionRepository = transactionRepository;
    }


    @GetMapping(value = "/")
    public String index(Model model){

        model.addAttribute("societes", societeRepository.findAll());

        return  "index";
    }


}
