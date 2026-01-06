package com.jdiassdev.wheredidmymoneygo.feature.transaction;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/")
    public List<TransactionDTO.ListUserTransactionsResponse> list(
            @AuthenticationPrincipal AuthUser user,
            @RequestParam(required = false) Long category_id) {
        return transactionService.list(
                user.email(),
                new TransactionDTO.ListUserTransactionsRequest(category_id));
    }

    @GetMapping("/totals-resume")
    public TransactionDTO.TotalResumeTransactionsResponse totals(
            @AuthenticationPrincipal AuthUser user,
            @RequestParam(required = false) Long category_id) {
        return transactionService.totalResume(
                user.email(), new TransactionDTO.TotalResumeTransactionsRequest(category_id));
    }

}
