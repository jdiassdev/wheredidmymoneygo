package com.jdiassdev.wheredidmymoneygo.feature.category;

import java.util.List;

public final class CategoryDTO {

    private CategoryDTO() {
    }

    public record Item(Long id, String name) {
    }

    public record Lista(List<Item> categories) {
    }

}
