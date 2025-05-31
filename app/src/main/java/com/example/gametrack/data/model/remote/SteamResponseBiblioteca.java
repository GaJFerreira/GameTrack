package com.example.gametrack.data.model.remote;

import java.util.List;

public class SteamResponseBiblioteca {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {
        private int game_count;
        private List<Game> games;

        public int getGameCount() {
            return game_count;
        }

        public List<Game> getGames() {
            return games;
        }
    }

    public static class Game {
        private int appid;
        private String name;
        private int playtime_forever;
        private String img_icon_url;
        private boolean has_community_visible_stats;
        private boolean has_leaderboards;
        private List<Integer> content_descriptorids;

        public int getAppid() {
            return appid;
        }

        public String getName() {
            return name;
        }

        public int getPlaytimeForever() {
            return playtime_forever;
        }

        public String getImgIconUrl() {
            return img_icon_url;
        }

        public boolean hasCommunityVisibleStats() {
            return has_community_visible_stats;
        }

        public boolean hasLeaderboards() {
            return has_leaderboards;
        }

        public List<Integer> getContentDescriptorIds() {
            return content_descriptorids;
        }
    }
}
