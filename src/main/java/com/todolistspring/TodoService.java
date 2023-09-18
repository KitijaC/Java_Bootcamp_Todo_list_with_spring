package com.todolistspring;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service // spring has control over #IOC
public class TodoService {
    private final ArrayList<Todo> todoList = new ArrayList<>();

    public Todo addItem(Todo todo) throws Exception {
        if (todo.getDescription().isBlank()) throw new Exception("Please provide description");
        this.todoList.add(todo);
        return todo;
    }

    public Todo updateTodo(Todo todo) throws Exception {
        this.todoList.forEach((currentTodo) -> {
            if (currentTodo.equals(todo)) {
                currentTodo.setDescription(todo.getDescription());
                currentTodo.setStatus(todo.getStatus());
                currentTodo.setPriority(todo.getPriority());
            }
        });

        return todo;
    }

    public ArrayList<Todo> getAllTodoItems() {
        return this.todoList;
    }
}
