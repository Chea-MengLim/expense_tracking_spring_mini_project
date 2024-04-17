package org.example.kps_group_01_spring_mini_project.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.apiresponse.APIResponse;
import org.example.kps_group_01_spring_mini_project.model.request.ExpenseRequest;
import org.example.kps_group_01_spring_mini_project.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/expense")
@AllArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    @GetMapping
    public ResponseEntity<APIResponse<Expense>> getAllExpenses(
            @Positive @RequestParam(defaultValue = "1") Integer offset,
            @Positive @RequestParam(defaultValue = "3") Integer limit,
            @RequestParam(defaultValue = "expense_id") String sortBy,
            @RequestParam(defaultValue = "false") boolean orderBy) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "All expenses have been successfully fetched.",
                        expenseService.getAllExpenses(offset, limit, sortBy, orderBy ? "desc" : "asc"),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Expense>> getExpenseById(@Positive @PathVariable Integer id){
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully founded.")
                .payload(expenseService.getExpenseById(id))
                .status(HttpStatus.CREATED)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<Expense>> createExpense(@Valid @RequestBody ExpenseRequest expenseRequest){
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully added.")
                .payload(expenseService.createExpense(expenseRequest))
                .status(HttpStatus.CREATED)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Expense>> updateExpense(@PathVariable Integer id, @Valid @RequestBody ExpenseRequest expenseRequest){
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully updated.")
                .payload(expenseService.updateExpense(id, expenseRequest))
                .status(HttpStatus.OK)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Expense>> deleteExpense(@PathVariable Integer id){
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully deleted.")
                .payload(expenseService.deleteExpense(id))
                .status(HttpStatus.OK)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
