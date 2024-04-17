package org.example.kps_group_01_spring_mini_project.service.serviceimpl;

import lombok.AllArgsConstructor;
import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.request.ExpenseRequest;
import org.example.kps_group_01_spring_mini_project.repository.ExpenseRepository;
import org.example.kps_group_01_spring_mini_project.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    @Override
    public Expense getAllExpenses(Integer offset, Integer limit, String sortBy, String orderBy) {
        return expenseRepository.getAllExpenses(offset, limit, sortBy,orderBy);
    }

    @Override
    public Expense getExpenseById(Integer id) {
        return expenseRepository.getExpenseById(id);
    }

    @Override
    public Expense updateExpense(Integer id, ExpenseRequest expenseRequest) {
        return expenseRepository.updateExpense(id, expenseRequest);
    }

    @Override
    public Expense deleteExpense(Integer id) {
        return expenseRepository.deleteExpense(id);
    }

    @Override
    public Expense createExpense(ExpenseRequest expenseRequest) {
        return expenseRepository.createExpense(expenseRequest);
    }


}
