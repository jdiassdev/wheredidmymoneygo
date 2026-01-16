package com.jdiassdev.wheredidmymoneygo.feature.dashboard;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jdiassdev.wheredidmymoneygo.dto.CategoryTotalDTO;
import com.jdiassdev.wheredidmymoneygo.dto.MonthlyEvolutionProjection;
import com.jdiassdev.wheredidmymoneygo.entity.User;
import com.jdiassdev.wheredidmymoneygo.feature.dashboard.DashboardDTO.MonthlyExpenseSummaryDTORes;
import com.jdiassdev.wheredidmymoneygo.feature.user.UserRepository;

@Service
public class DashboardService {
        private final UserRepository userRepository;
        private final DashboardRepository dashboardRepository;

        public DashboardService(DashboardRepository dashboardRepository, UserRepository userRepository) {
                this.dashboardRepository = dashboardRepository;
                this.userRepository = userRepository;
        }

        public DashboardDTO.MonthSummaryResponse getCurrentMonthSummary(
                        String email,
                        DashboardDTO.MonthSummaryReqDTO dto) {

                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                YearMonth month = YearMonth.from(dto.date_reference());

                LocalDateTime startDateTime = month.atDay(1).atStartOfDay();
                LocalDateTime endDateTime = month.atEndOfMonth().atTime(LocalTime.MAX);

                DashboardDTO.MonthSummaryDTO summary = dashboardRepository.getMonthSummary(
                                user.getId(),
                                startDateTime,
                                endDateTime);

                return new DashboardDTO.MonthSummaryResponse(
                                summary.totalItems(),
                                summary.totalAmount(),
                                startDateTime,
                                endDateTime);
        }

        public List<CategoryTotalDTO> getTopCategoriesCurrentMonth(
                        String email,
                        DashboardDTO.TopCaregoryMonthSummaryReqDTO dto) {

                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                YearMonth month = YearMonth.from(dto.date_reference());

                LocalDateTime start = month.atDay(1).atStartOfDay();
                LocalDateTime end = month.atEndOfMonth().atTime(LocalTime.MAX);

                return dashboardRepository.getTopCategories(
                                user.getId(),
                                start,
                                end);
        }

        public List<MonthlyEvolutionProjection> getYearToDateEvolution(String email) {

                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                LocalDate now = LocalDate.now();

                LocalDate startDate;

                if (now.getMonth() == Month.JANUARY) {
                        // = janeiro pega 6 meses para tras
                        startDate = now.minusMonths(5).withDayOfMonth(1);
                } else {
                        // de janeiro ate data consulta
                        startDate = now.withMonth(1).withDayOfMonth(1);
                }

                LocalDateTime start = startDate.atStartOfDay();
                LocalDateTime end = now.with(TemporalAdjusters.lastDayOfMonth())
                                .atTime(23, 59, 59);

                return dashboardRepository.getMonthlyEvolution(user.getId(), start, end);
        }

        public MonthlyExpenseSummaryDTORes getCurrentMonth(String email,
                        DashboardDTO.TopCaregoryMonthSummaryReqDTO dto) {

                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                YearMonth month = YearMonth.from(dto.date_reference());

                LocalDateTime start = month.atDay(1).atStartOfDay();
                LocalDateTime end = month.atEndOfMonth().atTime(LocalTime.MAX);

                return dashboardRepository.getMonthlySummary(user.getId(), start, end);
        }

}
