package org.example.kps_group_01_spring_mini_project.service;


import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.dto.request.ExpenseRequest;

import java.util.List;

public interface ExpenseService {
    List<Expense> getAllExpenses(Integer offset, Integer limit, String sortBy, String orderBy);

    Expense getExpenseById(String id);
    Expense createExpense(ExpenseRequest expenseRequest);

    Expense updateExpense(String id, ExpenseRequest expenseRequest);

    Expense deleteExpense(String id);


}

