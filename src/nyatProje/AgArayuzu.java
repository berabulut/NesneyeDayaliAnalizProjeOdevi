package nyatProje;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Strings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AgArayuzu implements IAgArayuzu {
    VeritabaniPostgreSQL postgre = VeritabaniPostgreSQL.getInstance();
    public void anaMenu(Eyleyici eyleyici, SicaklikAlgilayici sicaklik) {
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.println("1 - GIRIS YAP!");
            System.out.println("2 - KULLANICI OLUSTUR!");
            System.out.println("3 - PROGRAMDAN CIK");
            int secenek = scan.nextInt();

            if(secenek == 1) {
                Scanner in = new Scanner(System.in);
                System.out.println("Kullanici adinizi giriniz : ");
                String kullaniciAdi = in.nextLine();
                System.out.println("Sifrenizi giriniz : ");
                String sifre = in.nextLine();
                if(girisYap(kullaniciAdi, sifre)) {
                    String no = "";
                    String sDurumu = "";
                    postgre.kullaniciBilgileriniDon(kullaniciAdi, no, sDurumu);
                    Kullanici kullanici = Kullanici.getInstance(kullaniciAdi, no, sDurumu);
                    IObserver IKullanici = Kullanici.getInstance(kullaniciAdi, no, sDurumu);
                    postgre.attach(IKullanici, kullaniciAdi);
                    kullaniciMenusu(eyleyici, sicaklik, postgre, kullaniciAdi, kullanici);

                }
            }
            else if(secenek == 2) {
                kullaniciOlusturmaMenusu();
            }
            else if (secenek == 3) {
                System.out.println("PROGRAM SONLANDIRILIYOR!");
                System.exit(0);
            }
        }
    }


    public void kullaniciOlusturmaMenusu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Kullanici adi giriniz!");
        String kullaniciAdi = scan.nextLine();
        String sifre;
        String SQL = "SELECT COUNT(\"kullaniciAdi\") FROM \"Kullanicilar\" WHERE \"kullaniciAdi\" = ?";
        try {
            PreparedStatement pstmt = postgre.getConn().prepareStatement(SQL);
            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                if(Integer.parseInt(rs.getString("count")) > 0)
                    System.out.println("Kullanici adi alinmis, farkli kullanici adi girin");
                else {
                    System.out.println("Sifre girin");
                    sifre = scan.nextLine();
                    int kullaniciNo = postgre.kullaniciSayisi() + 1;
                    postgre.kullaniciOlustur(kullaniciAdi, sifre, kullaniciNo, false);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void kullaniciMenusu(Eyleyici eyleyici, SicaklikAlgilayici sicaklik, VeritabaniPostgreSQL postgre, String kullaniciAdi, Kullanici kullanici) {
        Scanner scan = new Scanner(System.in);
        String derece = kullanici.sicaklikBirim.SicaklikGonder(sicaklik);
        while(true) {
            String uyari = "";
            uyari = postgre.menuUyarı(uyari);
            System.out.print("\nKullanici : " + kullaniciAdi);
            System.out.println("        Sicaklik : " + derece);
            postgre.notify(uyari);


            System.out.println("\n1 - Sicaklik Goster");
            System.out.println("2 - Sogutucuyu Ac");
            System.out.println("3 - Sogutucuyu Kapa");
            System.out.println("4 - Sicaklik birimini degistir");
            System.out.println("5 - Cikis Yap");
            int secenek = scan.nextInt();

            if(secenek == 1) {
                derece = kullanici.sicaklikBirim.SicaklikGonder(sicaklik);
                System.out.println("-----------------Hava " + derece + " derece!-----------------");
            }

            else if(secenek == 2) {
                if(eyleyici.sogutucuDurumuDon(postgre, kullaniciAdi))
                    System.out.println("-----------------Sogutucu zaten acik!-----------------");
                else {
                    eyleyici.sogutucuAc(postgre, kullaniciAdi);
                    System.out.print("-----------------Sogutucu acildi!-----------------");
                }
            }
            else if(secenek == 3) {
                if(eyleyici.sogutucuDurumuDon(postgre, kullaniciAdi) == false)
                    System.out.println("-----------------Sogutucu zaten kapali!-----------------");
                else {
                    eyleyici.sogutucuKapa(postgre, kullaniciAdi);
                    System.out.println("-----------------Sogutucu kapatildi!-----------------");
                }
            }
            else if(secenek == 4) {
                System.out.println("1 - Celcius");
                System.out.println("2 - Fahrenheit");
                secenek = scan.nextInt();

                if(secenek == 1) {
                    kullanici.sicaklikBirim = new SicaklikCelcius();
                    derece = kullanici.sicaklikBirim.SicaklikDonustur(derece);
                    postgre.notifyKullanici(" sicaklik biriminiz Celcius'a cevrildi", kullaniciAdi);
                }
                else if(secenek == 2) {
                    kullanici.sicaklikBirim = new SicaklikFahrenheit();
                    derece = kullanici.sicaklikBirim.SicaklikDonustur(derece);
                    postgre.notifyKullanici(" sicaklik biriminiz Fahrenheit'e cevrildi", kullaniciAdi);
                }

            }
            else if(secenek == 5) {
                System.out.println("-----------------CIKIS YAPILIYOR!-----------------");
                postgre.detach(kullaniciAdi);
                anaMenu(eyleyici, sicaklik);
                break;
            }
            else {
                System.out.println("Gecerli karakter giriniz!");
            }
        }
    }



    public boolean girisYap(String kullaniciAdi, String sifre) {
        if(kullaniciDogrula(kullaniciAdi, sifre)) {
            System.out.println(kullaniciAdi + " giris yapti!");
            return true;
        }
        else {
            System.out.println("Sistem " + kullaniciAdi + " adli kullaniciyi dogrulayamadi!");
            return false;
        }
    };

    public boolean kullaniciDogrula(String kullaniciadi, String Sifre) {
        String SQL = "SELECT \"sifre\" FROM \"public\".\"Kullanicilar\" WHERE \"kullaniciAdi\" = ?";
        try {
            PreparedStatement pstmt = postgre.getConn().prepareStatement(SQL);
            pstmt.setString(1,kullaniciadi);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                if(Sifre.equals(rs.getString("sifre"))) {
                    rs.close();
                    pstmt.close();
                    return true;
                }
                else {
                    rs.close();
                    pstmt.close();
                    return false;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void oturumuSonlandir() {
        postgre.baglantiSonlandir();
    };
}
