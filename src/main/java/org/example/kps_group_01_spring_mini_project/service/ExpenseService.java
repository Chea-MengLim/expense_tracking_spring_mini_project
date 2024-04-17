package org.example.kps_group_01_spring_mini_project.service;

import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.request.ExpenseRequest;

public interface ExpenseService {
    Expense getAllExpenses(Integer offset, Integer limit, String sortBy, String orderBy);
    Expense createExpense(ExpenseRequest expenseRequest);

    Expense getExpenseById(Integer id);

    Expense updateExpense(Integer id, ExpenseRequest expenseRequest);

    Expense deleteExpense(Integer id);


}
