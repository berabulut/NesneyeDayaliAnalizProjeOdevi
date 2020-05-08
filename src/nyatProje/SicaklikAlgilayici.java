package nyatProje;
import java.util.Random;

public class SicaklikAlgilayici {
    private double derece;

    public void sicaklikOku() {
        Random rand = new Random();
        int max = 35;
        int min = 0;
         derece = min + (max - min) * rand.nextDouble();
    }

    public double sicaklikGonder() {
        return  Double.parseDouble(String.valueOf(derece).substring(0,5));
    }

}
