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
    }

    private String formatTimestamp(LocalDateTime timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(timestamp);
    }
}
