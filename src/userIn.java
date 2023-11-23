import java.util.ArrayList;

public class userIn {
    
    private String idPelanggan;
    private String Nama;
    private String Servis;
    private String Tanggal;
    private String Harga;

    public userIn(String idPelanggan , String Nama , String Servis, String Tanggal, String Harga){
        this.idPelanggan =idPelanggan;
        this.Nama = Nama;
        this.Servis =Servis;
        this.Tanggal =Tanggal;
        this.Harga = Harga;
    }
    public void rolUserIn (ArrayList<String> idPelanggan, ArrayList<String> nama, ArrayList<String> servis,ArrayList<String> tanggal, ArrayList<String> harga, int Size ){
        for (int i = 0; i < Size; i++) {
            new userIn(idPelanggan.get(i), nama.get(i), servis.get(i), tanggal.get(i), harga.get(i));
        }
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getNama() {
        return Nama;
    }

    public String getServis() {
        return Servis;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public String getHarga() {
        return Harga;
    }


    
    
}
