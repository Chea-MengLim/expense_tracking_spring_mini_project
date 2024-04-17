package org.example.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.request.ExpenseRequest;

@Mapper
public interface ExpenseRepository {

    @Select("SELECT * FROM expenses")
    Expense getAllExpenses(Integer offset, Integer limit, String sortBy, String orderBy);

    @Select("SELECT * FROM expenses WHERE expense_id = #{id}::uuid")
    Expense getExpenseById(Integer id);

    @Select("INSERT INTO expense (amount,description,date,category_id) VALUES ( #{expense.category_id}::uuid) RETURNING *")
    Expense createExpense(@Param("expense")ExpenseRequest expenseRequest);

    @Select("UPDATE expense SET amount = #{amount}, description = #{expense.description}, date = #{expense.date }, category_id = #{expense.category_id }::uuid")
    Expense updateExpense(Integer id, @Param("expense") ExpenseRequest expenseRequest);

    @Delete("DELETE FROM expenses WHERE id = #{id}")
    Expense deleteExpense(Integer id);


}
