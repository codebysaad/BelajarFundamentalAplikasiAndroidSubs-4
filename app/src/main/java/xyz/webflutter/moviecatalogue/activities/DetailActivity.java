package xyz.webflutter.moviecatalogue.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.Objects;

import xyz.webflutter.moviecatalogue.R;
import xyz.webflutter.moviecatalogue.helper.database.MovieDbHelper;
import xyz.webflutter.moviecatalogue.models.ResultMovie;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "movie_catalogue";
    private TextView title, overview, releasedDate, voteCount, popularity, language, voteAverage, overviewText;
    private String strTitle, strOverview, strReleaseDate, strVoteCount, strVoteAverage, strPopularity, strLanguage, strPoster;
    private ImageView poster;
    private RatingBar ratingBar;
    private View detailLayout;
    private CardView overviewLayout;
    private TableLayout tableLayout;
    private ProgressDialog progressDialog;
    private ShineButton favBtn;
    private MovieDbHelper helper;
    private ResultMovie modelMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        modelMovie = getIntent().getParcelableExtra(EXTRA_DATA);
        getItem();
        getView();
        helper = MovieDbHelper.getDbHelper(this);
        initAnimation();
        if (helper.movieDao().selectDetailMovie(String.valueOf(modelMovie.getId())) != null) {
            favBtn.setChecked(true);
        } else {
            favBtn.setChecked(false);
        }
        favBtn.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked) {
                    helper = MovieDbHelper.getDbHelper(getApplicationContext());
                    insertMovie(modelMovie);
                } else {
                    helper = MovieDbHelper.getDbHelper(getApplicationContext());
                    helper.movieDao().deleteMovie(modelMovie);
                    favBtn.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            progressDialog.dismiss();
        }
    }

    private void getItem() {
        title = findViewById(R.id.title_film);
        overview = findViewById(R.id.overview_film);
        releasedDate = findViewById(R.id.date_film);
        language = findViewById(R.id.language);
        voteCount = findViewById(R.id.vote_count);
        voteAverage = findViewById(R.id.vote_average);
        popularity = findViewById(R.id.popularity_film);
        poster = findViewById(R.id.poster);
        ratingBar = findViewById(R.id.vote_rating);
        overviewLayout = findViewById(R.id.overviewLayout);
        detailLayout = findViewById(R.id.detailLayout);
        overviewText = findViewById(R.id.overview);
        tableLayout = findViewById(R.id.table_view);
        favBtn = findViewById(R.id.btn_fav_movie);
    }

    private void getView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

                strTitle = modelMovie.getOriginalTitle();
                strOverview = modelMovie.getOverview();
                strReleaseDate = modelMovie.getReleaseDate();
                strLanguage = modelMovie.getOriginalLanguage();
                strVoteAverage = modelMovie.getVoteAverage();
                strVoteCount = modelMovie.getVoteCount();
                strPopularity = modelMovie.getPopularity();
                strPoster = modelMovie.getPosterPath();

                title.setText(strTitle);
                overview.setText(strOverview);
                releasedDate.setText(strReleaseDate);
                language.setText(strLanguage);
                voteAverage.setText(strVoteAverage);
                voteCount.setText(strVoteCount);
                popularity.setText(strPopularity);
                ratingBar.setRating(Float.parseFloat(strVoteAverage));
                Glide.with(getApplicationContext())
                        .load(strPoster)
                        .into(poster);
                Objects.requireNonNull(getSupportActionBar()).setSubtitle(strTitle);
            }
        }, 2000);
    }

    private void initAnimation() {
        Animation zoomingAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
        Animation swipeUp1 = AnimationUtils.loadAnimation(this, R.anim.swipe_animation1);
        Animation swipeUp2 = AnimationUtils.loadAnimation(this, R.anim.swipe_animation2);

        poster.startAnimation(zoomingAnimation);
        title.startAnimation(swipeUp1);
        overviewText.startAnimation(swipeUp1);
        overviewLayout.startAnimation(swipeUp2);
        detailLayout.startAnimation(swipeUp2);
        overview.startAnimation(swipeUp2);
        tableLayout.startAnimation(swipeUp2);
    }

    @SuppressLint("StaticFieldLeak")
    private void insertMovie(final ResultMovie models) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return helper.movieDao().insertMovie(models);
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(DetailActivity.this, "Favorite ditambahkan  " + status, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
