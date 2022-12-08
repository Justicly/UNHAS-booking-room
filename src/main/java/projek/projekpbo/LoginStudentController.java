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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    Connection con;
    PreparedStatement pst;

    ResultSet rs;

    public void switchToMenuStudent(ActionEvent event) throws IOException {
        warningLabelNIMPassStud.setText(null);
        warningLabelNIM.setText(null);
        warningKesalahan.setText(null);

        String inputKosong = "";

        String InputNIM = fieldNIM.getText();
        String InputPass = fieldPass.getText();

        if(inputKosong.equals(InputNIM) && inputKosong.equals(InputPass)){
            warningLabelNIMPassStud.setText("Masukkan Password dengan Benar");
            warningLabelNIM.setText("Masukkan Username dengan benar");
        }

        else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/dbbook", "root", "");
                pst = con.prepareStatement("select * from users where nim=? and pass=?");

                pst.setString(1, InputNIM);
                pst.setString(2, InputPass);

                rs = pst.executeQuery();

                if (rs.next()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("menuStudent.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(fxmlLoader.load());
                    MenuStudentController menuStudent = fxmlLoader.getController();
                    menuStudent.displayName(InputNIM, InputPass);
                    menuStudent.refreshData();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    warningKesalahan.setText("NIM atau Password yang Anda Masukkan Salah");
                    fieldNIM.setText("");
                    fieldPass.setText("");
                    fieldNIM.requestFocus();
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginStudentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(LoginStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
