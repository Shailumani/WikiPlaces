package com.vishesh.wikiplaces;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class StartActivity extends ActionBarActivity implements OnTaskCompleted {
    EditText searchField;
    ListView searchResultView;
    private boolean isSearchedOnce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        isSearchedOnce = false;

        searchField = (EditText)findViewById(R.id.searchField);
        searchResultView = (ListView)findViewById(R.id.listView);
        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    new UploadData(StartActivity.this).execute();
//					Network.makeEdit(placeName.getText().toString(), edittoken, starttimestamp, latd, latm, lats, latns, longd, longm, longs, longew);

            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSearchedOnce){
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);}
                else{
                    Toast.makeText(getApplicationContext(), "Please try searching...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskCompleted(ArrayList<String> searchResult) {
        final ArrayList<String> searchedResult = searchResult;
        if(searchedResult.size()>0) {
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, searchedResult);
            // Set The Adapter
            searchResultView.setAdapter(arrayAdapter);
            searchResultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                // argument position gives the index of item which is clicked
                public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                    String selectedPage = searchedResult.get(position);
                    Intent intent = new Intent(StartActivity.this, DisplayPage.class);
                    intent.putExtra("title", selectedPage);
                    startActivity(intent);
                    //TO BE IMPLEMENTED
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Sorry! No results found\nTry creating a new page", Toast.LENGTH_LONG).show();
        }
        isSearchedOnce = true;
    }

    private class UploadData extends AsyncTask<String, String, String> {
        private ArrayList<String> responseStr;
        private OnTaskCompleted listener;

        public UploadData(OnTaskCompleted listener){
            this.listener=listener;
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            responseStr = Network.search(searchField.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            super.onPostExecute(result);
            listener.onTaskCompleted(responseStr);
        }

    }
}
