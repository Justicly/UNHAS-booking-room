package projek.projekpbo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    private Stage stage;
    private Scene scene;

    public void switchTologinAdmin(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("loginAdmin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDataStudent(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("dataStudent.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}