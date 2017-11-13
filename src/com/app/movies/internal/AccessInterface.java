package com.app.movies.internal;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.app.movies.model.Movie;



public class AccessInterface {
	
	

	
	public static List<Movie> getPopularMovies()
	{
		 ArrayList<Movie> list_movies = new ArrayList<Movie>();
	
		 
		
		 String idMovie, title,overview,poster,genre,releaseyear,popularity="";

		
		
		 try {
			   
			   
			   HttpGet httpGet = new HttpGet(Constants.MOST_POPULAR+"&"+Constants.API_KEY);
			 
			   HttpClient httpClient = new DefaultHttpClient();
			   HttpResponse response = (HttpResponse)httpClient.execute(httpGet);
			   HttpEntity entity = response.getEntity();
			   BufferedHttpEntity buffer = new BufferedHttpEntity(entity);
			   InputStream iStream = buffer.getContent();
			                    
			   String aux = "";
			            
			   BufferedReader r = new BufferedReader(new InputStreamReader(iStream));
			   new StringBuilder();
			   String line;
			   while ((line = r.readLine()) != null) {
			     aux += line;
			   }
			   JSONObject jsonObject = new JSONObject(aux);
			   JSONArray movies = jsonObject.getJSONArray("results");
			 
			  
			   for(int i = 0; i < movies.length(); i++) {
				   JSONObject data_movies = movies.getJSONObject(i);
				   
				    idMovie = data_movies.getString("id");
				    title = data_movies.getString("title");
				    overview = data_movies.getString("overview");
				    poster = data_movies.getString("poster_path");
				    genre = data_movies.getString("genre_ids");
				    releaseyear = data_movies.getString("release_date");
				    popularity = data_movies.getString("popularity");
				    Movie res = new Movie(idMovie);
				    res.setTitle(title);
				    res.setOverview(overview);
				    res.setPoster(poster);
				    res.setGenre(genre);
				    res.setReleaseyear(releaseyear);
				    res.setPopularity(popularity);		
				
				   
				    list_movies.add(res);
				    
				    }
		

		   }catch (Exception e){
			   e.printStackTrace();
		   }

		 
		   return list_movies;
	}
	public static void getTrailer(Movie movie)
	{
		
		
		 String idTrailer, keyTrailer="";

		
		
		 try {
			   
		
			   
			   String url = Constants.PRE_TRAILER_MOVIE+movie.getID()+Constants.SUF_TRAILER_MOVIE;	
			   HttpGet httpGet = new HttpGet(url.trim());		
			   HttpClient httpClient = new DefaultHttpClient();
			   HttpResponse response = (HttpResponse)httpClient.execute(httpGet);
			   HttpEntity entity = response.getEntity();
			   BufferedHttpEntity buffer = new BufferedHttpEntity(entity);
			   InputStream iStream = buffer.getContent();
			                    
			   String aux = "";
			            
			   BufferedReader r = new BufferedReader(new InputStreamReader(iStream));
			   new StringBuilder();
			   String line;
			   while ((line = r.readLine()) != null) {
			     aux += line;
			   }
			   JSONObject jsonObject = new JSONObject(aux);
			   JSONArray movies = jsonObject.getJSONArray("results");
			  
			   for(int i = 0; i < movies.length(); i++) {
				   JSONObject data_movies = movies.getJSONObject(i);
				   
				    idTrailer = data_movies.getString("id");
				    keyTrailer= data_movies.getString("key");			    
				    
				    movie.setIdTrailer(idTrailer);
				    movie.setKeyTrailer(keyTrailer);
				
				
				    
				    }
		

		   }catch (Exception e){
			   e.printStackTrace();
		   }

		 
		 
	}
   
   
	public static Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
       } catch (IOException e) {
      
       }
       return bm;
    } 
    
    



}
