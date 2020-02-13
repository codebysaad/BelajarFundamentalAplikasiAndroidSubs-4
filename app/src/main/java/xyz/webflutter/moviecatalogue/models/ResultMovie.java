package xyz.webflutter.moviecatalogue.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "tbMovie")
public class ResultMovie implements Parcelable {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String originalTitle;
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private String voteAverage;
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String originalLanguage;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private String popularity;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("video")
    private boolean video;
    @SerializedName("adult")
    private boolean adult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeString(this.voteCount);
        dest.writeString(this.voteAverage);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.posterPath);
        dest.writeString(this.popularity);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.backdropPath);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
    }

    public ResultMovie() {
    }

    protected ResultMovie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.originalTitle = in.readString();
        this.voteCount = in.readString();
        this.voteAverage = in.readString();
        this.originalLanguage = in.readString();
        this.posterPath = in.readString();
        this.popularity = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.backdropPath = in.readString();
        this.video = in.readByte() != 0;
        this.adult = in.readByte() != 0;
    }

    public static final Creator<ResultMovie> CREATOR = new Creator<ResultMovie>() {
        @Override
        public ResultMovie createFromParcel(Parcel source) {
            return new ResultMovie(source);
        }

        @Override
        public ResultMovie[] newArray(int size) {
            return new ResultMovie[size];
        }
    };
}
