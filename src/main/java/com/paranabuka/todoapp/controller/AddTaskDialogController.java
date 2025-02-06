package com.paranabuka.todoapp.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddTaskDialogController {
    public MFXTextField taskTitleField;
    public TextArea taskDescriptionField;

    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeDialog();
    }

    public void handleSubmit(ActionEvent actionEvent) {
        String title = taskTitleField.getText();
        if (!title.isBlank()) {
            mainController.addTaskFromDialog(title, taskDescriptionField.getText());
            closeDialog();
        } else {
            System.out.println("Title can not be blank");
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) taskTitleField.getScene().getWindow();
        stage.close();
    }
}
