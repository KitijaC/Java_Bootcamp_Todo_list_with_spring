package com.todolistspring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Todo {
    private UUID id = UUID.randomUUID();
    private String description;
    private TodoStatus status;
    private TodoPriority priority;
    private Date createdAt = new Date();
}
