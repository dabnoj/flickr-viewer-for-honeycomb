/**
 * 
 */
package com.charles.task;

import java.io.IOException;

import org.xml.sax.SAXException;

import android.os.AsyncTask;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.auth.AuthInterface;
import com.charles.event.IAuthDoneListener;

/**
 * @author charles
 * 
 */
public class AuthTask extends AsyncTask<String, Integer, Object> {

	public static final int TYPE_FROB = 0;
	public static final int TYPE_TOKEN = 1;

	private int mTaskType = TYPE_FROB;
	private IAuthDoneListener mListener;
	private AuthInterface mAuthInterface;

	public AuthTask(int type, IAuthDoneListener listener,
			AuthInterface authInterface) {
		this.mTaskType = type;
		this.mListener = listener;
		this.mAuthInterface = authInterface;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Object doInBackground(String... params) {
		if (mTaskType == TYPE_FROB) {
			try {
				return mAuthInterface.getFrob();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FlickrException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				return mAuthInterface.getToken(params[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FlickrException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		mListener.onAuthDone(mTaskType, result);
	}

}
