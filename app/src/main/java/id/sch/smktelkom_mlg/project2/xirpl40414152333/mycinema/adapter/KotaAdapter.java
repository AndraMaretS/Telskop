package id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.FilmActivity;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.R;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.helpers.Constant;
import id.sch.smktelkom_mlg.project2.xirpl40414152333.mycinema.model.Kota;

/**
 * Created by Andra Maret on 26/03/2017.
 */
public class KotaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context c;
    private ArrayList<Kota> data;
    private AlphaAnimation btnAnimasi = new AlphaAnimation(1.0F, 0.5F);


    public KotaAdapter(Context c, ArrayList<Kota> data) {
        this.c = c;
        this.data = data;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.item_kota, parent, false);
        return new UserViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Kota x = data.get(position);
        UserViewHolder mHolder = (UserViewHolder) holder;

        String nama = x.getKotaNama();
        String names[] = nama.split(" ");
        String inisial = "A";
        try {
            if (names.length > 1) {
                inisial = names[0].substring(0, 1) + names[1].substring(0, 1);
            } else {
                inisial = names[0].substring(0, 1);
            }
        } catch (Exception e) {
            inisial = "A";
        }

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getRandomColor();
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();
        TextDrawable ic2 = builder.build(inisial, color2);
        mHolder.ivCover.setImageDrawable(ic2);

        mHolder.tvItemJudul.setText(nama);

        mHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(btnAnimasi);

                Intent intent = new Intent(c, FilmActivity.class);
                intent.putExtra(Constant.ID_KOTA, x.getIdKota());
                intent.putExtra(Constant.NAMA_KOTA, x.getKotaNama());
                c.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemJudul;
        public LinearLayout container;
        public ImageView ivCover;


        public UserViewHolder(View v) {
            super(v);

            tvItemJudul = (TextView) v.findViewById(R.id.tvItemJudul);
            ivCover = (ImageView) v.findViewById(R.id.ivItemCover);
            container = (LinearLayout) v.findViewById(R.id.container);


        }
    }
}
