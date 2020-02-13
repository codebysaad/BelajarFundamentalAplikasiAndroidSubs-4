package xyz.webflutter.moviecatalogue.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import xyz.webflutter.moviecatalogue.ItemClickSupport;
import xyz.webflutter.moviecatalogue.R;
import xyz.webflutter.moviecatalogue.activities.DetailActivity;
import xyz.webflutter.moviecatalogue.adapters.FavMovieAdapter;
import xyz.webflutter.moviecatalogue.helper.database.MovieDbHelper;
import xyz.webflutter.moviecatalogue.models.ResultMovie;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment {
    private RecyclerView rvFavMovie;
    private ArrayList<ResultMovie> favModels;

    public FavMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        rvFavMovie = view.findViewById(R.id.rv_fav_movie);
        TextView tvError = view.findViewById(R.id.error_fav_item_movie);
        favModels = new ArrayList<>();
        MovieDbHelper helper = MovieDbHelper.getDbHelper(getActivity());
        favModels.addAll(Arrays.asList(helper.movieDao().readMovies()));
        rvFavMovie.setHasFixedSize(true);
        if (favModels.isEmpty()){
            tvError.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Nothing Favorite Item In Movie", Toast.LENGTH_SHORT).show();
        }else {
            tvError.setVisibility(View.GONE);
            rvFavMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
            FavMovieAdapter adapter = new FavMovieAdapter(favModels, getActivity());
            rvFavMovie.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clickFavMovie();
    }

    private void clickFavMovie() {
        ItemClickSupport.addTo(rvFavMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                try {
                    ResultMovie models = new ResultMovie();
                    models.setId(favModels.get(position).getId());
                    models.setOriginalTitle(favModels.get(position).getOriginalTitle());
                    models.setOriginalLanguage(favModels.get(position).getOriginalLanguage());
                    models.setReleaseDate(favModels.get(position).getReleaseDate());
                    models.setOverview(favModels.get(position).getOverview());
                    models.setPopularity(favModels.get(position).getPopularity());
                    models.setVoteAverage(favModels.get(position).getVoteAverage());
                    models.setVoteCount(favModels.get(position).getVoteCount());
                    models.setPosterPath(favModels.get(position).getPosterPath());
                    intent.putExtra(DetailActivity.EXTRA_DATA, models);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}