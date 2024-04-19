package org.example.kps_group_01_spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.example.kps_group_01_spring_mini_project.model.Expense;
import org.example.kps_group_01_spring_mini_project.model.User;
import org.example.kps_group_01_spring_mini_project.model.constant.SortBy;
import org.example.kps_group_01_spring_mini_project.model.dto.response.APIResponse;
import org.example.kps_group_01_spring_mini_project.model.dto.request.ExpenseRequest;
import org.example.kps_group_01_spring_mini_project.service.ExpenseService;
import org.example.kps_group_01_spring_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;
    @GetMapping
    public ResponseEntity<APIResponse<List<Expense>>> getAllExpenses(
            @Positive @RequestParam(defaultValue = "1") Integer offset,
            @Positive @RequestParam(defaultValue = "5") Integer limit,
            @RequestParam SortBy sortBy,
            @RequestParam(defaultValue = "false") boolean orderBy) {
        APIResponse<List<Expense>> response = APIResponse.<List<Expense>>builder()
                .message("The expenses have been successfully founded.")
                .payload(expenseService.getAllExpenses(offset, limit, sortBy.name(), orderBy))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Expense>> getExpenseById(@PathVariable String id){
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully founded.")
                .payload(expenseService.getExpenseById(id))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<Expense>> createExpense(@RequestBody @Valid ExpenseRequest expenseRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail);

        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully added.")
                .payload(expenseService.createExpense(expenseRequest, user.getUserId()))
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Expense>> updateExpense( @PathVariable String id,@RequestBody @Valid ExpenseRequest expenseRequest){
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully updated.")
                .payload(expenseService.updateExpense(id, expenseRequest))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id){
        expenseService.deleteExpense(id);
        APIResponse<Expense> response = APIResponse.<Expense>builder()
                .message("The expense has been successfully deleted.")
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

