package com.jdiassdev.wheredidmymoneygo.feature.transaction;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            @RequestParam(required = false) Long category) {
        return transactionService.list(
                user.email(),
                new TransactionDTO.ListUserTransactionsRequest(category));
    }

    @GetMapping("/totals-resume")
    public TransactionDTO.TotalResumeTransactionsResponse totals(
            @AuthenticationPrincipal AuthUser user,
            @RequestParam(required = false) Long category_id) {
        return transactionService.totalResume(
                user.email(), new TransactionDTO.TotalResumeTransactionsRequest(category_id));
    }

    @PatchMapping("/{id}")
    public TransactionDTO.UpdateResponse update(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable("id") Long id,
            @RequestBody @Valid TransactionDTO.UpdateTransactionRequest dto) {
        return transactionService.updateT(user.email(), id, dto);
    }

    @PatchMapping("/{id}/activate")
    public TransactionDTO.StatusResponse activate(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable("id") Long id) {
        return transactionService.activate(user.email(), id);
    }

    @PatchMapping("/{id}/inactivate")
    public TransactionDTO.StatusResponse inactivate(
            @AuthenticationPrincipal AuthUser user,
            @PathVariable("id") Long id) {
        return transactionService.inactivate(user.email(), id);
    }

}
