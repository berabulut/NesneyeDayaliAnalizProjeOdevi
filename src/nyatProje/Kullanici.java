package nyatProje;

public class Kullanici implements  IObserver{
    private String kullaniciAdi;
    private String kullaniciNo;
    private String sogutucuDurumu;
    private String sicaklik;
    public ISicaklikBilgisi sicaklikBirim = new SicaklikCelcius();
    private static Kullanici single_instance = null;


    private Kullanici(String kullaniciAdi, String kullaniciNo, String sogutucuDurumu) {
        setKullaniciAdi(kullaniciAdi);
        setKullaniciNo(kullaniciNo);
        setSogutucuDurumu(sogutucuDurumu);
    }

    @Override
    public void update(String mesaj) {
        if(!(mesaj.length() > 0))
            return;
        else
            System.out.println("Sevgili " + kullaniciAdi + " " + mesaj);
    }
    public static Kullanici getInstance(String kullaniciAdi, String kullaniciNo, String sogutucuDurumu)
    {
        if (single_instance == null)
            single_instance = new Kullanici(kullaniciAdi, kullaniciNo, sogutucuDurumu);

        return single_instance;
    }
    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getKullaniciNo() {
        return kullaniciNo;
    }

    public void setKullaniciNo(String kullaniciNo) {
        this.kullaniciNo = kullaniciNo;
    }

    public String getSogutucuDurumu() {
        return sogutucuDurumu;
    }

    public void setSogutucuDurumu(String sogutucuDurumu) {
        this.sogutucuDurumu = sogutucuDurumu;
    }

    public String getSicaklik() { return sicaklik; }

    public void setSicaklik(String sicaklik) {
        this.sicaklik = sicaklik;
    }
}
