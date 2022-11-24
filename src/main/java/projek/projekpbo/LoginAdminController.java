package projek.projekpbo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.IOException;

public class LoginAdminController {
    @FXML
    private TextField Pass;

    @FXML
    private Label warningLabel;
    private Stage stage;
    private Scene scene;


    public void switchToMenuAdmin(ActionEvent event) throws IOException{
        String Password = "123";
        String inputKosong = "";
        String Input = Pass.getText();

        if (Password.equals(Input)) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("menuAdmin.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            MenuAdminController menuStudent = fxmlLoader.getController();
            menuStudent.refreshData();
            stage.setScene(scene);
            stage.show();
        }
        else if (inputKosong.equals(Input)){
            warningLabel.setText("Masukkan Password dengan Benar");
        }
        else{
            warningLabel.setText("Password salah");
        }
    }

    public void switchBackMainMenu(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Room Booking Portal Universitas Hasanuddin");
        stage.setScene(scene);
        stage.show();
    }
}