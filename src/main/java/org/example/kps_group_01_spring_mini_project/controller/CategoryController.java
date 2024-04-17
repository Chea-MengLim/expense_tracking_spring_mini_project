package org.example.kps_group_01_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.kps_group_01_spring_mini_project.model.Category;
import org.example.kps_group_01_spring_mini_project.model.response.APIResponse;
import org.example.kps_group_01_spring_mini_project.model.dto.request.CategoryRequest;
import org.example.kps_group_01_spring_mini_project.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<APIResponse<List<Category>>> findAllCategories(@RequestParam(defaultValue = "1") Integer offset, @RequestParam(defaultValue = "3") Integer limit){

        List<Category> allAttendees = categoryService.findAllCategories(offset, limit);
        APIResponse<List<Category>> response = new APIResponse<>(
                "Find all Categories successfully.",
                categoryService.findAllCategories(offset, limit),
                HttpStatus.OK,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Category>> findCategoryById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "Find Category by id successfully.",
                        categoryService.findCategoryById(id),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping
    public ResponseEntity<APIResponse<Category>> register(@RequestBody @Valid CategoryRequest categoryRequest){
        Category registerCategory = categoryService.register(categoryRequest);
        APIResponse<Category> response = new APIResponse<>(
                "Insert new category successfully.",
                registerCategory,
                HttpStatus.CREATED,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable String id){
        return categoryService.updateCategory(categoryRequest, id);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable String id){
        return categoryService.deleteCategory(id);
    }

}
