package com.paranabuka.todoapp.controller;

import com.paranabuka.todoapp.App;
import com.paranabuka.todoapp.dto.Task;
import com.paranabuka.todoapp.managers.TaskList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskCardController {

    public String taskId;
    public Label title;
    public Label timestamp;
    public Label status;

    private final TaskList taskList = new TaskList();

    private AppController mainController;

    public void setDetails(String taskId, String title, LocalDateTime timestamp, String status,
                           AppController mainController) {
        this.taskId = taskId;
        this.title.setText(title);
        this.timestamp.setText(formatTimestamp(timestamp));
        this.status.setText(status);
        applyStatusColor(status);

        this.mainController = mainController;
    }

    public void handleViewTask(ActionEvent actionEvent) {
        Task task = taskList.getTaskById(taskId);
        showTaskDialog(task);
    }

    public void showTaskDialog(Task task) {
        try {
            FXMLLoader loader = App.fxmlLoader("task_details");
            VBox dialog = loader.load();
            TaskDetailsController taskDetailsController = loader.getController();
            taskDetailsController.setTaskDetails(task, this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title.getText());
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(dialog);
            scene.getStylesheets().add(App.loadStylesheet("task_details.css"));
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        taskList.updateTask(task);
        title.setText(task.getTitle());
        status.setText(task.getStatus());
        applyStatusColor(task.getStatus());

        mainController.redrawTasks();
    }

    private void applyStatusColor(String status) {
        switch (status) {
            case "ToDo":
                this.status.setStyle("-fx-text-fill: gray;");
                break;
            case "InProgress":
                this.status.setStyle("-fx-text-fill: orange;");
                break;
            case "Done":
                this.status.setStyle("-fx-text-fill: green;");
                break;
            default:
                this.status.setStyle("-fx-text-fill: black;");
                break;
        }
    }

    private String formatTimestamp(LocalDateTime timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(timestamp);
    }
}
