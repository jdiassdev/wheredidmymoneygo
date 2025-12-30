package com.jdiassdev.wheredidmymoneygo.feature.category;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jdiassdev.wheredidmymoneygo.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c ORDER BY c.name ASC")
    List<Category> findAllOrderedByName();
}