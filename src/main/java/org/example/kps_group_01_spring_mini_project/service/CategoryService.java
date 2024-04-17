package org.example.kps_group_01_spring_mini_project.service;


import org.example.kps_group_01_spring_mini_project.model.Category;
import org.example.kps_group_01_spring_mini_project.model.dto.request.CategoryRequest;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories(Integer page, Integer limit);

    Category findCategoryById(String id);

    Category register(CategoryRequest categoryRequest);

    Category updateCategory(CategoryRequest categoryRequest, String id);

    String deleteCategory(String id);
}
