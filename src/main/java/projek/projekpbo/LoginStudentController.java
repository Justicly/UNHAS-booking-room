package projek.projekpbo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginStudentController {

    @FXML
    private Label warningLabelNIM;

    @FXML
    private Label warningLabelNIMPassStud;

    @FXML
    private Label warningKesalahan;
    @FXML
    private TextField fieldPass;
    @FXML
    private TextField fieldNIM;
    private Stage stage;
    private Scene scene;

    public void switchToMenuStudent(ActionEvent event) throws IOException {
        warningLabelNIMPassStud.setText(null);
        warningLabelNIM.setText(null);
        warningKesalahan.setText(null);

        String Username = "D121211001";
        String Password = "123456";

        String Username2 = "D121211000";
        String Password2 = "654321";
        String inputKosong = "";

        String InputNIM = fieldNIM.getText();
        String InputPass = fieldPass.getText();

        if (Password.equals(InputPass) && Username.equals(InputNIM) || Password2.equals(InputPass) && Username2.equals(InputNIM)) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("menuStudent.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            MenuStudentController menuStudent = fxmlLoader.getController();
            menuStudent.displayName(InputNIM, InputPass);
            menuStudent.refreshData();
            stage.setScene(scene);
            stage.show();
        } else if (inputKosong.equals(InputPass) && inputKosong.equals(InputNIM)) {
            warningLabelNIMPassStud.setText("Masukkan Password dengan Benar");
            warningLabelNIM.setText("Masukkan Username dengan benar");

        } else {
            warningKesalahan.setText("NIM atau Password yang Anda Masukkan Salah");
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