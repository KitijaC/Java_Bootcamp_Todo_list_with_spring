package com.todolistspring;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service // spring has control over #IOC
public class TodoService {
    private final ArrayList<Todo> todoList = new ArrayList<>();

    public Todo addItem(Todo todo) throws Exception {
        if (todo.getDescription().isBlank()) throw new Exception("Please provide description");
        this.todoList.add(todo);
        return todo;
    }

    public Todo updateTodo(Todo todo) throws Exception {
        for (Todo currentTodo: this.todoList) {
            if (currentTodo.getId().equals(todo.getId())) {
                currentTodo.setDescription(todo.getDescription());
                currentTodo.setStatus(todo.getStatus());
                currentTodo.setPriority(todo.getPriority());
                return currentTodo;
            }
        }
        throw new Exception("Todo item not found!");
    }

    public ArrayList<Todo> getAllTodoItems() {
        return this.todoList;
    }

    public void deleteTodoItem(UUID id) throws Exception {
        if (!this.todoList.removeIf((todoItem) -> todoItem.getId().equals(id)))
            throw new Exception("Item with specified id not found");
    }

    public Todo findTodoById(UUID id) throws Exception {
        for (Todo todo: this.todoList) {
            if (todo.getId().equals(id))return todo;
        }
        throw new Exception("Todo item not found");
    }
}
