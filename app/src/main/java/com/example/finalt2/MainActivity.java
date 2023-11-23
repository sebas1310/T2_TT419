package com.example.finalt2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.finalt2.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private Button btnNew;
    private Button btnAll;
    private Button btnHoje;
    private Button btnsp;


    ListView jornalView;

    ArrayList<String> titles;
    ArrayList<String> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.topAppBar);


        btnNew=(Button)findViewById(R.id.newXML);
        btnAll=(Button)findViewById(R.id.allListXML);
        btnHoje=(Button)findViewById(R.id.listHoyXML);
        //btnsp=(Button)findViewById(R.id.sp);

        jornalView = (ListView) findViewById(R.id.jornaSPViewlXML);

        titles = new ArrayList<String>();
        links = new ArrayList<String>();

        jornalView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = Uri.parse(links.get(i));
                Intent intent =new  Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        new ProcessInBackground().execute();


//        btnsp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(MainActivity.this, jornalSP.class);
//                startActivity(it);
//            }
//        });


        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, NewReminder.class);
                startActivity(it);
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, allReminders.class);
                startActivity(it);
            }
        });

        btnHoje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, todayReminders.class);
                startActivity(it);
            }
        });

    }




    ////


    public InputStream getInputStream(URL url){
        try {
            return url.openConnection().getInputStream();
        }catch (IOException e){
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer,Void,Exception> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Carregando...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {
            try {
                URL url = new URL("https://feeds.folha.uol.com.br/emcimadahora/rss091.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url),"ISO-8859-1");

                boolean insideItem = false;
                int eventType = xpp.getEventType();
                while (eventType!=XmlPullParser.END_DOCUMENT){
                    if(eventType== XmlPullParser.START_TAG){
                        if(xpp.getName().equalsIgnoreCase("item")){
                            insideItem=true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if(insideItem){
                                titles.add("°" + xpp.nextText().toUpperCase());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if(insideItem){
                                links.add(xpp.nextText());
                            }
                        }
                    } else if (eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem=false;
                    }
                    eventType=xpp.next();
                }
            }catch (MalformedURLException e){
                exception=e;
            }catch (XmlPullParserException e){
                exception=e;
            }catch (IOException e){
                exception=e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,titles);
            jornalView.setAdapter(adapter);
            progressDialog.dismiss();

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent it = new Intent(MainActivity.this, confMenu.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

}