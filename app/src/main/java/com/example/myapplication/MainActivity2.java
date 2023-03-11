package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity implements PizzaMenuFragment.OnItemSelectedListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Log.d("DEBUG", getResources().getConfiguration().orientation + "");

        if (savedInstanceState == null) {
            PizzaMenuFragment firstFragment = new PizzaMenuFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.flContainer, firstFragment);
            ft.commit();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            PizzaDetailFragment secondFragment = new PizzaDetailFragment();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            secondFragment.setArguments(args);
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.add(R.id.flContainer2, secondFragment);
            ft2.commit();
        }
    }

    public void onPizzaItemSelected(int position) {
        Toast.makeText(this, "Called By Fragment A: position - " + position, Toast.LENGTH_SHORT).show();


        PizzaDetailFragment secondFragment = new PizzaDetailFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        secondFragment.setArguments(args);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer2, secondFragment) // replace flContainer

                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }
    }
}