package nyatProje;

public interface IAgArayuzu {
    public boolean girisYap(String kullaniciAdi, String sifre);
    public void oturumuSonlandir();
    public boolean kullaniciDogrula(String kullaniciAdi, String sifre);
}
