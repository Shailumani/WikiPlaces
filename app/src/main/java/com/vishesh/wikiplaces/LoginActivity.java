package com.vishesh.wikiplaces;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_layout);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment(this))
					.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
	public static class PlaceholderFragment extends Fragment {

		EditText username, password;
		Button loginButton;
		TextView output;
		String responseStr;
		String usernameStr;
		String passwordStr;
		Activity actv;

		public PlaceholderFragment(Activity actv) {
			this.actv = actv;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.login_layout, container,
					false);
			username = (EditText) rootView.findViewById(R.id.username);
			password = (EditText) rootView.findViewById(R.id.password);
			//username.setText("Dipesh Parab");
			//password.setText("dipesh123");
			username.setText("JeoffreyHack");
			password.setText("wphackathon");
			loginButton = (Button) rootView.findViewById(R.id.loginButton);

			loginButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					usernameStr = username.getText().toString();
					passwordStr = password.getText().toString();
					// int responseCode;

					// Intent i;
				 new	LoginAsync().execute("");

//					actv.runOnUiThread(new Runnable() {
//
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							if (usernameStr != null && passwordStr != null) {
//
//								// TODO Auto-generated method stub
//								responseStr = Network.login(usernameStr,
//										passwordStr);
//
//								// output.setText(responseStr);
//								if (responseStr.equals("Success")) {
//									Intent i = new Intent(getActivity(),
//											UploadActivity.class);
//									startActivity(i);
//								}
//							} else {
//								Toast.makeText(getActivity(),
//										"Please enter all fields",
//										Toast.LENGTH_LONG).show();
//							}
//						}
//					});

				}
			});

			return rootView;
		}

		private class LoginAsync extends AsyncTask<String, String, String> {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				if (usernameStr != null && passwordStr != null) {

					// TODO Auto-generated method stub
					responseStr = Network.login(usernameStr, passwordStr);

					// output.setText(responseStr);
					
				} else {
					Toast.makeText(getActivity(), "Please enter all fields",
							Toast.LENGTH_LONG).show();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub


				super.onPostExecute(result);
				if (responseStr.equals("Success")) {
					Intent intent = new Intent(getActivity(), UploadActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(getActivity(), "Login Failed!\nPlease check your credentials/connection!", Toast.LENGTH_LONG).show();
				}
			}

		}
	}

}
