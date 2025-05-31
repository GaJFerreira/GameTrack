package com.example.gametrack.data.model.remote;

import java.util.List;

public class SteamResponseGetUser {
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

        public void setPlayers(List<Player> players) {
            this.players = players;
        }
    }

    public static class Player {
        private String steamid;
        private int communityvisibilitystate;
        private int profilestate;
        private String personaname;
        private int commentpermission;
        private String profileurl;
        private String avatar;
        private String avatarmedium;
        private String avatarfull;
        private String avatarhash;
        private int personastate;
        private String realname;
        private String primaryclanid;
        private long timecreated;
        private int personastateflags;
        private String loccountrycode;
        private String locstatecode;
        private int loccityid;

        // Getters e setters para todos os campos
        public String getSteamid() {
            return steamid;
        }
        public void setSteamid(String steamid) {
            this.steamid = steamid;
        }
        public int getCommunityvisibilitystate() {
            return communityvisibilitystate;
        }
        public void setCommunityvisibilitystate(int communityvisibilitystate) {
            this.communityvisibilitystate = communityvisibilitystate;
        }
        public int getProfilestate() {
            return profilestate;
        }
        public void setProfilestate(int profilestate) {
            this.profilestate = profilestate;
        }
        public String getPersonaname() {
            return personaname;
        }
        public void setPersonaname(String personaname) {
            this.personaname = personaname;
        }
        public int getCommentpermission() {
            return commentpermission;
        }
        public void setCommentpermission(int commentpermission) {
            this.commentpermission = commentpermission;
        }
        public String getProfileurl() {
            return profileurl;
        }
        public void setProfileurl(String profileurl) {
            this.profileurl = profileurl;
        }
        public String getAvatar() {
            return avatar;
        }
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
        public String getAvatarmedium() {
            return avatarmedium;
        }
        public void setAvatarmedium(String avatarmedium) {
            this.avatarmedium = avatarmedium;
        }
        public String getAvatarfull() {
            return avatarfull;
        }
        public void setAvatarfull(String avatarfull) {
            this.avatarfull = avatarfull;
        }
        public String getAvatarhash() {
            return avatarhash;
        }
        public void setAvatarhash(String avatarhash) {
            this.avatarhash = avatarhash;
        }
        public int getPersonastate() {
            return personastate;
        }
        public void setPersonastate(int personastate) {
            this.personastate = personastate;
        }
        public String getRealname() {
            return realname;
        }
        public void setRealname(String realname) {
            this.realname = realname;
        }
        public String getPrimaryclanid() {
            return primaryclanid;
        }
        public void setPrimaryclanid(String primaryclanid) {
            this.primaryclanid = primaryclanid;
        }
        public long getTimecreated() {
            return timecreated;
        }
        public void setTimecreated(long timecreated) {
            this.timecreated = timecreated;
        }
        public int getPersonastateflags() {
            return personastateflags;
        }
        public void setPersonastateflags(int personastateflags) {
            this.personastateflags = personastateflags;
        }
        public String getLoccountrycode() {
            return loccountrycode;
        }
        public void setLoccountrycode(String loccountrycode) {
            this.loccountrycode = loccountrycode;
        }
        public String getLocstatecode() {
            return locstatecode;
        }
        public void setLocstatecode(String locstatecode) {
            this.locstatecode = locstatecode;
        }
        public int getLoccityid() {
            return loccityid;
        }
        public void setLoccityid(int loccityid) {
            this.loccityid = loccityid;
        }
    }
}

