package com.paranabuka.todoapp.controller;

import com.paranabuka.todoapp.dto.Task;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TaskDetailsController {
    public MFXTextField taskTitleField;
    public TextArea taskDescriptionField;
    public MFXComboBox<String> taskStatusField;
    public MFXTextField commentField;
    public VBox commentList;

    private Task task;
    private TaskCardController mainController;

    public void setTaskDetails(Task task, TaskCardController mainController) {
        this.task = task;
        this.mainController = mainController;

        taskTitleField.setText(task.getTitle());
        taskDescriptionField.setText(task.getDescription());
        taskStatusField.getItems().clear();
        taskStatusField.getItems().addAll("ToDo", "InProgress", "Done");
        Platform.runLater(() -> {
            taskStatusField.setValue(task.getStatus());
        });
        task.getComments().forEach(this::displayComment);
    }

    private void displayComment(String comment) {
        Text commentLabel = new Text(comment);
        commentLabel.setStyle("-fx-padding: 3px;");
        commentList.getChildren().addFirst(commentLabel);
    }

    public void handleAddComment(ActionEvent actionEvent) {
        String comment = commentField.getText();
        if (!comment.isBlank()) {
            task.addComment(comment);
            displayComment(comment);
            commentField.clear();
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeDialog();
    }

    public void handleUpdate(ActionEvent actionEvent) {
        task.setTitle(taskTitleField.getText());
        task.setDescription(taskDescriptionField.getText());
        task.setStatus(taskStatusField.getValue());

        mainController.updateTask(task);
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) taskTitleField.getScene().getWindow();
        stage.close();
    }
}
