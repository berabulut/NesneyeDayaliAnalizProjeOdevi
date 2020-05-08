package nyatProje;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class VeritabaniPostgreSQL  implements ISubject,  IVeritabani {
    private Connection conn;
    private static VeritabaniPostgreSQL single_instance = null;
    private Hashtable<String, IObserver> kullanicilar = new Hashtable<>();
    private VeritabaniPostgreSQL() {
        baglan();
    }
    public static VeritabaniPostgreSQL getInstance()
    {
        if (single_instance == null)
            single_instance = new VeritabaniPostgreSQL();

        return single_instance;
    }

    public void baglan() {
        try{
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/NesneOdev",
                    "postgres", "bera2000");

            if (conn != null)
                System.out.println("Ağa bağlandı!");
            else
                System.out.println("Bağlantı girişimi başarısız!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void baglantiSonlandir() {
        try {
            conn.close();
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConn() {
        return conn;
    }

    public int kullaniciSayisi() {
        String SQL = "SELECT COUNT(*) FROM \"Kullanicilar\"";
        int kullaniciSayisi = 0;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            kullaniciSayisi = Integer.parseInt(rs.getString("count"));
            rs.close();
            stmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return kullaniciSayisi;
    }
    public void kullaniciOlustur(String kullaniciAdi, String sifre, int kullaniciNo, boolean SogutucuDurumu) {
        String SQL = "INSERT INTO \"public\".\"Kullanicilar\" ( \"kullaniciAdi\", \"sifre\", \"kullaniciNo\", \"SogutucuDurumu\") VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, kullaniciAdi);
            pstmt.setString(2, sifre);
            pstmt.setInt(3, kullaniciNo);
            pstmt.setString(4, String.valueOf(SogutucuDurumu));
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println(kullaniciAdi + " adli kullanici olusturuldu!");
            IObserver kullanici = Kullanici.getInstance(kullaniciAdi, String.valueOf(kullaniciNo), String.valueOf(SogutucuDurumu));
            attach(kullanici, kullaniciAdi);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void kullaniciSil(String kullaniciAdi, String sifre) {
        String SQL = "DELETE FROM \"public\".\"Kullanicilar\" WHERE \"kullaniciAdi\" = ? AND \"sifre\" = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1,kullaniciAdi);
            pstmt.setString(2,sifre);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void kullaniciBilgileriniDon(String kullaniciAdi, String no, String sogutucuDurumu) {
        String SQL = "SELECT \"kullaniciAdi\", \"sifre\", \"kullaniciNo\", \"SogutucuDurumu\" FROM \"public\".\"Kullanicilar\" WHERE \"kullaniciAdi\" = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                no = rs.getString("kullaniciNo");
                sogutucuDurumu = rs.getString("SogutucuDurumu");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String menuUyarı(String uyari) {
        String SQL = "SELECT \"uyari\" FROM \"public\".\"Uyarilar\"";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                uyari ="UYARI : " +  rs.getString("uyari");
            }
            rs.close();
            stmt.close();
            return uyari;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return uyari;
    }


    @Override
    public void attach(IObserver kullanici, String kullaniciAdi) {
        kullanicilar.put(kullaniciAdi, kullanici);
    }
    @Override
    public void detach(String kullaniciAdi) {
        kullanicilar.remove(kullaniciAdi);
    }
    @Override
    public void notify(String mesaj) {
        kullanicilar.forEach((key, value) -> value.update(mesaj));
    }
    @Override
    public void notifyKullanici(String mesaj, String kullaniciAdi) {
        kullanicilar.forEach((key, value) -> {
            if(key.equals(kullaniciAdi)) {
                value.update(mesaj);
            }
        });
    }

}
