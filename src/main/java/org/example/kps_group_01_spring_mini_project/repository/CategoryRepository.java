package org.example.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.example.kps_group_01_spring_mini_project.model.Category;
import org.example.kps_group_01_spring_mini_project.model.dto.request.CategoryRequest;

import java.util.List;

@Mapper
public interface CategoryRepository {

    @Select("""
            SELECT * FROM categories LIMIT #{limit} OFFSET #{offset};
            """)
    @Results(id = "cateMapper", value = {
            @Result(property = "id", column = "category_id"),
            @Result(property = "userId", column = "user_id")
    })
    List<Category> findAllCategories(Integer offset, Integer limit);

    @Select("""
            SELECT * FROM categories WHERE category_id = #{id}::uuid
            """)
    @ResultMap("cateMapper")
    Category findCategoryById(@Param("id") String id);

    @Select("""
            INSERT INTO categories (name, description, user_id) VALUES (#{cate.name}, #{cate.description}, #{userId}::uuid) RETURNING *
            """)
    @ResultMap("cateMapper")
    Category register(@Param("cate") CategoryRequest categoryRequest, String userId);

    @Select("""
            UPDATE categories SET name = #{cate.name}, description = #{cate.description}, user_id = #{cate.userId}::uuid WHERE category_id = #{id}::uuid
            """)
    Category updateCategory(@Param("cate") CategoryRequest categoryRequest, String id);


    @Delete("""
            DELETE FROM categories WHERE category_id = #{id}::uuid
            """)
    Boolean deleteCategory(String id);
}
