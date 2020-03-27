package com.ece.soccert.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ece.soccert.R;
import com.ece.soccert.ui.stats.StatsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

   // private Button startendmatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //startendmatch = findViewById(R.id.start_end_match);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

   /* public void StartEndMatch(View v){
        Log.d("TAG", "StartEndMatch: BTN CLICK "+getString(R.string.endMatch));
        // startendmatch.setText("getString(R.string.endMatch)");
        startendmatch.setVisibility(View.GONE);
      /*  if (startendmatch.getText() == String.valueOf(R.string.startMatch)){
            startendmatch.setText(R.string.endMatch);
        }else{
            startendmatch.setText(R.string.startMatch);
        }*/
      //  Log.d("TAG", "StartEndMatch: BTN CLICK ");

   // }*/

}
