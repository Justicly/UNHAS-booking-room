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
import javafx.scene.input.MouseEvent;
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
    PesananMahasiswa student = null ;
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
    private Scene scene;
    private FXMLLoader fxmlLoader;
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
        //make sure the property value factory should be exactly same as the e.g getStudentId from your model class
        //add your data to the table here.
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

    // add your data here from any source
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
            getQuery();
            insert();
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