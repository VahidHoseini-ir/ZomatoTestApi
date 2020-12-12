package ir.vahidhoseini.testtraining1.view;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.adapter.ResturantViewHolder;
import ir.vahidhoseini.testtraining1.databinding.ActivityDetailResturantBinding;
import ir.vahidhoseini.testtraining1.databinding.ActivityMainBinding;
import ir.vahidhoseini.testtraining1.databinding.ContentScrollingBinding;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Location;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurant;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class DetailResturantActivity extends AppCompatActivity {

    private ActivityDetailResturantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailResturantBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
        if (getIntent().getExtras() != null) {
            Restaurant restaurants = getIntent().getExtras().getParcelable("Restaurants");
            Location location = getIntent().getExtras().getParcelable("Location");


            binding.toolbarLayout.setTitle(restaurants.getName());
            if (!restaurants.getFeatured_image().isEmpty()) {
                Picasso.get().load(restaurants.getFeatured_image())
                        .placeholder(R.drawable.returant_placeholder)
                        .error(R.drawable.returant_placeholder)
                        .into(binding.image);
            } else {
                Picasso.get().load(R.drawable.returant_placeholder).into(binding.image);
            }

            binding.include.averageCost.setText("Average cost for two  :" + restaurants.getAverage_cost_for_two() + " " + restaurants.getCurrency());
            binding.include.city.setText("City :"+location.getCity());
            binding.include.locality.setText("Locality :"+location.getLocality());
            binding.include.address.setText("Address :"+location.getAddress());
            binding.include.phoneNumber.setText("Phone Numbers :"+restaurants.getPhone_numbers());
        } else {
            finish();
        }


    }
}