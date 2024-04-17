package org.example.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.dto.request.ExpenseRequest;

import java.util.List;

@Mapper
public interface ExpenseRepository {

    @Select("SELECT * FROM expenses")
    @Results(id = "expensesMapper", value = {
            @Result(property = "expense_id", column = "expense_id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "description", column = "description"),
            @Result(property = "date", column = "date"),
            @Result(property = "category", column = "category_id", one = @One(select = "org.example.kps_group_01_spring_mini_project.repository.CategoryRepository.findCategoryById")),
            @Result(property = "user_id", column = "user_id")

    })
    List<Expense> getAllExpenses(Integer offset, Integer limit, String sortBy, String orderBy);

    @Select("SELECT * FROM expenses WHERE expense_id = #{id}::uuid")
    Expense getExpenseById(String id);

    @Select("INSERT INTO expenses (amount,description,date,user_id,category_id)" +
            " VALUES (#{expense.amount},#{expense.description},#{expense.date},'f935ceaf-7f58-4ec4-8f27-03e45804e3b4', #{expense.category_id}::uuid) RETURNING *")
    @ResultMap("expensesMapper")
    Expense createExpense(@Param("expense")ExpenseRequest expenseRequest);

    @Select("UPDATE expenses SET amount = #{expense.amount}, description = #{expense.description}, date = #{expense.date }, category_id = #{expense.category_id }::uuid WHERE expense_id = #{id}::uuid RETURNING *")
    @ResultMap("expensesMapper")
    Expense updateExpense(String id, @Param("expense") ExpenseRequest expenseRequest);

    @Delete("DELETE FROM expenses WHERE expense_id = #{id}::uuid")
    Expense deleteExpense(String id);
}

