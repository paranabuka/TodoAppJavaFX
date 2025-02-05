package com.paranabuka.todoapp.controller;

import com.paranabuka.todoapp.App;
import com.paranabuka.todoapp.dto.Task;
import com.paranabuka.todoapp.managers.TaskList;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;

public class AppController {

    private TaskList taskList;

    public VBox taskListVBox;
    public MFXComboBox<String> statusComboBox;

    public void initialize() {
        taskList = new TaskList();
        statusComboBox.getItems().addAll("All", "ToDo", "InProgress", "Done");
        statusComboBox.setValue("All");

        addTask(
                "Create a JavaFX Project",
                "JavaFX is an open source, next generation client application platform for desktop, mobile and embedded systems built on Java",
                "Done",
                LocalDateTime.now().minusMinutes(60)
        );
        addTask(
                "Learn Spring Boot",
                "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can \"just run\"",
                "ToDo",
                LocalDateTime.now().minusMinutes(15)
        );
        addTask(
                "Create a ToDo App",
                "A productivity tool to ease your day",
                "InProgress",
                LocalDateTime.now().minusMinutes(45)
        );
    }

    public void handleAddTask(ActionEvent actionEvent) {
        addTask("New Task", "A brand new task", "ToDo", LocalDateTime.now());
    }

    private void addTask(String title, String description, String status, LocalDateTime createdAt) {
        Task newTask = new Task(title, description, status, createdAt);
        taskList.addTask(newTask);
        displayTask(newTask);
    }

    private void displayTask(Task task) {
        try {
            FXMLLoader loader = App.fxmlLoader("task");

            HBox taskCard = loader.load();
            TaskController controller = loader.getController();

            controller.setDetails(task.getTitle(), task.getCreatedAt(), task.getStatus());
            taskListVBox.getChildren().add(taskCard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
