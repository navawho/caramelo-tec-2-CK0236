package com.api.caramelo.controllers;

import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.services.AdoptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/adoptions")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService service;

    @PatchMapping("{adoptionId}/return")
    public ResponseEntity update(@PathVariable Long adoptionId) {
        try {
            return ok(service.update(adoptionId));
        } catch (BusinessRuleException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());

            return badRequest().body(map);
        }
    }

    @GetMapping
    public ResponseEntity index(HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");

            return ok(service.search(userId));
        } catch (BusinessRuleException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());

            return badRequest().body(map);
        }
    }
}
