package com.todolistspring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
