package com.minimarket.web_minimarket.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {
    @GetMapping("/api/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        // Spring automatically injects the CSRF token here
        return csrfToken;
    }
}

//{
//	"parameterName": "_csrf",
//	"token": "8nAT7-bzm0wX2I_7UtnwOw0pcYrVmJE9VX_AanS2eeoFSVAbxxRwitHHqXQ67bjJNPTEXmwcXLLi-6AQZE-kC0TQGtg9e2Ip",
//	"headerName": "X-CSRF-TOKEN"
//}
