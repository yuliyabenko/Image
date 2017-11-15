package sample.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.MainController;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../fxml/mainWindow.fxml"));
        Parent fxmlMain = fxmlLoader.load();

        this.primaryStage.setTitle("Image Editor");
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);
        this.primaryStage.setMinWidth(650);
        this.primaryStage.setMinHeight(550);
        this.primaryStage.setScene(new Scene(fxmlMain, 650, 800));
        this.primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
