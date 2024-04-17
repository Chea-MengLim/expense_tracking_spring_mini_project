package org.example.kps_group_01_spring_mini_project.service.impl;

import lombok.AllArgsConstructor;
import org.example.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.dto.request.ExpenseRequest;
import org.example.kps_group_01_spring_mini_project.repository.ExpenseRepository;
import org.example.kps_group_01_spring_mini_project.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Expense> getAllExpenses(Integer offset, Integer limit, String sortBy, String orderBy) {
        return expenseRepository.getAllExpenses(offset, limit, sortBy,orderBy);
    }

    @Override
    public Expense getExpenseById(String id) {
        Expense expense = expenseRepository.getExpenseById(id);
        if(expense == null) {
            throw new NotFoundException("The expense id " + id + "  has not been founded.");
        }
        return modelMapper.map(expense,Expense.class);
    }

    @Override
    public Expense createExpense(ExpenseRequest expenseRequest) {
        return expenseRepository.createExpense(expenseRequest);
    }

    @Override
    public Expense updateExpense(String id, ExpenseRequest expenseRequest) {
        return expenseRepository.updateExpense(id, expenseRequest);
    }

    @Override
    public Expense deleteExpense(String id) {
        Expense expense = expenseRepository.deleteExpense(id);
        if(expense == null) {
            throw new NotFoundException("The expense id " + id + "  has not been founded.");
        }
        return modelMapper.map(expense,Expense.class);
    }
}
