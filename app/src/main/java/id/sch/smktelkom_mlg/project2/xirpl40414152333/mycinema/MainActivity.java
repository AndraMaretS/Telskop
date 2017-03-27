package id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.adapter.KotaAdapter;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.helpers.Constant;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.helpers.RbHelper;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.model.Kota;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    public static ArrayList<Kota> data;
    private RecyclerView rvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();

        getData();

    }

    private void setupView() {
        rvData = (RecyclerView) findViewById(R.id.rvData);
        rvData.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
    }

    private void getData() {
        data.clear();
        rvData.setVisibility(View.VISIBLE);

        String url = "http://ibacor.com/api/jadwal-bioskop?k=" + Constant.KEY;

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
                                JSONArray jArray = json.getJSONArray("data");

                                if (jArray.length() > 0) {

                                    for (int i = 0; i < jArray.length(); i++) {
                                        JSONObject jObj = jArray.getJSONObject(i);

                                        String id = jObj.getString("id");
                                        String nama = jObj.getString("kota");

                                        Kota x = new Kota(id, nama);
                                        data.add(x);
                                    }


                                } else {
                                    RbHelper.pesan(c, "Data tidak ada");

                                }


                            } else {
                                RbHelper.pesan(c, "Data Tidak Ada");
                            }

                            KotaAdapter adapter = new KotaAdapter(c, data);
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

    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ic_about:
                Intent intent = new Intent(c, AboutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
