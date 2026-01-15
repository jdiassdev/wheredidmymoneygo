package com.jdiassdev.wheredidmymoneygo.dto;

import java.math.BigDecimal;

public interface MonthlyEvolutionProjection {
    String getMonth();

    BigDecimal getTotal();
}