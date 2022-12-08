package projek.projekpbo;

//import semua tools yang dibutuhkan
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginAdminController {
    ///Memanggil FXML yang telah dibuat di SceneBuilder
    @FXML
    private TextField fieldUname;
    @FXML
    private TextField fieldPass;

    @FXML
    private Label warningLabelUsername;
    @FXML
    private Label warningLabelPass;
    @FXML
    private Label warningLabel;
    private Stage stage;
    private Scene scene;

    Connection con;
    PreparedStatement pst;

    ResultSet rs;

    //Method untuk tombol pindah ke scene Menu Admin
    public void switchToMenuAdmin(ActionEvent event) throws IOException{
        warningLabelUsername.setText(null);
        warningLabelPass.setText(null);
        warningLabel.setText(null);

        String inputKosong = "";

        String InputUsername = fieldUname.getText();
        String InputPass = fieldPass.getText();

        if(inputKosong.equals(InputUsername) && inputKosong.equals(InputPass)){
            warningLabelUsername.setText("Masukkan Username dengan benar");
            warningLabelPass.setText("Masukkan Password dengan Benar");
        }

        else {
            try {
                //connect ke database
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/dbbook", "root", "");
                pst = con.prepareStatement("select * from admin where username=? and pass=?");

                pst.setString(1, InputUsername);
                pst.setString(2, InputPass);

                rs = pst.executeQuery();

                if (rs.next()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("menuAdmin.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(fxmlLoader.load());
                    MenuAdminController menuAdmin = fxmlLoader.getController();
                    menuAdmin.refreshData();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    warningLabel.setText("Username atau Password yang Anda Masukkan Salah!");
                    fieldUname.setText("");
                    fieldPass.setText("");
                    fieldUname.requestFocus();
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginAdminController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(LoginAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Method untuk tombol pindah ke scene Main menu
    public void switchBackMainMenu(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Room Booking Portal Universitas Hasanuddin");
        stage.setScene(scene);
        stage.show();
    }
}
