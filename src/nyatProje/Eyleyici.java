package nyatProje;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Eyleyici {
    private boolean sogutucuDurumu;


    public void sogutucuAc(VeritabaniPostgreSQL postgre, String kullaniciAdi) {
        sogutucuDurumu = true;
        String SQL = "UPDATE \"public\".\"Kullanicilar\" SET \"SogutucuDurumu\" = ? WHERE \"kullaniciAdi\" = ?";
        try {
            PreparedStatement pstmt = postgre.getConn().prepareStatement(SQL);
            pstmt.setString(1, String.valueOf(sogutucuDurumu));
            pstmt.setString(2, kullaniciAdi );
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void sogutucuKapa(VeritabaniPostgreSQL postgre, String kullaniciAdi) {
        sogutucuDurumu = false;
        String SQL = "UPDATE \"public\".\"Kullanicilar\" SET \"SogutucuDurumu\" = ? WHERE \"kullaniciAdi\" = ?";
        try {
            PreparedStatement pstmt = postgre.getConn().prepareStatement(SQL);
            pstmt.setString(1, String.valueOf(sogutucuDurumu));
            pstmt.setString(2, kullaniciAdi);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean sogutucuDurumuDon(VeritabaniPostgreSQL postgre, String kullaniciAdi) {
        String SQL = "SELECT \"SogutucuDurumu\" FROM \"public\".\"Kullanicilar\" WHERE \"kullaniciAdi\" = ?";
        try {
            PreparedStatement pstmt = postgre.getConn().prepareStatement(SQL);
            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                sogutucuDurumu = Boolean.parseBoolean(rs.getString("SogutucuDurumu"));
            }
            pstmt.close();
            rs.close();
            return sogutucuDurumu;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sogutucuDurumu;
    }

}
