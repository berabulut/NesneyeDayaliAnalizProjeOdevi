package nyatProje;

import java.sql.Connection;

public interface IVeritabani {
    public void baglan();
    public void kullaniciOlustur(String kullaniciAdi, String sifre, int kullaniciNo, boolean SogutucuDurumu);
    public void kullaniciSil(String kullaniciAdi, String sifre);
    public int  kullaniciSayisi();
    public void baglantiSonlandir();
}
