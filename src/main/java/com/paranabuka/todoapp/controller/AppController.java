package com.paranabuka.todoapp.controller;

import com.paranabuka.todoapp.App;
import com.paranabuka.todoapp.dto.Task;
import com.paranabuka.todoapp.managers.TaskList;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.beans.value.ChangeListener;
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
        redrawTasks();
    }

    @SuppressWarnings("unused")
    public void handleAddTask(ActionEvent actionEvent) {
        showAddTaskDialog();
    }

    @SuppressWarnings("CallToPrintStackTrace")
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
        addTask(title, description, LocalDateTime.now());
    }

    private void addTask(String title, String description, LocalDateTime createdAt) {
        Task newTask = new Task(title, description, "ToDo", createdAt);
        taskList.addTask(newTask);
        redrawTasks();
    }

    @SuppressWarnings("CallToPrintStackTrace")
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
            filteredTasks = taskList.getTasks()
                    .stream()
                    .filter(t -> t.getStatus().equals(status))
                    .collect(Collectors.toList());
        }

        for (Task t : filteredTasks) { displayTask(t); }
    }

    public void redrawTasks() {
        taskListVBox.getChildren().clear();
        taskList.getTasks().forEach(this::displayTask);
        statusComboBox.setValue("All");
    }

    private ChangeListener<String> statusChangeListener() {
        return (observable, oldValue, newValue) ->
                filterTasksByStatus(newValue);
    }
}
