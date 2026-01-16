package com.jdiassdev.wheredidmymoneygo.feature.dashboard;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdiassdev.wheredidmymoneygo.dto.AuthUser;
import com.jdiassdev.wheredidmymoneygo.dto.CategoryTotalDTO;
import com.jdiassdev.wheredidmymoneygo.dto.MonthlyEvolutionProjection;
import com.jdiassdev.wheredidmymoneygo.feature.dashboard.DashboardDTO.MonthlyExpenseSummaryDTORes;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardDTO.MonthSummaryResponse getCurrent(
            @AuthenticationPrincipal AuthUser user,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return dashboardService.getCurrentMonthSummary(
                user.email(),
                new DashboardDTO.MonthSummaryReqDTO(date));
    }

    @GetMapping("/top-categories")
    public List<CategoryTotalDTO> getTopCategories(
            @AuthenticationPrincipal AuthUser user,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return dashboardService.getTopCategoriesCurrentMonth(
                user.email(),
                new DashboardDTO.TopCaregoryMonthSummaryReqDTO(date));
    }

    @GetMapping("/evolution")
    public List<MonthlyEvolutionProjection> getYearToDateEvo(
            @AuthenticationPrincipal AuthUser user) {
        return dashboardService.getYearToDateEvolution(user.email());
    }

    @GetMapping("/monthly-summary")
    public MonthlyExpenseSummaryDTORes getCurrentMonth(
            @AuthenticationPrincipal AuthUser user,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return dashboardService.getCurrentMonth(user.email(),
                new DashboardDTO.TopCaregoryMonthSummaryReqDTO(date));
    }

}
