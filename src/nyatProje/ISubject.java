package nyatProje;

public interface ISubject {
    public void attach(IObserver kullanici, String kullaniciAdi);
    public void detach(String kullaniciAdi);
    public void notify(String mesaj);
    public void notifyKullanici(String mesaj, String kullaniciAdi);
}
