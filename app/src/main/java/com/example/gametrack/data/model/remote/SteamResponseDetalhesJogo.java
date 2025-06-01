package com.example.gametrack.data.model.remote;

import java.util.List;
import java.util.Map;

public class SteamResponseDetalhesJogo {
    public String name;
    public int steam_appid;
    public int required_age;
    public boolean is_free;
    public String controller_support;
    public List<Integer> dlc;
    public String detailed_description;
    public String about_the_game;
    public String short_description;
    public String supported_languages;
    public String header_image;
    public String capsule_image;
    public String capsule_imagev5;
    public String website;
    public List<String> developers;
    public List<String> publishers;
    public List<Integer> packages;
    public Platforms platforms;
    public Metacritic metacritic;
    public List<Category> categories;
    public List<Genre> genres;
    public List<Screenshot> screenshots;
    public List<Movie> movies;
    public Recommendations recommendations;
    public ReleaseDate release_date;
    public String background;
    public String background_raw;

    public static class Platforms {
        public boolean windows;
        public boolean mac;
        public boolean linux;
    }

    public static class Metacritic {
        public int score;
        public String url;
    }

    public static class Category {
        public int id;
        public String description;
    }

    public static class Genre {
        public String id;
        public String description;
    }

    public static class Screenshot {
        public int id;
        public String path_thumbnail;
        public String path_full;
    }

    public static class Movie {
        public int id;
        public String name;
        public String thumbnail;
        public Webm webm;
        public Mp4 mp4;
        public boolean highlight;
    }

    public static class Webm {
        public String _480; // variável não pode começar com número, pode usar @SerializedName
        public String max;
    }

    public static class Mp4 {
        public String _480;
        public String max;
    }

    public static class Recommendations {
        public int total;
    }

    public static class ReleaseDate {
        public boolean coming_soon;
        public String date;
    }
}
