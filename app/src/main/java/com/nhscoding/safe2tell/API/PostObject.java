package com.nhscoding.safe2tell.API;

import java.util.Objects;

/**
 * Created by david_000 on 2/11/2015.
 */
public class PostObject extends Object {

    public int _ID = -1;
    public int _Genre = -1;
    public int _ProblemID = -1;
    public int _logo = -1;
    public String _Title = "Error";
    public String _Text = "Error";
    public Boolean _media = false;
    public int _Level = -1;

    PostObject() {
        super();
    }

    public PostObject(int id, int level, int genre, int logo, int problemID, String text, String title) {
        _ID = id;
        _Genre = genre;
        _ProblemID = problemID;
        _logo = logo;
        _Title = title;
        _Text = text;
        _Level = level;
    }

    public void setID (int ID) {
        _ID = ID;
    }

    public void setGenre(int Genre) {
        _Genre = Genre;
    }

    public void setProblemID(int ID) {
        _ProblemID = ID;
    }

    public void setLogo(int logo) {
        _logo = logo;
    }

    public void setTitle(String title) {
        _Title = title;
    }

    public void setText(String text) {
        _Text = text;
    }

    public void setMedia(Boolean media) {
        _media = media;
    }
}
