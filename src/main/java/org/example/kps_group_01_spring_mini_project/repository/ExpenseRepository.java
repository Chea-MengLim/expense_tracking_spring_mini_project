package org.example.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.request.ExpenseRequest;

@Mapper
public interface ExpenseRepository {

    @Select("SELECT * FROM expenses")
    Expense getAllExpenses(Integer offset, Integer limit, String sortBy, String orderBy);
    @Select("INSERT INTO expenses RETURNING *")
    Expense createExpense(ExpenseRequest expenseRequest);

    @Select("SELECT * FROM expenses WHERE id = #{id}")
    Expense getExpenseById(Integer id);

    @Update("UPDATE expenses SET description = #{description}, amount = #{amount} WHERE id = #{id}")
    Expense updateExpense(Integer id, ExpenseRequest expenseRequest);

    @Delete("DELETE FROM expenses WHERE id = #{id}")
    Expense deleteExpense(Integer id);


}
