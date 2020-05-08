package nyatProje;

public class SicaklikCelcius implements ISicaklikBilgisi {
    @Override
    public String SicaklikGonder(SicaklikAlgilayici sicaklik) {
        sicaklik.sicaklikOku();
        return sicaklik.sicaklikGonder() + " C";
    }
    @Override
    public String SicaklikDonustur(String sicaklik) {
        if(sicaklik.contains("C"))
            return sicaklik;
        else
            return String.valueOf((Double.parseDouble(sicaklik.substring(0,4)) - 32) / 1.8).substring(0,4) + "C";
    }
}
