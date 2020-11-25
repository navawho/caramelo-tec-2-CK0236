package com.api.caramelo.controllers;

import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.services.SolicitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/solicitations")
@RequiredArgsConstructor
public class SolicitationController {

    private final SolicitationService service;

    @PostMapping("{petId}")
    public ResponseEntity store(HttpServletRequest request, @PathVariable Long petId) {
        try {
            Long userId = (Long) request.getAttribute("userId");

            return ok(service.create(userId, petId));
        } catch (BusinessRuleException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());

            return badRequest().body(map);
        }
    }

    @PatchMapping("{solicitationId}/{accepted}")
    public ResponseEntity update(@PathVariable Long solicitationId, @PathVariable String accepted) {
        try {
            return ok(service.update(solicitationId, accepted.matches("accept")));
        } catch (BusinessRuleException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());

            return badRequest().body(map);
        }
    }

    @DeleteMapping("{solicitationId}")
    public ResponseEntity delete(@PathVariable Long solicitationId, HttpServletRequest request) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            service.delete(solicitationId, userId);

            return ok().build();
        } catch (BusinessRuleException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());

            if(e.checkHasSomeError()){
                map.put("errors", e.getErrors());
            }

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

    @GetMapping("{petId}")
    public ResponseEntity getPetSolicitations(HttpServletRequest request, @PathVariable Long petId) {
        try {
            Long userId = (Long) request.getAttribute("userId");

            return ok(service.searchPetSolicitations(userId, petId));
        } catch (BusinessRuleException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());

            return badRequest().body(map);
        }
    }
}
