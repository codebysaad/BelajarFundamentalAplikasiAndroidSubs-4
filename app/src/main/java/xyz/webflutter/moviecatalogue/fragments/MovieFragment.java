package xyz.webflutter.moviecatalogue.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.webflutter.moviecatalogue.BuildConfig;
import xyz.webflutter.moviecatalogue.ItemClickSupport;
import xyz.webflutter.moviecatalogue.models.ModelMovie;
import xyz.webflutter.moviecatalogue.viewmodel.MainViewModel;
import xyz.webflutter.moviecatalogue.R;
import xyz.webflutter.moviecatalogue.activities.DetailActivity;
import xyz.webflutter.moviecatalogue.adapters.AdapterMovie;
import xyz.webflutter.moviecatalogue.models.ResultMovie;

public class MovieFragment extends Fragment {
    private RecyclerView rvMovie;
    private ArrayList<ResultMovie> listMovie = new ArrayList<>();
    private AdapterMovie adapter;
    private ProgressBar progressBar;
    private TextView tvLoading;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progress_bar);
        tvLoading = view.findViewById(R.id.tv_loading_movie);
        progressBar.showContextMenu();


        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.initializeMovie();
        viewModel.getMoviesModel().observe(this, new Observer<ModelMovie>() {
            @Override
            public void onChanged(ModelMovie modelMovie) {
                showLoading(false);
                List<ResultMovie> resultMovies = modelMovie.getResultMovies();
                listMovie.addAll(resultMovies);
                adapter.notifyDataSetChanged();
            }
        });

        initRv();

        clickMovie();

    }

    private void initRv() {
        if (adapter == null) {
            adapter = new AdapterMovie(getActivity(), listMovie);
            rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvMovie.setAdapter(adapter);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.recyclerview_animation);
            rvMovie.startAnimation(animation);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void clickMovie() {
        ItemClickSupport.addTo(rvMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                try {
                    ResultMovie modelMovie = new ResultMovie();
                    modelMovie.setId(listMovie.get(position).getId());
                    modelMovie.setOriginalTitle(listMovie.get(position).getOriginalTitle());
                    modelMovie.setOriginalLanguage(listMovie.get(position).getOriginalLanguage());
                    modelMovie.setReleaseDate(listMovie.get(position).getReleaseDate());
                    modelMovie.setOverview(listMovie.get(position).getOverview());
                    modelMovie.setPopularity(listMovie.get(position).getPopularity());
                    modelMovie.setVoteAverage(listMovie.get(position).getVoteAverage());
                    modelMovie.setVoteCount(listMovie.get(position).getVoteCount());
                    modelMovie.setPosterPath(BuildConfig.IMAGE + listMovie.get(position).getPosterPath());
                    intent.putExtra(DetailActivity.EXTRA_DATA, modelMovie);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            tvLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
