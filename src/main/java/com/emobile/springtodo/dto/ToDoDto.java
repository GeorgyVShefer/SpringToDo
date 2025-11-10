package com.emobile.springtodo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToDoDto implements Serializable{

    private Long id;
    @NotBlank(message = "Title must not be blank")
    @Size(max = 50, message = "Title must be at most 50 characters")
    private String title;
    @Size(max = 100, message = "Description must be at most 1000 characters")
    private String description;
    private Boolean completed;
}
