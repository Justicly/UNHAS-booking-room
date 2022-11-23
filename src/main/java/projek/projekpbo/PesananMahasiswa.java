package projek.projekpbo;

import javafx.scene.control.CheckBox;

public class PesananMahasiswa {

    private int id;
    private String NIM;
    private String kelas;
    private String tanggal;
    private String status;
    private String mulai;
    private String selesai;

    private CheckBox pilihBaris;

    public PesananMahasiswa(int id, String NIM, String kelas, String tanggal, String mulai, String selesai, String status, String pilihBaris) {
        this.id = id;
        this.NIM = NIM;
        this.kelas = kelas;
        this.tanggal = tanggal;
        this.mulai = mulai;
        this.selesai = selesai;
        this.status = status;
        this.pilihBaris = new CheckBox();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNIM() {
        return NIM;
    }

    public void setNIM(String NIM) {
        this.NIM = NIM;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSelesai() {
        return selesai;
    }

    public void setSelesai(String selesai) {
        this.selesai = selesai;
    }

    public CheckBox getPilihBaris() {
        return pilihBaris;
    }

    public void setPilihBaris(CheckBox pilihBaris) {
        this.pilihBaris = pilihBaris;
    }


}


