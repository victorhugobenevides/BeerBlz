package itbenevides.com.beer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class InfoBeer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_beer);
        String json=null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            json = extras.getString("beer");
        }
        Beer beer= new Gson().fromJson(json, Beer.class);
        Toast.makeText(getApplicationContext(), beer.getName(), Toast.LENGTH_LONG).show();


        //não tenho permissão para acessar as imagens do link do json ou elas já não existem mais :/
        Picasso.
                with(InfoBeer.this).
                load(beer.getImage_url()).
                error(getDrawable(R.drawable.beerlogo)).
                into(((ImageView)findViewById(R.id.imageView2)));


        TextView name = (TextView)findViewById(R.id.name);
        TextView country = (TextView)findViewById(R.id.country);
        TextView category= (TextView)findViewById(R.id.category);
        TextView size= (TextView)findViewById(R.id.size);
        TextView type= (TextView)findViewById(R.id.type);
        TextView brewer= (TextView)findViewById(R.id.brewer);
        CardView cardView=(CardView)findViewById(R.id.card_view);

        name.setText(beer.name);
        country.setText("Country: " + beer.country);
        category.setText("Category: " + beer.category);
        size.setText("Size: " + beer.size);
        type.setText("Type: " + beer.type);
        brewer.setText("Brewer: " + beer.brewer);


    }
}
