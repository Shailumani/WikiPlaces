package com.vishesh.wikiplaces;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;

public class UploadActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnTaskCompleted{

		TextView gpsCoords;
		EditText placeName, stateName, textLang, textLat;
		Button submitButton;
		String latd, latm, lats, latns, longd, longm, longs, longew;
		String edittoken ;
		String starttimestamp;
		public PlaceholderFragment() {
		}

		@Override
		public void onTaskCompleted(ArrayList<String> searchResult) {
			Toast.makeText(getActivity(), searchResult.get(0), Toast.LENGTH_LONG).show();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			gpsCoords = (TextView) rootView.findViewById(R.id.gpsCoords);
			placeName = (EditText) rootView.findViewById(R.id.placeName);
			stateName = (EditText) rootView.findViewById(R.id.stateName);
			//textLang = (EditText) rootView.findViewById(R.id.txt_Lat);
			//textLat = (EditText) rootView.findViewById(R.id.txt_Lang);
			submitButton = (Button) rootView.findViewById(R.id.submitButton);

			submitButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
//					Network.makeEdit(placeName.getText().toString(), edittoken, starttimestamp, latd, latm, lats, latns, longd, longm, longs, longew);
					 new UploadData(PlaceholderFragment.this).execute("");
				}
			});

			// Acquire a reference to the system Location Manager
			LocationManager locationManager = (LocationManager) rootView
					.getContext().getSystemService(Context.LOCATION_SERVICE);

			// Define a listener that responds to location updates
			LocationListener locationListener = new LocationListener() {
				public void onLocationChanged(Location location) {
					// Called when a new location is found by the network
					// location provider.
					// makeUseOfNewLocation(location);
					double latitude = location.getLatitude();
					double vFractionLat = (latitude * 3600) % 3600;
					double vMinutesLat = vFractionLat / 60;
					double vSecondsLat = vFractionLat % 60;
					char ns;
					if (latitude > 0)
						ns = 'N';
					else
						ns = 'S';

					double longitude = location.getLongitude();
					double vFractionLong = (longitude * 3600) % 3600;
					double vMinutesLong = vFractionLong / 60;
					double vSecondsLong = vFractionLong % 60;
					char ew;
					if (longitude > 0)
						ew = 'E';
					else
						ew = 'W';

					// gpsCoords.setText("Co-ordinates: " +
					// location.getLatitude()
					// + "," + location.getLongitude());
					placeName.setText("User:JeoffreyHack/sandbox");
					gpsCoords.setText("Latitude : "+(int) latitude + "deg "
							+ (int) vMinutesLat + "min "
							+ (double) Math.round(vSecondsLat * 10) / 10
							+ "sec " + ns + "\nLongitude : "+(int) longitude
							+ "deg " + (int) vMinutesLong + "min "
							+ (double) Math.round(vSecondsLong * 10) / 10
							+ "sec " + ew);
					//textLang.setText(""+(int)longitude);
					//textLat.setText(""+(int)latitude);
					latd = ""+(int) latitude;
					latm = ""+(int) vMinutesLat;
					lats = ""+(double) Math.round(vSecondsLat * 10) / 10;
					latns = ""+ns;
					longd = ""+(int) longitude;
					longm = ""+(int) vMinutesLong;
					longs = ""+(double) Math.round(vSecondsLong * 10) / 10;
					longew = ""+ew;

				}
				

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
				}

				public void onProviderEnabled(String provider) {
					gpsCoords.setText(R.string.fetching_location);
				}

				public void onProviderDisabled(String provider) {
					gpsCoords
							.setText("Error. Please check your data connection.");
				}
			};

			//Register the listener with the Location Manager to receive
			// location updates
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			return rootView;
		}
		
		private class UploadData extends AsyncTask<String, String, String>{

			String resultEdit;
			private OnTaskCompleted listener;

			public UploadData(OnTaskCompleted listener){
				this.listener=listener;
			}
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String responseStr = Network.getEditToken();
				//String responseStrSplit[] = responseStr.split("@");
				 edittoken = responseStr;
				Log.d("EditToken", responseStr);
				// starttimestamp = responseStrSplit[1];
				/*double latitude = Double.parseDouble("20.622088");
				double vFractionLat = (latitude * 3600) % 3600;
				double vMinutesLat = vFractionLat / 60;
				double vSecondsLat = vFractionLat % 60;
				char ns;
				if (latitude > 0)
					ns = 'N';
				else
					ns = 'S';

				double longitude =  Double.parseDouble("77.544079");
				double vFractionLong = (longitude * 3600) % 3600;
				double vMinutesLong = vFractionLong / 60;
				double vSecondsLong = vFractionLong % 60;
				char ew;
				if (longitude > 0)
					ew = 'E';
				else
					ew = 'W';
				*/
				// gpsCoords.setText("Co-ordinates: " +
				// location.getLatitude()
				// + "," + location.getLongitude());
				resultEdit = Network.makeEdit(placeName.getText().toString(), stateName.getText().toString(), edittoken, starttimestamp, latd, latm, lats, latns, longd, longm, longs, longew);
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				ArrayList<String> resultArray = new ArrayList<String>();
				super.onPostExecute(result);
				resultArray.add(0, resultEdit);
				listener.onTaskCompleted(resultArray);
			}
			
		}
	}

}
