package com.project.coinPrj;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class coinController {
    @GetMapping("/hello")
    public String hello() {
        return "부자가 될거야 화이팅!";
    }
}
