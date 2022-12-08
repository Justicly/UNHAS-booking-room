package projek.projekpbo;

//import tools yang dibutuhkan
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

import java.sql.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuAdminController {
    ObservableList<PesananMahasiswa> pesananMahasiswa = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;


    //Memanggil FXML yang telah dibuat dari Scene Builder
    @FXML
    private TableView<PesananMahasiswa> tableView2;

    @FXML
    public TableColumn<PesananMahasiswa, String> kolomNIM2;

    @FXML
    public TableColumn<PesananMahasiswa, String> kolomTime2;

    @FXML
    public TableColumn<PesananMahasiswa, String> kolomKelas2;
    @FXML
    public TableColumn<PesananMahasiswa, String> kolomMulai2;
    @FXML
    public TableColumn<PesananMahasiswa, String> kolomSelesai2;

    @FXML
    public TableColumn<PesananMahasiswa, String> kolomStatus2;
    @FXML
    public TableColumn<PesananMahasiswa, String> kolomSelect2;
    private Stage stage;

    int studentId;

    public void initialize(){

        loadData();
    }

    //Memasukkan data ke database
    public void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentNIM);
            preparedStatement.setString(2, studentKelas);
            preparedStatement.setString(3, String.valueOf(studentTanggal));
            preparedStatement.setString(4, studentMulai);
            preparedStatement.setString(5, studentSelesai);
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(MenuStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String StatusTerima = "Diterima";
    String StatusTolak = "Ditolak";

    //Perubahan status menjadi "Diterima"
    public void getQueryTerima() {
        query = "UPDATE `tablebooking` SET "
                + "`NIM`=?,"
                + "`kelas`=?,"
                + "`tanggal`=?,"
                + "`mulai`=?,"
                + "`selesai`=?,"
                + "`status`='"+StatusTerima+"' WHERE id = '" + studentId +"'";
    }

    //Perubahan status menjadi "Ditolak"
    public void getQueryTolak() {
        query = "UPDATE `tablebooking` SET "
                + "`NIM`=?,"
                + "`kelas`=?,"
                + "`tanggal`=?,"
                + "`mulai`=?,"
                + "`selesai`=?,"
                + "`status`='"+StatusTolak+"' WHERE id = '" + studentId +"'";
    }

    //Refresh data untuk menampilkan data terbaru ketika terjadi suatu perubahan pada data
    public void refreshData(){
        try {
            pesananMahasiswa.clear();
            query = "SELECT * FROM `tablebooking`";
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
                tableView2.setItems(pesananMahasiswa);
            }
        }catch (SQLException ex) {
            Logger.getLogger(MenuStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Menampilkan data pada tabel yang ada di GUI
    public void loadData(){
        connection = DbConnect.getConnect();
        refreshData();
        //ini ambil nama parameter dari pesanan mahasiswa
        kolomNIM2.setCellValueFactory(new PropertyValueFactory<>("NIM"));
        kolomKelas2.setCellValueFactory(new PropertyValueFactory<>("kelas"));
        kolomTime2.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        kolomMulai2.setCellValueFactory(new PropertyValueFactory<>("mulai"));
        kolomSelesai2.setCellValueFactory(new PropertyValueFactory<>("selesai"));
        kolomStatus2.setCellValueFactory(new PropertyValueFactory<>("status"));
        kolomSelect2.setCellValueFactory(new PropertyValueFactory<>("pilihBaris"));
    }

    //Tombol log out dari menu admin
    public void switchToLogOutAdmin(ActionEvent event) throws IOException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Keluar");
        alert.setHeaderText(null);
        alert.setContentText("Anda yakin ingin keluar?");

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
    private String studentNIM;
    private String studentKelas;
    private String studentTanggal;
    private String studentMulai;
    private String studentSelesai;
    private String studentStatus;

    //Pembuatan parameter agar fungsi checkbox dapat mendeteksi data hanya pada keadaan checkbox di check atau dicentang
    void setTextField(int id, String NIM, String kelas, String tanggal, String mulai, String selesai, String status) {
        studentId = id;
        studentNIM = NIM;
        studentKelas = kelas;
        studentTanggal = tanggal ;
        studentMulai = mulai;
        studentSelesai = selesai;
        studentStatus = status;
    }

    //Tombol untuk admin dapat menerima kelas
    @FXML
    private void terimaKelas(ActionEvent event) {
        connection = DbConnect.getConnect();

        ObservableList<PesananMahasiswa> dataListTerima = FXCollections.observableArrayList();

        for(PesananMahasiswa bean : pesananMahasiswa)
        {
            if(bean.getPilihBaris().isSelected())
            {
                dataListTerima.add(bean);
                setTextField(bean.getId(), bean.getNIM(), bean.getKelas(), bean.getTanggal(), bean.getMulai(), bean.getSelesai(), bean.getStatus());
                getQueryTerima();
                
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setHeaderText(null);
                alert1.setContentText("Permintaan pemesanan kelas berhasil diterima!");
                alert1.showAndWait();
                
                insert();
            }
        }
        refreshData();
    }

    //Tombol untuk admin dapat menolak kelas
    @FXML
    private void tolakKelas(ActionEvent event) {
        connection = DbConnect.getConnect();

        ObservableList<PesananMahasiswa> dataListTolak = FXCollections.observableArrayList();

        for(PesananMahasiswa bean : pesananMahasiswa)
        {
            if(bean.getPilihBaris().isSelected())
            {
                dataListTolak.add(bean);
                setTextField(bean.getId(), bean.getNIM(), bean.getKelas(), bean.getTanggal(), bean.getMulai(), bean.getSelesai(), bean.getStatus());
                getQueryTolak();
                
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setHeaderText(null);
                alert1.setContentText("Permintaan pemesanan kelas berhasil ditolak!");
                alert1.showAndWait();
                
                insert();
            }

        }
        refreshData();
    }
}
