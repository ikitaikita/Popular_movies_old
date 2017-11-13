package com.app.movies.model;

import java.io.Serializable;

public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ID;
	private String title;
	private String overview;
	private String poster;
	private String genre;	
	private String releaseyear;
	private String popularity;	
	private String image;
	private String keyTrailer;
	private String idTrailer;

	


public Movie(String ID)
	{
		this.ID = ID;
		this.setKeyTrailer("");
		this.setIdTrailer("");
	
		
	}
public void setTitle(String title){
	this.title = title ;
}
public String getTitle(){
	return title;
}

public String getID(){
	return ID;
}
public String getOverview() {
	return overview;
}
public void setOverview(String overview) {
	this.overview = overview;
}

public String getGenre() {
	return genre;
}
public void setGenre(String genre) {
	this.genre = genre;
}
public String getReleaseyear() {
	return releaseyear;
}
public void setReleaseyear(String releaseyear) {
	this.releaseyear = releaseyear;
}
public String getPopularity() {
	return popularity;
}
public void setPopularity(String popularity) {
	this.popularity = popularity;
}
public String getPoster() {
	return poster;
}
public void setPoster(String poster) {
	this.poster = poster;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public String getKeyTrailer() {
	return keyTrailer;
}
public void setKeyTrailer(String keyTrailer) {
	this.keyTrailer = keyTrailer;
}
public String getIdTrailer() {
	return idTrailer;
}
public void setIdTrailer(String idTrailer) {
	this.idTrailer = idTrailer;
}



}
