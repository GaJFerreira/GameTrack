package com.example.gametrack.data.model.remote;

import java.util.List;

public class SteamResponseDetalhesJogo {
    public String name;
    public String short_description;
    public String header_image;
    public List<String> developers;
    public List<Movie> movies;
    public ReleaseDate release_date;

    public String background;

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
        public String _480;
        public String max;
    }

    public static class Mp4 {
        public String _480;
        public String max;
    }

    public static class ReleaseDate {
        public boolean coming_soon;
        public String date;
    }
}
