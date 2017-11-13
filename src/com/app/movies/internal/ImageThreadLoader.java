package com.app.movies.internal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;


public class ImageThreadLoader {

	private ConcurrentHashMap<URL, SoftReference<Bitmap>> cache = new ConcurrentHashMap<URL, SoftReference<Bitmap>>();

	private ConcurrentLinkedQueue<QueueItem> queue = new ConcurrentLinkedQueue<QueueItem>();

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	private Handler handler = new Handler(); 

	
	public interface ImageLoadedListener {

		public void imageLoaded(Bitmap imageBitmap);

	}

	private final class QueueItem {

		public URL url;
		public ImageLoadedListener listener;

	}

	private class NotifyImageLoadedTask implements Runnable {

		private ImageLoadedListener listener;
		private Bitmap bitmap;

		public NotifyImageLoadedTask(ImageLoadedListener listener, Bitmap bitmap) {
			this.listener = listener;
			this.bitmap = bitmap;
		}

		@Override
		public void run() {
			listener.imageLoaded(bitmap);
		}

	}


	private class QueueRunner implements Runnable {

		@Override
		public void run() {
			QueueItem item = queue.poll();

			SoftReference<Bitmap> ref = cache.get(item.url);

			Bitmap bitmap = null;
			if (ref != null)
				bitmap = ref.get();

			// If in the cache, return that copy and be done
			if (bitmap != null) {
				if (item.listener != null)
					// Use a handler to get back onto the UI thread for the update
					handler.post(new NotifyImageLoadedTask(item.listener, bitmap));
			} else {
				bitmap = readBitmapFromNetwork(item.url);
				if (bitmap != null)
					cache.put(item.url, new SoftReference<Bitmap>(bitmap));
				if (item.listener != null)
					// Use a handler to get back onto the UI thread for the update
					handler.post(new NotifyImageLoadedTask(item.listener, bitmap));
			}
		}
	}



	@SuppressWarnings("unused")
	public Bitmap loadImage(String uriStr, ImageLoadedListener listener) throws MalformedURLException {
	
		URL url = new URL(uriStr);
		SoftReference<Bitmap> ref = cache.get(url);
		if (ref != null) {
			Bitmap bitmap = ref.get();
			if (bitmap != null)
				return bitmap;
			else 
			{
			
				if (bitmap != null)
				{
					return bitmap;
				}
			}
		}

		QueueItem item = new QueueItem();
		item.url = url;
		item.listener = listener;

		queue.offer(item);

		executor.execute(new QueueRunner());

		return null;
	}


	public static Bitmap readBitmapFromNetwork(URL url) {
		InputStream is = null;
		BufferedInputStream bis = null;
		Bitmap bmp = null;
		try {
	
			bmp = BitmapFactory.decodeStream(url.openStream());
		} catch (MalformedURLException e) {
		
		} catch (IOException e) {
			
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (is != null)
					is.close();
			} catch (IOException e) {

			}
		}
		return bmp;
	}

}