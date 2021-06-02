package com.linkeriyo.cybermanger.models;

import com.linkeriyo.cybermanger.CyberManager;
import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

public class Post {

    private String pk;
    private String title;
    private String content;
    private String image;
    private Date date;

    public Post(JSONObject json) throws JSONException, ParseException {
        pk = json.getString(Tags.PK);
        title = json.getString(Tags.TITLE);
        content = json.getString(Tags.CONTENT);
        image = json.getString(Tags.IMAGE);
        date = CyberManager.sdf.parse(json.getString(Tags.DATE));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Tags.PK, pk);
        json.put(Tags.TITLE, title);
        json.put(Tags.CONTENT, content);
        json.put(Tags.IMAGE, image);
        json.put(Tags.DATE, CyberManager.sdf.format(date));
        return json;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
