package com.jdiassdev.wheredidmymoneygo.feature.transaction;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdiassdev.wheredidmymoneygo.dto.AuthUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/")
    public TransactionDTO.CreateResponse create(@AuthenticationPrincipal AuthUser user,
            @RequestBody @Valid TransactionDTO.CreateRequest dto) {
        return transactionService.create(user.email(), dto);
    }
}
