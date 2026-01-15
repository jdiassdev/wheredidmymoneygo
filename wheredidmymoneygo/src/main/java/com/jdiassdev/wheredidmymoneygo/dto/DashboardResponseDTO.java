package com.jdiassdev.wheredidmymoneygo.dto;

import java.util.List;

import com.jdiassdev.wheredidmymoneygo.feature.dashboard.DashboardDTO;

public record DashboardResponseDTO(
        DashboardDTO.MonthSummaryDTO summary,
        List<CategoryTotalDTO> topCategories
) {}
