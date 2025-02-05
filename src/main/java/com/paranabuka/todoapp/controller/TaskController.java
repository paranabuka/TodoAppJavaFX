package com.paranabuka.todoapp.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskController {

    public Label title;
    public Label timestamp;
    public Label status;

    public void handleViewTask(ActionEvent actionEvent) {
        System.out.println("Viewing task: " + title.getText());
    }

    public void setDetails(String title, LocalDateTime timestamp, String status) {
        this.title.setText(title);
        this.timestamp.setText(formatTimestamp(timestamp));
        this.status.setText(status);
        applyStatusColor(status);
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
