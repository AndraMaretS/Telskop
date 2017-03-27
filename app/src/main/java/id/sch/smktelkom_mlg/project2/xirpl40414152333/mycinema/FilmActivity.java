package id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.adapter.FilmAdapter;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.helpers.Constant;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.helpers.RbHelper;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.model.Film;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.model.Jadwal;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class FilmActivity extends BaseActivity {

    public static ArrayList<Film> data;
    private RecyclerView rvData;
    private String idKota, kotaNama;
    private TextView tvTanggal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //ambil data yang dikirimkan dari halaman sebelumnya
        idKota = getIntent().getStringExtra(Constant.ID_KOTA);
        kotaNama = getIntent().getStringExtra(Constant.NAMA_KOTA);
        //set judul toolbar
        getSupportActionBar().setTitle("Kota " + kotaNama);

        setupView();
        getData();

    }

    private void setupView() {
        rvData = (RecyclerView) findViewById(R.id.rvData);
        rvData.setLayoutManager(new LinearLayoutManager(this));
        tvTanggal = (TextView) findViewById(R.id.tvTanggal);

        data = new ArrayList<>();
    }

    private void getData() {
        data.clear();
        rvData.setVisibility(View.VISIBLE);

        String url = "http://ibacor.com/api/jadwal-bioskop?k=" + Constant.KEY
                + "&id=" + idKota;

        Request request = new Request.Builder()
                .url(url)
                .build();

        RbHelper.pre("url : " + url);


        showLoading();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            hideLoading();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            hideLoading();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                // Read data on the worker thread
                final String responseData = response.body().string();
                RbHelper.pre("response : " + responseData);

                // Run view-related code back on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            JSONObject json = new JSONObject(responseData);

                            String hasil = json.getString("status");


                            if (hasil.equalsIgnoreCase("success")) {
                                String tanggal = json.getString("date");
                                tvTanggal.setText("Tayang pada "
                                        + RbHelper.tglToInd(tanggal));

                                JSONArray jArray = json.getJSONArray("data");

                                if (jArray.length() > 0) {

                                    for (int i = 0; i < jArray.length(); i++) {
                                        JSONObject jObj = jArray.getJSONObject(i);

                                        String judul = jObj.getString("movie");
                                        String poster = jObj.getString("poster");
                                        String genre = jObj.getString("genre");
                                        String durasi = jObj.getString("duration");

                                        ArrayList<Jadwal> dataJadwal = new ArrayList<Jadwal>();
                                        //cek apakah ada jadwalnya ngga
                                        if (jObj.has("jadwal")) {
                                            JSONArray jJadwal = jObj.getJSONArray("jadwal");

                                            for (int j = 0; j < jJadwal.length(); j++) {
                                                JSONObject objJadwal = jJadwal.getJSONObject(j);

                                                Jadwal jadwal = new Jadwal();
                                                jadwal.setNamaBioskop(objJadwal.getString("bioskop"));
                                                jadwal.setHarga(objJadwal.getString("harga"));

                                                ArrayList<String> dataJam = new ArrayList<String>();

                                                //cek apakah ada jam tayangnya tidak
                                                if (objJadwal.has("jam")) {
                                                    JSONArray jJam = objJadwal.getJSONArray("jam");
                                                    for (int k = 0; k < jJam.length(); k++) {

                                                        String jam = jJam.getString(k);
                                                        //masukkan ke array jam
                                                        dataJam.add(jam);
                                                    }
                                                }

                                                //masukkan data jam ke jadwal
                                                jadwal.setDataJam(dataJam);

                                                //masukkan data jadwal ke array jadwal
                                                dataJadwal.add(jadwal);

                                            }
                                        }

                                        Film x = new Film();

                                        x.setFilmNama(judul);
                                        x.setFilmGenre(genre);
                                        x.setFilmPoster(poster);
                                        x.setFilmDurasi(durasi);
                                        x.setDataJadwal(dataJadwal);

                                        data.add(x);
                                    }


                                } else {
                                    RbHelper.pesan(c, "Data tidak ada");

                                }


                            } else {
                                RbHelper.pesan(c, "Data Tidak Ada");
                            }

                            FilmAdapter adapter = new FilmAdapter(c, data);
                            rvData.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            RbHelper.pesan(c,
                                    "Error parsing data, please try again.");
                        } catch (Exception e) {
                            e.printStackTrace();
                            RbHelper.pesan(c,
                                    "error get data.");
                        }
                    }
                });
            }

        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
