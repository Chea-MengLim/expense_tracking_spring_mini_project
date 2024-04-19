package org.example.kps_group_01_spring_mini_project.service.impl;

import lombok.AllArgsConstructor;
import org.example.kps_group_01_spring_mini_project.exception.BadRequestException;
import org.example.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.example.kps_group_01_spring_mini_project.model.Category;
import org.example.kps_group_01_spring_mini_project.model.User;
import org.example.kps_group_01_spring_mini_project.model.dto.request.CategoryRequest;
import org.example.kps_group_01_spring_mini_project.repository.CategoryRepository;
import org.example.kps_group_01_spring_mini_project.service.CategoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> findAllCategories(Integer offset, Integer limit) {
        if (offset <= 0 || limit <= 0) {
            throw new BadRequestException("Offset and limit must be greater than 0.");
        }
        offset = (offset - 1)*limit;
        List<Category> allCategory = categoryRepository.findAllCategories(offset, limit);
        return allCategory;
    }

    @Override
    public Category findCategoryById(String id) {
        Category category = null;
        try{
            category = categoryRepository.findCategoryById(id);
        }catch (Exception ignore){

        }
        if(category == null)
            throw new NotFoundException("Find category with id "+ id + " is not found.");
        return category;
    }

    @Override
    public Category register(CategoryRequest categoryRequest) {
        String userId = getUserIdOfCurrentUser();
        if (categoryRequest.getName().isBlank()){
            throw new BadRequestException("Category's name is blank...");
        } else if (categoryRequest.getDescription().isBlank()) {
            throw new BadRequestException("Category's description is blank...");
        }
        return categoryRepository.register(categoryRequest, userId);
    }

    @Override
    public Category updateCategory(CategoryRequest categoryRequest, String id) {
        Category category = null;
        try{
            category = categoryRepository.findCategoryById(id);
        }catch (Exception ignore){

        }
        if(category == null)
            throw new NotFoundException("Find category with id "+ id + " is not found.");
        return categoryRepository.updateCategory(categoryRequest, id);
    }

    @Override
    public String deleteCategory(String id) {
        Category category = null;
        try{
            category = categoryRepository.findCategoryById(id);
        }catch (Exception ignore){

        }
        if(category == null)
            throw new NotFoundException("Find category with id "+ id + " is not found.");
        Boolean isDelete = categoryRepository.deleteCategory(id);
        if (isDelete){
            return "Remove Category successfully.";
        }
        return "Remove fail...";
    }

    String getUserIdOfCurrentUser() {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userId = userDetails.getUserId().toString();
        System.out.println(userId);
        return userId;
    }

}
