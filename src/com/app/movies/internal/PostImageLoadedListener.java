package com.app.movies.internal;



import com.app.movies.internal.ImageThreadLoader.ImageLoadedListener;

import android.graphics.Bitmap;
import android.widget.ImageView;


public class PostImageLoadedListener implements ImageLoadedListener {
	private ImageView m_image = null;

	public PostImageLoadedListener(ImageView image) {
		m_image = image;
	}
	


	@Override
	public void imageLoaded(Bitmap imageBitmap) {
		m_image.setImageBitmap(imageBitmap);
	}
}
