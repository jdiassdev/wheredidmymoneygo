package com.jdiassdev.wheredidmymoneygo.feature.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jdiassdev.wheredidmymoneygo.dto.TransactionTotals;
import com.jdiassdev.wheredidmymoneygo.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndCategory_Id(Long userId, Long categoryId);

    @Query("""
                select new com.jdiassdev.wheredidmymoneygo.dto.TransactionTotals(
                    count(t),
                    coalesce(sum(t.amount), 0),
                    coalesce(min(t.amount), 0),
                    coalesce(max(t.amount), 0)
                )
                from Transaction t
                where t.user.id = :userId
                and (:categoryId is null or t.category.id = :categoryId)
            """)
    TransactionTotals getTotals(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId);

}
