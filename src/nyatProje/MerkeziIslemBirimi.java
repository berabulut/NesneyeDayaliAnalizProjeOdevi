package nyatProje;
import java.util.Scanner;

public class MerkeziIslemBirimi {
    private Eyleyici eyleyici = new Eyleyici();
    private SicaklikAlgilayici sicaklik = new SicaklikAlgilayici();
    private AgArayuzu arayuz = new AgArayuzu();
    private static MerkeziIslemBirimi single_instance = null;

    private MerkeziIslemBirimi() {
        arayuz.anaMenu(eyleyici, sicaklik);
    }
    public static MerkeziIslemBirimi getInstance()
    {
        if (single_instance == null)
            single_instance = new MerkeziIslemBirimi();

        return single_instance;
    }
}
