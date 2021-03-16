package com.devkuma.webflux;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class ItemController {

    @GetMapping("/")
    public String index(Model model) {
        
        Flux<String> flux = Flux
                .range(0, 5)
                .map(i -> "index: " + i)
                .repeat(3)
                .delayElements(Duration.ofSeconds(1L));

        model.addAttribute("items", new ReactiveDataDriverContextVariable(flux, 1));

        return "index";
    }
}
