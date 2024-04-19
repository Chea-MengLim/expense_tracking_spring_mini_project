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
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Expense> getAllExpenses(Integer offset, Integer limit, String sortBy, boolean orderBy) {
        String order;
        if(orderBy)
            order = "DESC";
        else
            order = "ASC";
        offset = (offset - 1) * limit;
        return expenseRepository.getAllExpenses(offset, limit, sortBy,order);
    }

    @Override
    public Expense getExpenseById(String id) {
        Expense expense = null;
        try{
            expense = expenseRepository.getExpenseById(id);
        }catch (Exception ignored){

        }

        if(expense == null) {
            throw new NotFoundException("The expense id " + id + "  has not been founded.");
        }
        return modelMapper.map(expense,Expense.class);
    }


    @Override
    public Expense createExpense(ExpenseRequest expenseRequest, UUID userId) {
        return expenseRepository.createExpense(expenseRequest, userId);
    }

    @Override
    public Expense updateExpense(String id, ExpenseRequest expenseRequest) {
        Expense expense = null;
        try{
            expense = expenseRepository.getExpenseById(id);
        }catch (Exception ignored){

        }
        if(expense == null) {
            throw new NotFoundException("The expense id " + id + "  has not been founded.");
        }
        return expenseRepository.updateExpense(id, expenseRequest);
    }

    @Override
    public Expense deleteExpense(String id) {
        Expense expense = null;
        try{
            expense = expenseRepository.getExpenseById(id);
        }catch (Exception ignored){

        }
        if(expense == null) {
            throw new NotFoundException("The expense id " + id + "  has not been founded.");
        }
        return modelMapper.map(expense,Expense.class);
    }

}
