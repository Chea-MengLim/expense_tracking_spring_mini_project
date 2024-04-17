package org.example.kps_group_01_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.response.APIResponse;
import org.example.kps_group_01_spring_mini_project.model.dto.request.ExpenseRequest;
import org.example.kps_group_01_spring_mini_project.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {
    private final ExpenseService expenseService;
    @GetMapping
    public ResponseEntity<APIResponse<List<Expense>>> getAllExpenses(
            @Positive @RequestParam(defaultValue = "1") Integer offset,
            @Positive @RequestParam(defaultValue = "5") Integer limit,
            @RequestParam(defaultValue = "expense_id") String sortBy,
            @RequestParam(defaultValue = "false") String orderBy) {
        APIResponse<List<Expense>> response = APIResponse.<List<Expense>>builder()
                .message("The expenses have been successfully founded.")
                .payload(expenseService.getAllExpenses(offset, limit, sortBy, orderBy))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Expense>> getExpenseById( @PathVariable("id") String id){
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully founded.")
                .payload(expenseService.getExpenseById(id))
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<Expense>> createExpense(@RequestBody ExpenseRequest expenseRequest){
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully added.")
                .payload(expenseService.createExpense(expenseRequest))
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Expense>> updateExpense( @PathVariable String id,@RequestBody ExpenseRequest expenseRequest){
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully updated.")
                .payload(expenseService.updateExpense(id, expenseRequest))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Expense>> deleteExpense(@PathVariable String id){
        Expense expense = expenseService.deleteExpense(id);
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully deleted.")
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

