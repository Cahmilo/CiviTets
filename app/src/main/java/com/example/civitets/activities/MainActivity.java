package com.example.civitets.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.civitets.R;
import com.example.civitets.clases.Offers;
import com.example.civitets.clases.Sites;
import com.example.civitets.interfaces.GetDataService;
import com.example.civitets.network.RetrofitClientInstance;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private static int LIMIT = 30;
    private static int RADIO = 30;
    private static String DISTANCE = "distance";
    private SearchView svMain;
    private Button btnSearch;
    private ProgressBar pgrbMain;
    private Offers offers;
    private Sites sites;
    private ArrayList<Offers> arrayOffers;
    private ArrayList<Sites> arraySites;
    Toolbar toolbar;
    private FusedLocationProviderClient client;
    private Double latitud = null;
    private Double longitud = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.AppName));

        svMain = (SearchView) findViewById(R.id.sv_main);
        btnSearch = (Button) findViewById(R.id.btn_search);
        pgrbMain = (ProgressBar) findViewById(R.id.pgrb_main);

        client = LocationServices.getFusedLocationProviderClient(this);
        setUpLocation();

        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    System.out.println(location.getLatitude());
                    System.out.println(location.getLongitude());
                }
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search = svMain.getQuery().toString();

                //Validate turn On/off GPS for get coordinates
                if (latitud == null || longitud == null) {
                    Toast.makeText(MainActivity.this, R.string.turnOnGPS, Toast.LENGTH_LONG).show();
                    return;
                }

                btnSearch.setVisibility(View.GONE);
                pgrbMain.setVisibility(View.VISIBLE);

                getInfoApi(search);

            }
        });
    }

    private void getInfoApi(String search) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        /*
        Bogota test parameters
        Call<JsonObject> call = service.getShearch("noticia", 50,"distance",50, 4.60971, -74.08175);
        Call<JsonObject> call = service.getShearch("ofertas", 1000,"distance",1000, 4.60971, -74.08175);
         */

        //call de service
        Call<JsonObject> call = service.getShearch(search, LIMIT, DISTANCE, RADIO, latitud, longitud);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonElement jelement = response.body().get("results");
                JsonArray jarray = null;

                if (jelement instanceof JsonArray) {
                    jarray = jelement.getAsJsonArray();
                } else {
                    return;
                }

                if (jarray.size() == 0 || jarray == null) {
                    Toast.makeText(MainActivity.this, R.string.thereIsNotResult, Toast.LENGTH_LONG).show();
                    btnSearch.setVisibility(View.VISIBLE);
                    pgrbMain.setVisibility(View.GONE);
                } else {
                    arrayOffers = new ArrayList<Offers>();
                    arraySites = new ArrayList<Sites>();
                    String typeKey = "";

                    for (int i = 0; i < jarray.size(); i++) {
                        JsonObject jsonObject = jarray.get(i).getAsJsonObject();
                        JsonElement jsonElementKey = jsonObject.get("type");
                        typeKey = jsonElementKey.toString();
                        //System.out.println(jsonObject);
                        System.out.println(typeKey);

                        if (typeKey.equals("\"Lugar\"")) {
                            /*
                            was not is possible found results with this key
                            */
                            JsonElement jsonElementName = jsonObject.get("name");
                            JsonElement jsonElementLat = jsonObject.getAsJsonObject("coordinates").get("lat");
                            JsonElement jsonElementLong = jsonObject.getAsJsonObject("coordinates").get("long");

                            sites = new Sites();
                            sites.setName(jsonElementName.toString());
                            sites.setUser_lat(Double.valueOf(jsonElementLat.toString()));
                            sites.setUser_lng(Double.valueOf(jsonElementLong.toString()));

                            arraySites.add(sites);

                        } else if (typeKey.equals("\"Noticia\"")) {

                            JsonElement jsonElementNoticia = jsonObject.get("name");
                            JsonElement jsonElementInfoNoticia = jsonObject.get("description");
                            JsonElement jsonElementImagen = jsonObject.get("image");

                            offers = new Offers();
                            offers.setName(jsonElementNoticia.toString());
                            offers.setInfo(jsonElementInfoNoticia.toString());
                            offers.setImagen(jsonElementImagen.toString());

                            arrayOffers.add(offers);

                        } else {
                            Toast.makeText(MainActivity.this, R.string.noNoticeAndPlaces, Toast.LENGTH_SHORT).show();
                        }
                    }

                    Intent intent = new Intent(MainActivity.this, TabsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("arraySites", arraySites);
                    intent.putExtra("arrayOffers", arrayOffers);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("ERROR");
                Toast.makeText(MainActivity.this, R.string.serviceError, Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());

                btnSearch.setVisibility(View.VISIBLE);
                pgrbMain.setVisibility(View.GONE);

            }
        });
    }

    @Override
    protected void onPostResume() {

        btnSearch.setVisibility(View.VISIBLE);
        pgrbMain.setVisibility(View.GONE);
        super.onPostResume();
    }

    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);

    }

}








