package id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.model;

import java.util.ArrayList;

/**
 * Created by Andra Maret on 26/03/2017.
 */
public class Jadwal {
    private String namaBioskop, harga;

    private ArrayList<String> dataJam;

    public String getNamaBioskop() {
        return namaBioskop;
    }

    public void setNamaBioskop(String namaBioskop) {
        this.namaBioskop = namaBioskop;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public ArrayList<String> getDataJam() {
        return dataJam;
    }

    public void setDataJam(ArrayList<String> dataJam) {
        this.dataJam = dataJam;
    }
}
