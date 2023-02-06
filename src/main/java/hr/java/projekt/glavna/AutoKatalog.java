package hr.java.projekt.glavna;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AutoKatalog extends Application {
    private static Stage appStage;

    @Override
    public void start(Stage stage) throws IOException {
        appStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(AutoKatalog.class.getResource("views/pocetni-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),800,600);
        stage.setTitle("Katalog autodijelova");
        stage.setScene(scene);
        stage.show();
    }
    public static void setMainPage(Pane root) {
        Scene scene = new Scene(root,800,600);
        appStage.setScene(scene);
        appStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}