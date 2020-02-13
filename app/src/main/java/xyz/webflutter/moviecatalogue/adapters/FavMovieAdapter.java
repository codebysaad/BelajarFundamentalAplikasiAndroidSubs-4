package xyz.webflutter.moviecatalogue.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import xyz.webflutter.moviecatalogue.helper.database.MovieDbHelper;
import xyz.webflutter.moviecatalogue.models.ResultMovie;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.ViewHolderMovie> {
    private ArrayList<ResultMovie> favModels;
    private MovieDbHelper helper;
    private Context context;

    public FavMovieAdapter(ArrayList<ResultMovie> favModels, Context context){
        this.favModels = favModels;
        this.context = context;

        helper = MovieDbHelper.getDbHelper(context);
    }
    @NonNull
    @Override
    public FavMovieAdapter.ViewHolderMovie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_movie, parent, false);
        return new ViewHolderMovie(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull FavMovieAdapter.ViewHolderMovie holder, final int position) {
        holder.tvTitle.setText(favModels.get(position).getOriginalTitle());
        @SuppressLint("SimpleDateFormat") DateFormat formatIn = new SimpleDateFormat(context.getString(R.string.FORMAT_IN));
        @SuppressLint("SimpleDateFormat") DateFormat formatOut = new SimpleDateFormat(context.getString(R.string.FORMAT_OUT));
        String inputDate = favModels.get(position).getReleaseDate();
        Date date = null;
        try {
            date = formatIn.parse(inputDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        String outputDate = formatOut.format(date);
        holder.tvReleaseDate.setText(outputDate);
        holder.ratingBarMovie.setRating(Float.parseFloat(favModels.get(position).getVoteAverage()));

        Glide.with(context)
                .load(favModels.get(position).getPosterPath())
                .into(holder.ivPoster);
        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CharSequence[] choice = {"Yes", "No"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete This Favorite Movie?")
                        .setItems(choice, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        deleteMovie(position);
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

    class ViewHolderMovie extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvReleaseDate;
        private RatingBar ratingBarMovie;
        private final ImageView ivPoster;
        private ConstraintLayout constraintLayout;
        ViewHolderMovie(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_film_home);
            tvReleaseDate = itemView.findViewById(R.id.release_film_home);
            ratingBarMovie = itemView.findViewById(R.id.rating_movie_home);
            ivPoster = itemView.findViewById(R.id.poster_home);
            constraintLayout = itemView.findViewById(R.id.const_movie);
        }
    }

    private void deleteMovie(int position){
        helper.movieDao().deleteMovie(favModels.get(position));
        favModels.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, favModels.size());
        Toast.makeText(context, "Favorite Movie Deleted", Toast.LENGTH_SHORT).show();
    }
}
