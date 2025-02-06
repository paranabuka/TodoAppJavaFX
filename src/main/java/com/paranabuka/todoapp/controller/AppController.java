package com.paranabuka.todoapp.controller;

import com.paranabuka.todoapp.App;
import com.paranabuka.todoapp.dto.Task;
import com.paranabuka.todoapp.managers.TaskList;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppController {

    private TaskList taskList;

    public VBox taskListVBox;
    public MFXComboBox<String> statusComboBox;

    public void initialize() {
        taskList = new TaskList();
        statusComboBox.getItems().addAll("All", "ToDo", "InProgress", "Done");
        statusComboBox.setValue("All");
        statusComboBox.valueProperty().addListener(statusChangeListener());

        addTask(
                "Create a JavaFX Project",
                "JavaFX is an open source, next generation client application platform for " +
                        "desktop, mobile and embedded systems built on Java",
                "Done",
                LocalDateTime.now().minusMinutes(60)
        );
        addTask(
                "Learn Spring Boot",
                "Spring Boot makes it easy to create stand-alone, production-grade Spring based " +
                        "Applications that you can \"just run\"",
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
        showAddTaskDialog();
    }

    public void showAddTaskDialog() {
        try {
            FXMLLoader loader = App.fxmlLoader("add_task");
            VBox dialog = loader.load();
            AddTaskDialogController dialogController = loader.getController();
            dialogController.setMainController(this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Task");
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(dialog);
            scene.getStylesheets().add(App.loadStylesheet("add_task.css"));
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTaskFromDialog(String title, String description) {
        addTask(title, description, "ToDo", LocalDateTime.now());
    }

    private void addTask(String title, String description, String status, LocalDateTime createdAt) {
        Task newTask = new Task(title, description, status, createdAt);
        taskList.addTask(newTask);
        redrawTasks();
    }

    private void displayTask(Task task) {
        try {
            FXMLLoader loader = App.fxmlLoader("task_card");

            HBox taskCard = loader.load();
            TaskCardController controller = loader.getController();

            controller.setDetails(
                    task.getId(),
                    task.getTitle(),
                    task.getCreatedAt(),
                    task.getStatus(),
                    this
            );

            taskListVBox.getChildren().add(taskCard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterTasksByStatus(String status) {
        taskListVBox.getChildren().clear();

        List<Task> filteredTasks;
        if (status.equals("All")) {
            filteredTasks = taskList.getTasks();
        } else {
            filteredTasks = taskList.getTasks().stream().filter(t -> {
                return t.getStatus().equals(status);
            }).collect(Collectors.toList());
        }

        for (Task t : filteredTasks) { displayTask(t); }
    }

    public void redrawTasks() {
        taskListVBox.getChildren().clear();
        taskList.getTasks().forEach(this::displayTask);
        statusComboBox.setValue("All");
    }

    private ChangeListener<String> statusChangeListener() {
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterTasksByStatus(newValue);
            }
        };
    }
}
