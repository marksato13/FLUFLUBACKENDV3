package com.example.demo.controller.error;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
public class GenericError {
	protected ResponseEntity<List<Map<String, String>>> getError(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors()
                .stream().map(e -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(e.getField(), e.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
