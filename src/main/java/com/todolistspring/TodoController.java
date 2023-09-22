package com.todolistspring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller // a bean -> iOC
public class TodoController {

    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }
    @GetMapping("/")
    public String displayTodoList(@RequestParam(required = false) String message,
                                  @RequestParam(required = false) String error,
                                  Model model) {
        model.addAttribute("message", message);
        model.addAttribute("error", error);
        model.addAttribute("todoList", this.todoService.getAllTodoItems());
        return "todoList";
    }

    @GetMapping("/add-todo")
    public String displayAddTodoPage() {
        return "addTodo";
    }

    @PostMapping("/add-todo")
    public String createTodo(Todo todo) {
        try{
            System.out.println("Created todo item >>>>" + todo);
            this.todoService.addItem(todo);
            // make a redirect to another endpoint if we do not want to duplicate the code
            // what is already done to return the html we wnt to show
            return "redirect:/?message=TODO_CREATED_SUCCESS";
        } catch (Exception exception) {
            return "redirect:/?message=TODO_CREATION_FAILED&error=" + exception.getMessage();
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable() UUID id) {
        try {
            this.todoService.deleteTodoItem(id);
            return "redirect:/?message=TODO_DELETED_SUCCESSFULLY";
        } catch (Exception exception) {
            return "redirect:/?message=TODO_DELETE_FAILED&error=" + exception.getMessage();
        }

    }

    @GetMapping("/update-status/{id}/{status}")
    public String updateTodo(@PathVariable() UUID id, @PathVariable String status) {
        try {
            Todo foundTodo = this.todoService.findTodoById(id);
            foundTodo.setStatus(TodoStatus.valueOf(status));
            this.todoService.updateTodo(foundTodo);
            return "redirect:/?message=TODO_UPDATED_SUCCESSFULLY";
        } catch (Exception exception) {
            return "redirect:/?message=TODO_UPDATE_FAILED&error=" + exception.getMessage();
        }

    }

    @GetMapping("/edit/{id}")
    public String showEditTodoPage(@PathVariable UUID id, Model model) {
        try {
            Todo todo = this.todoService.findTodoById(id);
            model.addAttribute("todoItem", todo);
            return "editTodo";
        } catch (Exception exception) {
            return "redirect:/?message=TODO_EDIT_FAILED&error=" + exception.getMessage();
        }
    }

    @PostMapping("/edit/{id}")
    public String editTodo(@PathVariable UUID id, Todo todo) {
        try {
            this.todoService.findTodoById(id); // this is just to check that it exists
            todo.setId(id);
            this.todoService.updateTodo(todo);
            return "redirect:/?message=TODO_EDITED_SUCCESSFULLY";
        } catch (Exception exception) {
            return "redirect:/?message=TODO_EDIT_FAILED&error=" + exception.getMessage();
        }
    }
}
