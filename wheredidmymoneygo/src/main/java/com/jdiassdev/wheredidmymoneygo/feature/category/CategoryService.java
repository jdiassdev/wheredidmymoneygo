package com.jdiassdev.wheredidmymoneygo.feature.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jdiassdev.wheredidmymoneygo.entity.Category;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

   public CategoryDTO.Lista listar() {
    List<Category> categories = categoryRepository.findAllOrderedByName();

    List<CategoryDTO.Item> items = categories.stream()
        .map(cat -> new CategoryDTO.Item(cat.getId(), cat.getName()))
        .toList();

    return new CategoryDTO.Lista(items);
}


}
