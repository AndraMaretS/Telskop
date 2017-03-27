package id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.helpers;

/**
 * Created by Andra Maret on 26/03/2017.
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;


@SuppressLint("SimpleDateFormat")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RbHelper {
    public static final String APP = "xrb21-JadwalBioskop";
    private static final int DEBUG = 1;

    public static boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // peringatan jika internet tidak konek
    public static void alertMessageNoInternet(Context c) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(
                "Anda tidak terkoneksi dengan internet, Silahkan Aktifkan Internet Anda terlebih dahulu.")
                .setCancelable(false)
                .setTitle("Informasi Internet")
                .setNegativeButton("Tutup",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog,
                                                @SuppressWarnings("unused") final int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public static void pesan(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }


    public static void pre(String pesan) {
        try {
            if (DEBUG == 1) {
                System.out.println(pesan);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public static String tglToInd(String tgl) {
        String x[] = tgl.split("/");
        return x[0] + " " + getBulan(x[1]) + " " + "20" + x[2];
    }

    public static String getBulan(String i) {
        String hasil = "";
        if (i.equalsIgnoreCase("01")) {
            hasil = "Januari";
        } else if (i.equalsIgnoreCase("02")) {
            hasil = "Februari";
        } else if (i.equalsIgnoreCase("03")) {
            hasil = "Maret";
        } else if (i.equalsIgnoreCase("04")) {
            hasil = "April";
        } else if (i.equalsIgnoreCase("05")) {
            hasil = "Mei";
        } else if (i.equalsIgnoreCase("06")) {
            hasil = "Juni";
        } else if (i.equalsIgnoreCase("07")) {
            hasil = "Juli";
        } else if (i.equalsIgnoreCase("08")) {
            hasil = "Agustus";
        } else if (i.equalsIgnoreCase("09")) {
            hasil = "September";
        } else if (i.equalsIgnoreCase("10")) {
            hasil = "Oktober";
        } else if (i.equalsIgnoreCase("11")) {
            hasil = "November";
        } else if (i.equalsIgnoreCase("12")) {
            hasil = "Desember";
        }

        return hasil;
    }


}
