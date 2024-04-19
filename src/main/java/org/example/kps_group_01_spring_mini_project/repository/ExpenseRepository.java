package org.example.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.dto.request.ExpenseRequest;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ExpenseRepository {

    @Select("SELECT * FROM expenses  ORDER BY ${sortBy} ${orderBy} LIMIT #{limit} OFFSET #{offset}")
    @Results(id = "expensesMapper", value = {
            @Result(property = "expense_id", column = "expense_id"),
            @Result(property = "categoryResponse", column = "category_id", one = @One(select = "org.example.kps_group_01_spring_mini_project.repository.CategoryRepository.findCategoryResponseById")),
            @Result(property = "user", column = "user_id", one = @One(select = "org.example.kps_group_01_spring_mini_project.repository.UserRepository.findUserByUserId"))

    })
    List<Expense> getAllExpenses(Integer offset, Integer limit, String sortBy, String orderBy);

    @Select("SELECT * FROM expenses WHERE expense_id = #{id}::uuid")
    @ResultMap("expensesMapper")
    Expense getExpenseById(String id);

    @Select("INSERT INTO expenses (amount,description,date, user_id, category_id)" +
            " VALUES (#{expense.amount},#{expense.description},#{expense.date}, #{userId}, #{expense.category_id}::uuid) RETURNING *")
    @ResultMap("expensesMapper")
    Expense createExpense(@Param("expense")ExpenseRequest expenseRequest, UUID userId);

    @Select("UPDATE expenses SET amount = #{expense.amount}, description = #{expense.description}, date = #{expense.date }, category_id = #{expense.category_id }::uuid WHERE expense_id = #{id}::uuid RETURNING *")
    @ResultMap("expensesMapper")
    Expense updateExpense(String id, @Param("expense") ExpenseRequest expenseRequest);

    @Select("DELETE FROM expenses WHERE expense_id = #{id}::uuid")
    void deleteExpense(String id);
}
