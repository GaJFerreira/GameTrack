package com.example.gametrack.data.model.remote;

import java.util.List;

public class SteamResponseUsurario {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        private List<Player> players;

        public List<Player> getPlayers() {
            return players;
        }
    }

    public static class Player {
        private String personaname;
        private String avatarfull;
        public String getPersonaname() {
            return personaname;
        }
        public String getAvatarfull() {
            return avatarfull;
        }

    }
}

