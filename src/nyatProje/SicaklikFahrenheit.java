package nyatProje;

public class SicaklikFahrenheit implements ISicaklikBilgisi {
    @Override
    public String SicaklikGonder(SicaklikAlgilayici sicaklik) {
        sicaklik.sicaklikOku();
        return (sicaklik.sicaklikGonder() * 1.8 + 32) + " F";
    }
    @Override
    public String SicaklikDonustur(String sicaklik) {
        if(sicaklik.contains("F"))
            return sicaklik;
        else
            return (String.valueOf(Double.parseDouble(sicaklik.substring(0,5)) * 1.8 + 32)).substring(0,4) + "F";
    }
}
