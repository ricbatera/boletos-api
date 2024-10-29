package br.com.consultdg.boletos_api.api.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/teste")
public class TesteController {

    @GetMapping("/hello")
    public String getMethodName() {
        return "Api respondendo - endpoint de teste - Automatizado em 29-10";
    }

}
