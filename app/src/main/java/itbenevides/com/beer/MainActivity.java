package itbenevides.com.beer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getBeerDB().execute();
            }
        });
        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getBeerServer().execute();
            }
        });


    }



    private class getBeerServer extends AsyncTask<Void,Void,Void>{
        List<Beer> beers;
        boolean erro=false;
        ProgressDialog barProgressDialog;

        @Override
        protected void onPreExecute() {
            barProgressDialog = new ProgressDialog(MainActivity.this);

            barProgressDialog.setTitle("Downloading data ...");

            barProgressDialog.setMessage("json ...");



            barProgressDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {

            try {


            URL url = null;
            url = new URL("http://ontariobeerapi.ca:80/products/");
            HttpURLConnection urlConnection = null;


                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = null;

                in = new BufferedInputStream(urlConnection.getInputStream());

                InputStreamReader Input = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(Input);


                Gson gson = new Gson();
               beers = Arrays.asList(gson.fromJson(reader, Beer[].class));
//              feito assim por vir dados duplicados no json
                gambiarra();

                Beer.onCreate(DAO.getHelper(getApplicationContext()).getWritableDatabase());
                DAO.getHelper(getApplicationContext()).salvar(beers);

                beers= Beer.consultar(DAO.getHelper(getApplicationContext()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                erro=true;
            } catch (IOException e) {
                e.printStackTrace();
                erro=true;
            }

            return null;
        }

        public void gambiarra(){
//para tirar dados duplicados
            List<Beer> auxBeer = new ArrayList<>();



            for(Beer beer:beers){
                boolean tem=false;
                for(Beer beer1:auxBeer){
                    if(beer1.name.trim().equalsIgnoreCase(beer.name.trim())){
                        tem=true;
                        break;
                    }


                }
                if(!tem){
                    auxBeer.add(beer);
                }
            }
            beers = new ArrayList<>();
            beers.addAll(auxBeer);

        }

        @Override
        protected void onPostExecute(Void aVoid) {


            if(barProgressDialog.isShowing())barProgressDialog.cancel();

        if(!erro){
            new getBeerDB().execute();
        }else{
            Toast.makeText(getApplicationContext(), "Error :/", Toast.LENGTH_LONG).show();
        }


        }
    }

    private class getBeerDB extends AsyncTask<Void,Void,Void>{
        List<Beer> beers;
        boolean erro=false;
        ProgressDialog barProgressDialog;

        @Override
        protected void onPreExecute() {
            barProgressDialog = new ProgressDialog(MainActivity.this);

            barProgressDialog.setTitle("Loading data ...");

            barProgressDialog.setMessage("DB ...");



            barProgressDialog.show();


        }
        @Override
        protected Void doInBackground(Void... params) {

            try {


                beers= Beer.consultar(DAO.getHelper(getApplicationContext()));

            } catch (Exception e) {
                e.printStackTrace();
                erro=true;
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {


            if(barProgressDialog.isShowing())barProgressDialog.cancel();


            if(!erro){


                if(beers.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Empty :/",Toast.LENGTH_LONG).show();
                    return;
                }

                beerAdapter beerAdapter = new beerAdapter(beers,getApplicationContext());



                final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


                mRecyclerView.setHasFixedSize(true);


                LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);



                mRecyclerView.setAdapter(beerAdapter);






            }else{
                Toast.makeText(getApplicationContext(), "Error :/", Toast.LENGTH_LONG).show();
            }





        }
    }










}
