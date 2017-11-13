package com.app.movies.controller;

import com.app.movies.controller.R;
import com.app.movies.internal.Constants;
import com.app.movies.internal.ImageThreadLoader;
import com.app.movies.internal.PostImageLoadedListener;
import com.app.movies.internal.Utils;
import com.app.movies.model.Movie;
import com.app.movies.internal.DeveloperKey;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class DetailMovieActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
	
	private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;

	
	private ImageThreadLoader imageLoader = new ImageThreadLoader();
	//layout
	private ImageView poster;
	private TextView title, genre, popularity,releaseyear, overview ;
	private TextView textgenre, textpopularity,textreleaseyear,textoverview ;

	
	//private Activity activity;
	private Movie mMovie;

	public final int MESSAGE_ERROR = -1;
    public final int MESSAGE_OK = 1;
    private ProgressDialog pDialog;	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_movie_layout);
		
		poster = (ImageView) findViewById(R.id.poster);	
		title = (TextView) findViewById(R.id.title);
		textgenre = (TextView) findViewById(R.id.textGenre);
		genre = (TextView) findViewById(R.id.genre);
		textpopularity = (TextView) findViewById(R.id.textPopularity);
		popularity = (TextView) findViewById(R.id.popularity);
		textreleaseyear = (TextView) findViewById(R.id.textReleaseYear);
		releaseyear =(TextView) findViewById(R.id.releaseYear);
		textoverview = (TextView) findViewById(R.id.textOverview);
		overview = (TextView) findViewById(R.id.overview);
		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
		overview.setMovementMethod(ScrollingMovementMethod.getInstance());
		
		
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {          
		
		
		}
		mMovie= (Movie) getIntent().getExtras().getSerializable("movie");
		if(mMovie!=null)
		{
					
			dataView();
		
		}
	

	
	}
	

		
		@SuppressLint("ResourceAsColor")
		private void dataView()
		{
		
			title.setTextSize(30);			
			title.setText(mMovie.getTitle());
			textgenre.setTextSize(20);
			textgenre.setText("Genre");
			
			genre.setText(Utils.seqGenre(mMovie.getGenre()));
			if(!mMovie.getOverview().equals(""))
			{
				textoverview.setTextSize(20);
				textoverview.setText("Overview");
				overview.setText(mMovie.getOverview());
			}
			
			
			textpopularity.setTextSize(20);
			textpopularity.setText("Popularity");
			popularity.setText(mMovie.getPopularity());
			textreleaseyear.setTextSize(20);
			textreleaseyear.setText("Release Year");
			releaseyear.setText(mMovie.getReleaseyear());
		
			Bitmap cachedImage = null;
			poster.setImageBitmap(null);
			if(mMovie.getPoster().contains("jpg"))
			{
				
				
				try {
					

					PostImageLoadedListener pill = new PostImageLoadedListener(
							poster);
					String uri_movie = Constants.POSTER_MOVIE+ mMovie.getPoster();
					cachedImage = imageLoader.loadImage(uri_movie,pill);					
					
					if (cachedImage != null) {
						poster.setImageBitmap(cachedImage);
					}
					

				} catch (Exception e) {
					
				}
			
			}
			if(!mMovie.getKeyTrailer().equals(""))
			{
				youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);		
				
				
			}
			
		}

protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
@Override
public void onInitializationFailure(Provider provider,
		YouTubeInitializationResult errorReason) {
	
	 if (errorReason.isUserRecoverableError()) {
         errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
     } else {
         String error = String.format(getString(R.string.player_error), errorReason.toString());
         Toast.makeText(this, error, Toast.LENGTH_LONG).show();
     }
}



@Override
public void onInitializationSuccess(Provider provider, YouTubePlayer player,boolean wasRestored) {
	
	if(!wasRestored)
	{
		player.cueVideo(mMovie.getKeyTrailer());
		
	}
	
	
}
	
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == RECOVERY_REQUEST) {
        // Retry initialization if user performed a recovery action
        getYouTubePlayerProvider().initialize(DeveloperKey.DEVELOPER_KEY, this);
    }
}

}
