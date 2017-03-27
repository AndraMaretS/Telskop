package id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.model;

/**
 * Created by Andra Maret on 26/03/2017.
 */
public class Kota {
    private String idKota, kotaNama;

    public Kota() {
    }

    public Kota(String idKota, String kotaNama) {
        this.idKota = idKota;
        this.kotaNama = kotaNama;
    }

    public String getIdKota() {
        return idKota;
    }

    public void setIdKota(String idKota) {
        this.idKota = idKota;
    }

    public String getKotaNama() {
        return kotaNama;
    }

    public void setKotaNama(String kotaNama) {
        this.kotaNama = kotaNama;
    }
}
