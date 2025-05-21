package arac_kiralama.service;
import java.util.List;
import arac_kiralama.doa.AracDAO;
import arac_kiralama.models.Arac;

public class AracService {
    private AracDAO aracDAO = new AracDAO();

    public List<Arac> araclariListele() {
        return aracDAO.araclariGetir();
    }
}