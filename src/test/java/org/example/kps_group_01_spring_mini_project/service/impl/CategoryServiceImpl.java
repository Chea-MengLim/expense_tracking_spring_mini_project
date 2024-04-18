package org.example.kps_group_01_spring_mini_project.service.impl;

import lombok.AllArgsConstructor;
import org.example.kps_group_01_spring_mini_project.exception.BadRequestException;
import org.example.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.example.kps_group_01_spring_mini_project.model.Category;
import org.example.kps_group_01_spring_mini_project.model.dto.request.CategoryRequest;
import org.example.kps_group_01_spring_mini_project.repository.CategoryRepository;
import org.example.kps_group_01_spring_mini_project.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategories() {
        List<Category> allCategory = categoryRepository.findAllCategories();
        return allCategory;
    }

    @Override
    public Category findCategoryById(String id) {
        Category category;
        try{
            category = categoryRepository.findCategoryById(id);
            if(category == null){
                throw new NotFoundException("Find category with id "+ id + " is not found.");
            }
        }catch (NotFoundException e){
            throw e;
        }
        return category;
    }

    @Override
    public Category register(CategoryRequest categoryRequest) {
        if (categoryRequest.getName().isBlank()){
            throw new BadRequestException("Category's name is blank...");
        } else if (categoryRequest.getDescription().isBlank()) {
            throw new BadRequestException("Category's description is blank...");
        } else if (categoryRequest.getUserId().isBlank()) {
            throw new BadRequestException("User id is blank...");
        }
        return categoryRepository.register(categoryRequest);
    }

    @Override
    public Category updateCategory(CategoryRequest categoryRequest, String id) {
        return categoryRepository.updateCategory(categoryRequest, id);
    }

    @Override
    public String deleteCategory(String id) {
        Boolean isDelete = categoryRepository.deleteCategory(id);
        if (isDelete){
            return "Remove Category successfully.";
        }
        return "Remove fail...";
    }
}
