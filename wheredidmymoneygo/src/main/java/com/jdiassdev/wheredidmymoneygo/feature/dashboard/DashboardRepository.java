package com.jdiassdev.wheredidmymoneygo.feature.dashboard;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jdiassdev.wheredidmymoneygo.dto.CategoryTotalDTO;
import com.jdiassdev.wheredidmymoneygo.dto.MonthlyEvolutionProjection;
import com.jdiassdev.wheredidmymoneygo.entity.Transaction;
import com.jdiassdev.wheredidmymoneygo.feature.dashboard.DashboardDTO.MonthlyExpenseSummaryDTORes;

@Repository
public interface DashboardRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserIdAndCreatedAtBetween(
            Long userId,
            LocalDateTime start,
            LocalDateTime end);

    @Query("""
                select new com.jdiassdev.wheredidmymoneygo.feature.dashboard.DashboardDTO$MonthSummaryDTO(
                    count(t.id),
                    coalesce(sum(t.amount), 0)
                )
                from Transaction t
                where t.user.id = :userId
                  and t.isActive = true
                  and t.createdAt between :start and :end
            """)
    DashboardDTO.MonthSummaryDTO getMonthSummary(
            @Param("userId") Long userId,
            LocalDateTime start,
            LocalDateTime end);

    @Query("""
                select new com.jdiassdev.wheredidmymoneygo.dto.CategoryTotalDTO(
                    t.category.name,
                    count(t.id)
                )
                from Transaction t
                where t.user.id = :userId
                  and t.isActive = true
                  and t.createdAt between :start and :end
                group by t.category.name
                order by count(t.id) desc
            """)
    List<CategoryTotalDTO> getTopCategories(
            @Param("userId") Long userId,
            LocalDateTime start,
            LocalDateTime end);

    @Query("""
                SELECT
                    FUNCTION('DATE_FORMAT', t.createdAt, '%Y-%m') AS month,
                    SUM(t.amount) AS total
                FROM Transaction t
                WHERE t.user.id = :userId
                  AND t.createdAt BETWEEN :start AND :end
                GROUP BY FUNCTION('DATE_FORMAT', t.createdAt, '%Y-%m')
                ORDER BY month
            """)
    List<MonthlyEvolutionProjection> getMonthlyEvolution(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("""
                SELECT new com.jdiassdev.wheredidmymoneygo.feature.dashboard.DashboardDTO$MonthlyExpenseSummaryDTORes(
                    u.monthlySalary,
                    COALESCE(SUM(t.amount), 0),
                    u.monthlySalary - COALESCE(SUM(t.amount), 0),
                    COALESCE(SUM(
                        CASE
                            WHEN t.amount >= u.expensiveThreshold
                            THEN t.amount
                            ELSE 0
                        END
                    ), 0)
                )
                FROM User u
                LEFT JOIN u.transactions t
                    ON t.createdAt BETWEEN :start AND :end
                    AND t.isActive = true
                WHERE u.id = :userId
            """)
    MonthlyExpenseSummaryDTORes getMonthlySummary(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
