package projek.projekpbo;

import helpers.DbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MenuStudentController {
    ObservableList<PesananMahasiswa> pesananMahasiswa = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    String Status = "Menunggu";

    public void displayName(String InputNIM, String InputPass){
        if(InputNIM.equals("D121211001")){
            namaLabel.setText("Roger Udin");
        }else{
            namaLabel.setText("Slamet Ngawi");
        }
        NIMLabel.setText(InputNIM);
    }

    @FXML
    private TableView<PesananMahasiswa> tableView;

    @FXML
    public TableColumn<PesananMahasiswa, String> kolomNIM;

    @FXML
    public TableColumn<PesananMahasiswa, String> kolomTime;

    @FXML
    public TableColumn<PesananMahasiswa, String> kolomKelas;
    @FXML
    public TableColumn<PesananMahasiswa, String> kolomMulai;
    @FXML
    public TableColumn<PesananMahasiswa, String> kolomSelesai;

    @FXML
    public TableColumn<PesananMahasiswa, String> kolomStatus;
    ObservableList<String> pilihKelasList = FXCollections.observableArrayList("G01", "101", "202");

    private Stage stage;
    @FXML
    ChoiceBox<String> pilihKelasBox;
    @FXML
    DatePicker tanggalKelas;
    @FXML
    TextField fieldMulaiKelas;

    @FXML
    TextField fieldSelesaiKelas;
    @FXML
    Label namaLabel;
    @FXML
    Label NIMLabel;

    private void getQuery() {
        query = "INSERT INTO `tablebooking`(`NIM`, `kelas`, `tanggal`, `mulai`, `selesai`, `status`) VALUES (?,?,?,?,?,?)";
    }

    public void insert() {

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, NIMLabel.getText());
            preparedStatement.setString(2, pilihKelasBox.getValue());
            preparedStatement.setString(3, String.valueOf(tanggalKelas.getValue()));
            preparedStatement.setString(4, fieldMulaiKelas.getText());
            preparedStatement.setString(5, fieldSelesaiKelas.getText());
            preparedStatement.setString(6, Status);
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(MenuStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void initialize() throws ClassNotFoundException {
        loadData();
        pilihKelasBox.setItems(pilihKelasList);
    }

    public void refreshData(){
        try {
            pesananMahasiswa.clear();
            query = "SELECT * FROM `tablebooking` WHERE NIM ='"+NIMLabel.getText()+"'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pesananMahasiswa.add(new PesananMahasiswa(
                        resultSet.getInt("id"),
                        resultSet.getString("NIM"),
                        resultSet.getString("kelas"),
                        resultSet.getString("tanggal"),
                        resultSet.getString("mulai"),
                        resultSet.getString("selesai"),
                        resultSet.getString("status"),
                        resultSet.getString("pilih")));
                tableView.setItems(pesananMahasiswa);
            }
        }catch (SQLException ex) {
            Logger.getLogger(MenuStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void checkData(){
        try {
            pesananMahasiswa.clear();
            query = "SELECT * FROM `tablebooking` WHERE (kelas ='"+pilihKelasBox.getValue()+"') AND (tanggal= '"+tanggalKelas.getValue()+"') AND (mulai >= '"+fieldMulaiKelas.getText()+":00' AND mulai <= '"+fieldSelesaiKelas.getText()+":00') OR (selesai >= '"+fieldMulaiKelas.getText()+":00' AND selesai <= '"+fieldSelesaiKelas.getText()+":00')";
            //query = "SELECT * FROM `tablebooking` WHERE kelas ='"+pilihKelasBox.getValue()+"' AND tanggal= '"+tanggalKelas.getValue()+"' AND mulai>='"+fieldMulaiKelas.getText()+":00' AND (selesai <= '"+fieldSelesaiKelas.getText()+":00' OR selesai >= '"+fieldSelesaiKelas.getText()+":00')";
            //query = "SELECT * FROM `tablebooking` WHERE (kelas ='"+pilihKelasBox.getValue()+"') AND (tanggal= '"+tanggalKelas.getValue()+"') AND (mulai>='"+fieldMulaiKelas.getText()+":00' AND selesai <= '"+fieldSelesaiKelas.getText()+":00')";
            // A>=12:00 AND (B<=13:00 OR B>=13:00)
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("This Class not available");
                alert.showAndWait();
            }else{
                getQuery();
                insert();
            }
        }catch (SQLException ex) {
            Logger.getLogger(MenuStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void loadData(){
        connection = DbConnect.getConnect();
        refreshData();
        kolomNIM.setCellValueFactory(new PropertyValueFactory<>("NIM"));
        kolomKelas.setCellValueFactory(new PropertyValueFactory<>("kelas"));
        kolomTime.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        kolomMulai.setCellValueFactory(new PropertyValueFactory<>("mulai"));
        kolomSelesai.setCellValueFactory(new PropertyValueFactory<>("selesai"));
        kolomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void buttonPesanKelas (ActionEvent event) throws IOException{
        connection = DbConnect.getConnect();
        String tanggal = String.valueOf(tanggalKelas.getValue());
        String mulai = fieldMulaiKelas.getText();
        String selesai = fieldSelesaiKelas.getText();


        if (tanggal.isEmpty() || mulai.isEmpty() || selesai.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            checkData();
        }
        refreshData();
    }

    public void switchToLogOutStudent(ActionEvent event) throws IOException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Are You Sure to Log Out?");

        if (alert.showAndWait().get() == ButtonType.OK){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Room Booking Portal Universitas Hasanuddin");
            stage.setScene(scene);
            stage.show();
        }
        else{
            event.consume();
        }
    }
}