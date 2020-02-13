package xyz.webflutter.moviecatalogue.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xyz.webflutter.moviecatalogue.R;
import xyz.webflutter.moviecatalogue.helper.database.TvShowDbHelper;
import xyz.webflutter.moviecatalogue.models.ResultTvShow;

public class FavShowAdapter extends RecyclerView.Adapter<FavShowAdapter.FavShowViewHolder> {

    private ArrayList<ResultTvShow> favModels;
    private TvShowDbHelper helper;
    private Context context;

    public FavShowAdapter(ArrayList<ResultTvShow> favModels, Context context) {
        this.favModels = favModels;
        this.context = context;

        helper = TvShowDbHelper.getTvShowDbHelper(context);
    }

    @NonNull
    @Override
    public FavShowAdapter.FavShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_tv, parent, false);
        return new FavShowViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavShowAdapter.FavShowViewHolder holder, final int position) {
        holder.tvTitle.setText(favModels.get(position).getName());
        @SuppressLint("SimpleDateFormat") DateFormat formatIn = new SimpleDateFormat(context.getString(R.string.FORMAT_IN));
        @SuppressLint("SimpleDateFormat") DateFormat formatOut = new SimpleDateFormat(context.getString(R.string.FORMAT_OUT));
        String inputDate = favModels.get(position).getFirstAirDate();
        Date date = null;
        try {
            date = formatIn.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDate = formatOut.format(date);
        holder.tvFirstDate.setText(outputDate);
        Glide.with(context)
                .load(favModels.get(position).getPosterPath())
                .into(holder.thumbnail);
        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CharSequence[] choice = {"Yes", "No"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete This Favorite Tv Show?")
                        .setItems(choice, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        deleteTvShow(position);
                                        break;

                                    case 1:
                                        dialog.cancel();
                                        break;
                                }
                            }
                        });
                dialog.create();
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return favModels.size();
    }

    class FavShowViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvFirstDate;
        final ImageView thumbnail;
        final ConstraintLayout constraintLayout;

        FavShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_tv_show);
            tvFirstDate = itemView.findViewById(R.id.first_date);
            thumbnail = itemView.findViewById(R.id.thumbnail_tv_show);
            constraintLayout = itemView.findViewById(R.id.const_tv_show);
        }
    }

    private void deleteTvShow(int position) {
        helper.tvShowDao().deleteTvShow(favModels.get(position));
        favModels.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, favModels.size());
        Toast.makeText(context, "Favorite TvShow Deleted", Toast.LENGTH_SHORT).show();
    }
}
