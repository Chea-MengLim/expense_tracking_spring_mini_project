package org.example.kps_group_01_spring_mini_project.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.kps_group_01_spring_mini_project.model.Category;
import org.example.kps_group_01_spring_mini_project.model.apiresponse.APIResponse;
import org.example.kps_group_01_spring_mini_project.model.dto.request.CategoryRequest;
import org.example.kps_group_01_spring_mini_project.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<APIResponse<List<Category>>> findAllCategories(){
        List<Category> allAttendees = categoryService.findAllCategories();
        APIResponse<List<Category>> response = new APIResponse<>(
                "Find all Categories successfully.",
                categoryService.findAllCategories(),
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
