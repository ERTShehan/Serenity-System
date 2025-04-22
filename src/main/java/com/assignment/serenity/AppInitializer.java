package com.assignment.serenity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
        primaryStage.setTitle("Serenity Center");
        Parent root = loader.load();
//        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon-helth.png")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
//        new AppInitializer().initializeDatabase();
        launch(args);
    }


}
