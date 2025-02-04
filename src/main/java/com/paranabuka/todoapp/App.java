package com.paranabuka.todoapp;

import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private static final String APP_TITLE = "Todo App";

    @Override
    public void start(Stage stage) throws IOException {
        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA)
                .themes(MaterialFXStylesheets.forAssemble(true))
                .setDeploy(true)
                .setResolveAssets(true)
                .build()
                .setGlobal();

        Scene scene = new Scene(loadFXML("app"));
        scene.getStylesheets().add(loadStylesheet());
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle(APP_TITLE);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setMaxHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public static FXMLLoader fxmlLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        return fxmlLoader(fxml).load();
    }

    private static String loadStylesheet() {
        return App.class.getResource("styles.css").toExternalForm();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
