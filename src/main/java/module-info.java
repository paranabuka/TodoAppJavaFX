module com.paranabuka.todoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;


    opens com.paranabuka.todoapp.controller to javafx.fxml;
    exports com.paranabuka.todoapp;
}