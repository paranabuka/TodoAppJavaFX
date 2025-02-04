package com.paranabuka.todoapp.controller;

import com.paranabuka.todoapp.App;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;

public class AppController {
    public VBox taskList;
    public MFXComboBox<String> statusComboBox;

    public void initialize() {
        statusComboBox.getItems().addAll("All", "TODO", "IN PROGRESS", "DONE");
        statusComboBox.setValue("ALL");

        addTask("Create a JavaFX Project", LocalDateTime.now().minusMinutes(60), "DONE");
        addTask("Learn Spring Boot", LocalDateTime.now().minusMinutes(15), "TODO");
        addTask("Create a ToDo App", LocalDateTime.now().minusMinutes(45), "IN PROGRESS");
    }

    public void handleAddTask(ActionEvent actionEvent) {
        addTask("New Task", LocalDateTime.now(), "TODO");
    }

    private void addTask(String title, LocalDateTime timestamp, String status) {
        try {
            FXMLLoader loader = App.fxmlLoader("task");

            HBox task = loader.load();
            TaskController controller = loader.getController();

            controller.setDetails(title, timestamp, status);
            taskList.getChildren().add(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
