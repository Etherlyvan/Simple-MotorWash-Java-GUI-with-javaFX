import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.print.DocFlavor.URL;

import com.microsoft.sqlserver.jdbc.dataclassification.Label;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.Node;
import javafx.event.ActionEvent;


public class MainSceneController implements Initializable {
    
    
    ArrayList <String> idPelang;
    ArrayList <String> namae;
    ArrayList <String> Servise;
    ArrayList <String> tanggale;
    ArrayList <String> hargae;
    String idTemmpt ="";
    
    ObservableList<userIn> listin = FXCollections.observableArrayList();
    ObservableList<userIn> listTemp = FXCollections.observableArrayList();
    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getList();
        tDate.setText(seDate());  
      
    }
    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }
    

    private Stage stage;
    private Scene scene;
    private Parent root;
    String idPelanggan = "oi";
    String idPel = "oio";
    

    public void switchToCAtalogue(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Catalogue.fxml"));	
		root = loader.load();	   
        MainSceneController sceneUbah = loader.getController();
        //MainSceneController.idPelanggan
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        
        stage.show();
    }

        public void switchToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));	
		root = loader.load();	   
        MainSceneController sceneUbah = loader.getController();
        //MainSceneController.idPelanggan
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        
        stage.show();
    }
    

    public void switchToMainScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void insertPelanggan (String idPelanggan, String nama,String noTelp, String tipe){
        //Untuk Menambah Tabel Pelanggan
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
        try (Connection connection = DriverManager.getConnection(url)) {
            // Query SQL untuk menyisipkan data pelanggan
            String sql = "INSERT INTO Pelanggan (IdPelanggan, Nama, NoTelp, Tipe) VALUES (?, ?, ?, ?)";

            // Membuat objek PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Menetapkan nilai parameter untuk pelanggan pertama
                preparedStatement.setString(1, idPelanggan);
                preparedStatement.setString(2, nama);
                preparedStatement.setString(3, noTelp);
                preparedStatement.setString(4, tipe);

                preparedStatement.executeUpdate();
  
                System.out.println("Data pelanggan berhasil disisipkan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public String idPelangganGen(String out ){
        
        //Untuk Membuat IdPelanggan
        int sumPelanggan=0;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
        try (Connection connection = DriverManager.getConnection(url);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS JumlahPelanggan FROM Pelanggan");
        ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
            //Mencari Jumlah Pelanggan yang ada di Database
            sumPelanggan = resultSet.getInt("JumlahPelanggan");
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Mencari Tanggal Hari dan Bulan sQL
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        LocalDate localDateFromSqlDate = sqlDate.toLocalDate();
        System.out.println(localDateFromSqlDate.getDayOfMonth()+" "+localDateFromSqlDate.getMonth().getValue()+" "+localDateFromSqlDate.getYear()+" ");
        out =Integer.toString(localDateFromSqlDate.getMonth().getValue())+""+localDateFromSqlDate.getDayOfMonth()+""+Integer.toString(sumPelanggan);

        //Return IdPelanggan
        return out;
    }
    public void delIdUp(String idToDelete){
        String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";

        // Id of the record you want to delete
        String recordId = idToDelete;

        try {
            // Establishing a connection to the database
            Connection connection = DriverManager.getConnection(jdbcUrl);

            // SQL query to delete a record based on Id
            String deleteQuery = "DELETE FROM Pelanggan WHERE IdPelanggan = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                // Set the Id parameter
                preparedStatement.setString(1, recordId);

                // Execute the DELETE statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Record with Id " + recordId + " deleted successfully.");
                } else {
                    System.out.println("No records deleted. Record with Id " + recordId + " not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saerchAll(String whatTosearch){
        
          // Informasi koneksi database
          String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";

          // Data yang ingin dicari
          String searchId = whatTosearch;
          String searchNama = whatTosearch;
          String searchSNama = whatTosearch;
          String searchTanggal = whatTosearch; // Sesuaikan dengan format tanggal yang digunakan
  
          try {
            // Membuat koneksi
            Connection connection = DriverManager.getConnection(jdbcUrl);

            // Kueri SQL untuk mencari data
            String searchQuery =  
            "SELECT pd.IdPelanggan, pd.Nama, s.sNama, sd.Tanggal, sd.Price " +
            "FROM Pelanggan pd " +
            "LEFT JOIN Servis s ON pd.IdPelanggan = s.IdPelanggan " +
            "LEFT JOIN ServisDetail sd ON pd.IdPelanggan = sd.IdPelanggan " +
            "WHERE pd.IdPelanggan = ? OR pd.Nama =  ? OR s.sNama = ? OR sd.Tanggal=  ? ";

            // Membuat pernyataan persiapan
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
                // Mengatur parameter
                preparedStatement.setString(1, searchId);
                preparedStatement.setString(2, searchNama);
                preparedStatement.setString(3, searchSNama);
                preparedStatement.setString(4, searchTanggal);

                // Mengeksekusi kueri SELECT
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Menampilkan hasil kueri
                    while (resultSet.next()) {
                        String idPelanggan = resultSet.getString("IdPelanggan");
                        String nama = resultSet.getString("Nama");
                        String sNama = resultSet.getString("sNama");
                        String tanggal = resultSet.getString("Tanggal");
                        double price = resultSet.getDouble("Price");

                        System.out.println("IdPelanggan: " + idPelanggan +
                                ", Nama: " + nama +
                                ", sNama: " + sNama +
                                ", Tanggal: " + tanggal +
                                ", Price: " + price);
                        listTemp.add(new userIn(idPelanggan, nama, sNama, tanggal, Double.toString(price)));
                    }
                    datCol.setItems(listTemp);
                   
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

    }

    public String searId(String id, String namaSers){
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
              // Data yang ingin dicari
        String idPelanggan = id;
        String sNama = namaSers;
        String idS= "";

        try {
            // Membuat koneksi
            Connection connection = DriverManager.getConnection(url);

            // Kueri SQL untuk mencari data
            String selectQuery = "SELECT IdServis FROM Servis WHERE IdPelanggan = ? AND sNama = ?";

            // Membuat pernyataan persiapan
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                // Mengatur parameter
                preparedStatement.setString(1, idPelanggan);
                preparedStatement.setString(2, sNama);

                // Mengeksekusi kueri SELECT
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Menampilkan hasil kueri
                    while (resultSet.next()) {
                        String idServis = resultSet.getString("IdServis");
                        System.out.println("IdServis: " + idServis);
                        idS =  idServis;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idS;
        
                
    }
    public void deleteRow(String idServ){
         // Informasi koneksi database
         String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
        
         // Data yang ingin dihapus
         String idServis = idServ;
 
         try {
             // Membuat koneksi
             Connection connection = DriverManager.getConnection(jdbcUrl);
 
             try {
                 // Hapus data dari tabel ServisDetail
                 String deleteServisDetailQuery = "DELETE FROM ServisDetail WHERE IdServis = ?";
                 try (PreparedStatement preparedStatementServisDetail = connection.prepareStatement(deleteServisDetailQuery)) {
                     preparedStatementServisDetail.setString(1, idServis);
                     preparedStatementServisDetail.executeUpdate();
                 }
 
                 // Hapus data dari tabel Servis
                 String deleteServisQuery = "DELETE FROM Servis WHERE IdServis = ?";
                 try (PreparedStatement preparedStatementServis = connection.prepareStatement(deleteServisQuery)) {
                     preparedStatementServis.setString(1, idServis);
                     preparedStatementServis.executeUpdate();
                 }
 
                 // Commit transaksi jika berhasil
                 connection.commit();
                 System.out.println("Data berhasil dihapus.");
             } catch (SQLException e) {
                 // Rollback transaksi jika terjadi kesalahan
                 connection.rollback();
                 e.printStackTrace();
             } finally {
                 // Kembalikan otomatis commit ke kondisi awal
                 connection.setAutoCommit(true);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }
    public void getList(){
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
    

        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT TOP 100 Pelanggan.IdPelanggan, Pelanggan.Nama AS NamaPelanggan, Pelanggan.NoTelp, " +
            "Pelanggan.Tipe, Pelanggan.CC, Servis.IdServis, Servis.sNama AS NamaServis, " +
            "ServisDetail.Tanggal, ServisDetail.Price " +
            "FROM Pelanggan " +
            "INNER JOIN Servis ON Pelanggan.IdPelanggan = Servis.IdPelanggan " +
            "INNER JOIN ServisDetail ON Pelanggan.IdPelanggan = ServisDetail.IdPelanggan " +
            "ORDER BY Pelanggan.IdPelanggan ASC";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    String idPelanggan = resultSet.getString("IdPelanggan");
                    String namaPelanggan = resultSet.getString("NamaPelanggan");
                    String noTelp = resultSet.getString("NoTelp");
                    String tipe = resultSet.getString("Tipe");
                    String cc = resultSet.getString("CC");
                    String idServis = resultSet.getString("IdServis");
                    String namaServis = resultSet.getString("NamaServis");
                    String tanggal = resultSet.getString("Tanggal");
                    int price = resultSet.getInt("Price");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listin.add(new userIn(idPelanggan, namaPelanggan, namaServis, tanggal, Integer.toString(price)));
                    System.out.println("IdPelanggan: " + idPelanggan + ", NamaPelanggan: " + namaPelanggan +
                            ", NoTelp: " + noTelp + ", Tipe: " + tipe + ", CC: " + cc +
                            ", IdServis: " + idServis + ", NamaServis: " + namaServis +
                            ", Tanggal: " + tanggal + ", Price: " + price);
                    
                        }
                        
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            //Skeleton MainScene
    
    @FXML
    private Button Cari;




    // @FXML
    // private Label History;

    
    @FXML
    private Button logoutButt;
    
    @FXML
    private TextField fSearch;

    @FXML
    private TextField fNama;

    @FXML
    private TextField fTelp;

    @FXML
    private TextField fTipe;

    @FXML
    private Button updateButton;

     @FXML
    private Text tDate ;

    @FXML
    private TableView<userIn> datCol;

    @FXML
    private TableColumn<userIn, String> hargaCol;

    @FXML
    private TableColumn<userIn, String> idPelCol;

    @FXML
    private TableColumn<userIn, String> namaCol;

    @FXML
    private TableColumn<userIn, String> serviceCol;

    @FXML
    private TableColumn<userIn, String> tanggalCol;



    
    
    @FXML
    void LogOutClick(ActionEvent event) {
        try {
            switchToLogin(event);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void okButtonClicked(ActionEvent event) {

        try {
            System.out.println(fNama.getText()+" "+fTelp.getText()+" "+fTipe.getText());
        } 
        
        catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error broku ");
        }
        //Insert ID Setelah Tombol ok Di Clik
        String idPelanggan="";
        idPelanggan = idPelangganGen(idPelanggan);
        insertPelanggan (idPelanggan, fNama.getText(),fTelp.getText(), fTipe.getText());
        idPel="udahBisa";
        System.out.println(idPel);
        try {
            switchToCAtalogue(event);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        

    }
    @FXML
    void CariButtonCLick(ActionEvent event) {

        saerchAll(fSearch.getText());

    }
        @FXML
    void hapusButtonClicked(ActionEvent event) {
        int index = datCol.getSelectionModel().getSelectedIndex();
        if(index <=-1){
            return;
        }
        String IdToDelete = idPelCol.getCellData(index).toString();
        String namToDelete = serviceCol.getCellData(index).toString();
        String idServie = searId(IdToDelete, namToDelete);
        deleteRow(idServie);
        listin.remove(index);
        datCol.setItems(listin);
    }

    
    @FXML
    void setButtonClicked(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Apakah anda ingin mengganti data tersebut?");
        alert.setContentText("Anda akan menghapus dan mengganti semua data tersebut?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            int index = datCol.getSelectionModel().getSelectedIndex();
            if(index <=-1){
            return;
            }
            String IdToDelete = idPelCol.getCellData(index).toString();
            listin.clear();
            delIdUp(IdToDelete);
            insertPelanggan(IdToDelete, fNama.getText(), fTelp.getText(), fTipe.getText());
        }else{
            return;
        }
        try {
            switchToCAtalogue(event);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        getList();


    }

    @FXML
    void updateButtonClicked(ActionEvent event) {
        
        namaCol.setCellValueFactory(new PropertyValueFactory<userIn, String>("Nama"));
        idPelCol.setCellValueFactory(new PropertyValueFactory<userIn, String>("idPelanggan"));
        serviceCol.setCellValueFactory(new PropertyValueFactory<userIn, String>("Servis"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<userIn, String>("Tanggal"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<userIn, String>("Harga"));

        datCol.setItems(listin);
        listTemp.clear();
    }

    //Skeleton Catalogue
    
    ArrayList<ArrayList<String>> dataHargaList = new ArrayList<>();
    ArrayList<String> servList = new ArrayList<>();
    ArrayList<String> ccList = new ArrayList<>();
    ArrayList<String> hargaList = new ArrayList<>();
    
    int ServId = 0;
    int CCId = 0;

    
   
    public void dataListCreate(){
     //Service Id
    //0 = Cuci
    //1 = Wax
    //2 = BlackAgain
    //3 = ChainLube
    //CC Id
    //0=0-150
    //1=150-200
    //2=200-600
    //Harga Cuci Id
    //0 = '35k'
    //1 = '45k'
    //2 = '50k'
    
    servList.add("Cuci");
    servList.add("Wax");
    servList.add("BlackAgain");
    servList.add("Chain Lube");
    
    ccList.add("0-200cc");
    ccList.add("200-600cc");
    ccList.add("600cc++");
    ccList.add("Uknown");

// Deklarasi ArrayList dan pengisian elemennya dalam satu baris
    ArrayList<String> daftarNama = new ArrayList<>(java.util.Arrays.asList("Nama1", "Nama2", "Nama3"));

    
    dataHargaList.add(new ArrayList<>(java.util.Arrays.asList("35000", "45000", "50000"))); // Baris Cuci
    dataHargaList.add(new ArrayList<>(java.util.Arrays.asList("1000", "15000", "20000"))); // Baris Wax
    dataHargaList.add(new ArrayList<>(java.util.Arrays.asList("5000", "10000", "15000"))); // Baris Black Again
    dataHargaList.add(new ArrayList<>(java.util.Arrays.asList("5000", "10000", "15000"))); // Baris Chain Lube
    
}
String [] CCArr = new String[3];
public String idServisGen(String out, int idServis,int idCC ){
        //Untuk Membuat IdServis

    //Service Id
    //0 = Cuci
    //1 = Wax
    //2 = BlackAgain
    //3 = ChainLube
    //Service Id
    //0 = 0-200
    //1 = 200-600
    //2 = 600++
    String [] servis = {"WA","WX","BA","CA"};
    CCArr = new String[]{"100", "200", "600"};
    
        int sumPelanggan=0;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
        try (Connection connection = DriverManager.getConnection(url);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS JumlahPelanggan FROM Pelanggan");
        ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
            //Mencari Jumlah Pelanggan yang ada di Database
            sumPelanggan = resultSet.getInt("JumlahPelanggan");
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        out =servis[idServis]+""+CCArr[idCC]+""+Integer.toString(sumPelanggan);

        //Return IdPelanggan
        return out;
    }

    
   
    @FXML
    private ToggleGroup CC;

    @FXML
    private CheckBox CheckCA;

    @FXML
    private Button cancelCatalogue;

    @FXML
    private CheckBox checkBA;

    @FXML
    private CheckBox checkCUCI;

    @FXML
    private CheckBox checkWX;

    @FXML
    private Button confirmCatalogue;

    @FXML
    private RadioButton radio0;

    @FXML
    private RadioButton radio200;

    @FXML
    private RadioButton radio600;

    @FXML
    private TextField idPelCatalog;
    
    @FXML
    private TextField fTanggal;



    
    @FXML
    public void getccMotor(ActionEvent event){


        if(radio0.isSelected()){      
            CCId=0;           
              
        }
        
        else if(radio200.isSelected()){
            CCId=1;            
      
        }
        
        else if(radio600.isSelected()){
            CCId=2; 
    
        }else{
            CCId=3;
        }
      
    }
    //List Array boolean Servis
    //0 = CUCI
    //1 = WAX
    //2 = Black Again
    //3 = Chain Lube

    boolean [] servArr = new boolean[4];
    boolean boolCA = false;
    boolean boolCuci = false;
    boolean boolBA = false;
    boolean boolWX = false;
    
    //Checkboxes 
    //Black Again check
 

    @FXML
    void BAcheck(ActionEvent event) {
        if(checkBA.isSelected()){
            servArr[2]=true; 
            
        }else{
            servArr[2]=false;
        }
    }
    //Chain lube check
    @FXML
    void CAcheck(ActionEvent event) { 
        if(CheckCA.isSelected()){
         servArr[3]=true;
        }else{
            servArr[3]=false;
        }
    }
     //Cuci check
    @FXML
    void CUCIcheck(ActionEvent event) {
        if(checkCUCI.isSelected()){
            servArr[0]=true;
        }else{
            servArr[0]=false;
        }
    }
     //Wax check
    @FXML
    void WXcheck(ActionEvent event) {
        if(checkWX.isSelected()){
            servArr[1]=true;
        }else{
            servArr[1]=false;
        }
    }

    ////////////////////////////////////////////////////////////////////

    @FXML
    void CancelCliked(ActionEvent event) {
        cancelDelete();
        try {
            switchToMainScene(event);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }

    
    @FXML
    void rad0cc(ActionEvent event) {
        
    }
    
    @FXML
    void rad200cc(ActionEvent event) {
        
    }
    
    @FXML
    void rad600cc(ActionEvent event) {
    
        
    }

    
    @FXML
    void tanggalField(ActionEvent e) {
    
    }

    public void insertService (String idServis, String namaSer, int Harga, String idPelanggan, String CC ,String tanggal){
        //Untuk Menambah Tabel Pelanggan
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
        try (Connection connection = DriverManager.getConnection(url)) {
            // Query SQL untuk menyisipkan data pelanggan
            String sqlServis = "INSERT INTO Servis (IdPelanggan, IdServis, sNama) VALUES (?, ?, ?)";
            
            String insertServisDet = "INSERT INTO ServisDetail (IdPelanggan, IdServis, Price, Tanggal) VALUES (?, ?, ?, ?)";
            // Membuat objek PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlServis)) {
                // Menetapkan nilai parameter untuk pelanggan pertama
                preparedStatement.setString(1, idPelanggan);
                preparedStatement.setString(2, idServis);
                preparedStatement.setString(3, namaSer);
                preparedStatement.executeUpdate();
                System.out.println(idPel);
                
                System.out.println("Data Servis berhasil disisipkan.");
            }



              try (PreparedStatement preparedStatement = connection.prepareStatement(insertServisDet)) {
                // Menetapkan nilai parameter untuk pelanggan pertama
                preparedStatement.setString(1, idPelanggan);
                preparedStatement.setString(2, idServis);
                preparedStatement.setInt(3, Harga);
                preparedStatement.setString(4, tanggal);


                preparedStatement.executeUpdate();
                System.out.println("Data Deatail Servis berhasil disisipkan.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
         
        
        

    }
    public void insertCC(String cc, String idPelanggan){
       
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
        try (Connection connection = DriverManager.getConnection(url)) {
            // Query SQL untuk menyisipkan data pelanggan
            System.out.println("ini cc " + cc);
            String insertPelangganCont = "UPDATE Pelanggan SET CC = ? WHERE IdPelanggan = ?";
            
            // Membuat objek PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertPelangganCont)) {
                // Menetapkan nilai parameter untuk pelanggan pertama
                preparedStatement.setString(1, cc);
                preparedStatement.setString(2, idPelanggan);
                
                preparedStatement.executeUpdate();

                System.out.println("Data CC Servis berhasil disisipkan.");
            }
        
        
        }
        catch (SQLException e) {
            e.printStackTrace();
        }  

        
    }


    @FXML
    void conCatalogue(ActionEvent event) {


        if((radio0.isSelected()||radio200.isSelected()||radio600.isSelected())&&(checkBA.isSelected()||checkCUCI.isSelected()||checkWX.isSelected()||CheckCA.isSelected())){
            CCArr = new String[]{"100", "200", "600"};
            String Tanggal = seDate();
            dataListCreate();
            ArrayList <ArrayList<String>> inData = new ArrayList<>();
            String idTemp2 = "";
            if(idPelCatalog.getText().equals("")){
                idTemp2 = Integer.toString(Integer.parseInt(idPelangganGen(idTemp2))-1);
            }else{
                idTemp2 =idPelCatalog.getText(); 
            }
            System.out.println("CC FIX: "+ccList.get(CCId));
            insertCC(CCArr[CCId], idTemp2);
            for (int i = 0; i < servArr.length; i++) {
                String idServis="";
                System.out.println(servList.get(i)+" : "+servArr[i] );
                if(servArr[i]==true){
                    idServis = idServisGen(idServis, i, CCId);
                    System.out.println("Id "+servList.get(i)+" : "+idServis);
                    System.out.println("dataHargaList Cuci : " + dataHargaList.get(i).get(CCId));
                    System.out.println();
                    System.out.println("print 1 "+idTemp2);
                    insertService (idServis, servList.get(i), Integer.parseInt((dataHargaList.get(i).get(CCId))),idTemp2,CCArr[CCId],Tanggal);
                }
            }
            try {
                switchToMainScene(event);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Data Belum diisi!!");
            alert.setContentText("Tolong Pilih Size dan Servis!");
            alert.showAndWait();
        }
        
    }
    public String seDate(){
        
        String out="";
        //Mencari Tanggal Hari dan Bulan sQL
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        LocalDate localDateFromSqlDate = sqlDate.toLocalDate();
        return out=((localDateFromSqlDate.getMonth().getValue())+"-"+localDateFromSqlDate.getDayOfMonth()+"-"+localDateFromSqlDate.getYear());

    }

    public void  cancelDelete(){
              //Untuk Menambah Tabel Pelanggan
        String idTemp2="";
        idTemp2 = Integer.toString(Integer.parseInt(idPelangganGen(idTemp2))-1);
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";
        try (Connection connection = DriverManager.getConnection(url)) {
            // Query SQL untuk menyisipkan data pelanggan
            String sql = "DELETE FROM Pelanggan WHERE IdPelanggan = ? ;";

            // Membuat objek PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Menetapkan nilai parameter untuk pelanggan pertama
                preparedStatement.setString(1, idTemp2);

                preparedStatement.executeUpdate();
  
                System.out.println("Data pelanggan berhasil dihapus.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    //Skeleton Login

       @FXML
    private Button LoginButton;

    @FXML
    private PasswordField fPassword;

    @FXML
    private TextField fUsername;

    @FXML
    void LoginClicked(ActionEvent event) throws SQLException, IOException {
        
        String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=FlashLight;IntegratedSecurity=true;encrypt=false;";

            
        try {
            // Establishing the database connection
            Connection connection = DriverManager.getConnection(JDBC_URL);

            // Getting user input for login
            Scanner scanner = new Scanner(System.in);
            String USERNAME = fUsername.getText();
            String PASSWORD = fPassword.getText();
            
            // Querying the database for user credentials
            String query = "SELECT * FROM UserIn WHERE username = ? AND passKey = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, USERNAME);
                preparedStatement.setString(2, PASSWORD);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Checking if the user credentials are valid
                    if (resultSet.next()) {
                        System.out.println("Login successful!");
                        switchToMainScene(event);
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                }
            }

            // Closing resources
            connection.close();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

     
}


