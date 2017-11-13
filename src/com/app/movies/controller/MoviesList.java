package com.app.movies.controller;

import com.app.movies.controller.R;
import com.app.movies.internal.AccessInterface;
import com.app.movies.internal.Constants;
import com.app.movies.internal.ImageThreadLoader;
import com.app.movies.internal.PostImageLoadedListener;
import com.app.movies.internal.Utils;
import com.app.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class MoviesList extends ListActivity {

 private ListView lv;
 
 private Handler handler = new Handler(new ResultMessageCallback());

 private ProgressDialog pDialog;

 public final int MESSAGE_ERROR = -1;
 public final int MESSAGE_OK = 1;
 private List<Movie> m_movies = null;


ImageThreadLoader imageLoader = new ImageThreadLoader();



  
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
 
  setContentView(R.layout.activity_movies_list);
	 pDialog = ProgressDialog.show(this, getString(R.string.info), getString(R.string.loadMovies));
	  
		
		 Thread thread = new Thread(new LoadMovies());
		 thread.start();
  
		lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				int fixedpos = arg2;
				
				
				Intent intent= new Intent(MoviesList.this,DetailMovieActivity.class); 
				
				intent.putExtra("movie", m_movies.get(fixedpos));
                startActivity(intent);
                               
			}
		});
 
 } 


	private class LoadMovies implements Runnable {

		public void run() {

			int messageReturn = MESSAGE_OK;
			try{
				
				m_movies =( ArrayList<Movie>)AccessInterface.getPopularMovies();
				for(Movie temp:m_movies)
				{
					AccessInterface.getTrailer(temp);
					
				}
			}catch (Exception e){
				messageReturn =  MESSAGE_ERROR;
				
			}



			handler.sendEmptyMessage(messageReturn);
		}
	}

	private class ResultMessageCallback implements Callback {

		public boolean handleMessage(Message arg0) {
			pDialog.dismiss();
			

			switch (arg0.what) {
			case MESSAGE_ERROR:
				Toast.makeText(MoviesList.this,
						getString(R.string.ErrorCarga), Toast.LENGTH_LONG)
						.show();
				break;
			case  MESSAGE_OK:
			
				setListAdapter(new Adapter(MoviesList.this,R.layout.list_item, m_movies));			
				break;

			}

			return true; // lo marcamos como procesado
		}
	}
	


	
	private class Adapter extends ArrayAdapter<Movie> {

		private ArrayList<Movie> items;
	

		public Adapter(Context context, int textViewResourceId,
				List<Movie> items) {
			super(context, textViewResourceId, items);
			this.items = (ArrayList<Movie>) items;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 View v = convertView;

			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list_item, null);
			}

			final Movie p = items.get(position);
		
			if (p != null) {
				
				
			
				
				final TextView title = (TextView) v.findViewById(R.id.title);
				title.setTextSize(20);

				final TextView genre = (TextView) v.findViewById(R.id.genre);
				final TextView popularity = (TextView) v.findViewById(R.id.popularity);
				final TextView release = (TextView) v.findViewById(R.id.release);
				title.setText(p.getTitle());
				genre.setText(Utils.seqGenre(p.getGenre()));
			
				popularity.setText(p.getPopularity());
				release.setText(p.getReleaseyear());
				
				final ImageView imagen = (ImageView) v.findViewById(R.id.image);
				Bitmap cachedImage = null;
		
				if(p.getPoster().contains("jpg"))
				{
					
					
					try {
						

						PostImageLoadedListener pill = new PostImageLoadedListener(	imagen);
						String uri_imagen = Constants.POSTER_MOVIE+ p.getPoster();
						cachedImage = imageLoader.loadImage(uri_imagen,pill);
					
						
						if (cachedImage != null) {
							imagen.setImageBitmap(cachedImage);
						}
						

					} catch (Exception e) {
						
					}
				
				}
			

			}

			return v;

		}

	}

 

 }
