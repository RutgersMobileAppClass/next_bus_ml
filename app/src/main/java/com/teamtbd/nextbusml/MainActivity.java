package com.teamtbd.nextbusml;

import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void showServerDatabase() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            StopsFragment newFragment = new StopsFragment();

            transaction.replace(R.id.database_fragment_container, newFragment);

            // Commit the transaction
            transaction.commit();
        }
    }

    private void showLocalDatabase() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            CoursesFragment newFragment = new CoursesFragment();

            transaction.replace(R.id.database_fragment_container, newFragment);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.database_menu, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            switch (item.getItemId()) {
                case R.id.stops:
                    showStops();
                    return true;
                case R.id.courses:
                    showCourses();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return false;
    }
}
