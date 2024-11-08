package com.example.demo.controller.base;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResMessage {
	private String message;
    private Boolean success = false;
    private LinkedHashMap<String, Object> data;
}