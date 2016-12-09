package com.teamtbd.nextbusml;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.teamtbd.nextbusml.fragment.CoursesFragment;
import com.teamtbd.nextbusml.fragment.StopsFragment;
import com.teamtbd.nextbusml.model.Campus;
import com.teamtbd.nextbusml.model.Course;
import com.teamtbd.nextbusml.notification.NotificationService;
import com.teamtbd.nextbusml.notification.ReminderNotification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements CoursesFragment.OnCourseInteractionListener, LocationListener {

    public static final String COURSES_FILE = "COURSES_LIST.ser";
    public static final String BASE_URL = "http://runextbus.herokuapp.com/route/";

    public static final double BUSCH_MAX_LONG = -74.450765;
    public static final double BUSCH_MIN_LONG = -74.478908;
    public static final double BUSCH_MAX_LAT = 40.526724;
    public static final double BUSCH_MIN_LAT = 40.511323;

    public static final double LIVVY_MAX_LONG = -74.430871;
    public static final double LIVVY_MIN_LONG = -74.44651;
    public static final double LIVVY_MAX_LAT = 40.527227;
    public static final double LIVVY_MIN_LAT = 40.518376;

    public static final double COOK_MAX_LONG = -74.427082;
    public static final double COOK_MIN_LONG = -74.448313;
    public static final double COOK_MAX_LAT = 40.476597;
    public static final double COOK_MIN_LAT = 40.475409;

    public static final double CAC_MAX_LONG = -74.441745;
    public static final double CAC_MIN_LONG = -74.460306;
    public static final double CAC_MAX_LAT = 40.505617;
    public static final double CAC_MIN_LAT = 40.492795;

    public static final String A_BUS = "a";
    public static final String B_BUS = "b";
    public static final String EE_BUS = "ee";
    public static final String F_BUS = "f";
    public static final String H_BUS = "h";
    public static final String LX_BUS = "lx";
    public static final String REXB_BUS = "rexb";
    public static final String REXL_BUS = "rexl";

    public static final String BUS_KEY = "bus";
    public static final String CAMPUS_KEY = "campus";

    private LocationManager mLocationManager;
    private int notif_id = 1;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        startService(new Intent(MainActivity.this, NotificationService.class));

        requestLocation();

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(this, new String[] {
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION },
//                    1);
//        }
//        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        if(location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
//            // Do something with the recent location fix
//            //  otherwise wait for the update below
//        }
//        else {
//            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        }
//        double current_latitude = location.getLatitude();
//        double current_longitude = location.getLongitude();
//
//        Log.d("MAINACTIVITY", current_latitude+":"+current_longitude);

        //showStops();
    }

    private void requestLocation() {
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location l) {
                location = l;
                Log.d("Location Changes", location.toString());
                showStops();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Status Changed", String.valueOf(status));
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Provider Enabled", provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Provider Disabled", provider);
            }
        };

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        // This is the Best And IMPORTANT part
        Looper looper = null;

        // Now create a location manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);
            return;
        }
        locationManager.requestSingleUpdate(criteria, locationListener, looper);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                requestLocation();
            } else {
                // permission denied
            }
        }
    }

    private void showStops() {
        Campus currentCampus = findCampus(location);
        if (currentCampus == null) {
            // not on a campus...
            Log.d("MAINACTIVITY", "Current campus is null");
            return;
        }
        Log.d("MAINACTIVITY", currentCampus.getCampusValue());


        // get next course
        Campus nextCampus = findNextCampus();

        String bus = findBusFromCampuses(currentCampus, nextCampus);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        StopsFragment newFragment = new StopsFragment();

        Bundle bundle = new Bundle();
        // bundle.putString(BUS_KEY, bus);
        bundle.putString(BUS_KEY, B_BUS);
        bundle.putSerializable(CAMPUS_KEY, currentCampus);

        newFragment.setArguments(bundle);

        transaction.replace(R.id.main_fragment_container, newFragment);

        // Commit the transaction
        transaction.commit();
    }

    private void showCourses() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        CoursesFragment newFragment = new CoursesFragment();

        transaction.replace(R.id.main_fragment_container, newFragment);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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



    @Override
    public void addAlarm(Course course) {
        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Log.d("MAIN", cur_cal.getTime().toString() + " pls");

        int day = course.getDay();

        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone("est"));
        cal.set(Calendar.YEAR, 2016);
        Log.d("MAIN", course.getStartTime().getHour() + " pls");
        cal.set(Calendar.HOUR, course.getStartTime().getHour());
        cal.set(Calendar.MINUTE, course.getStartTime().getMinute());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Log.d("MAIN", day + "");
        Log.d("MAIN", cur_cal.get(Calendar.DAY_OF_WEEK) + "");
        cal.set(Calendar.DAY_OF_WEEK, day);
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
        cal.set(Calendar.WEEK_OF_MONTH, cur_cal.get(Calendar.WEEK_OF_MONTH));
        cal.add(Calendar.HOUR, -7);
        cal.add(Calendar.MINUTE, -40);
        if (day < cur_cal.get(Calendar.DAY_OF_WEEK)) {
            cal.add(Calendar.HOUR, 24*7);
        }
        Log.d("ALARM", cal.getTime().toString());
        Log.d("ALARM", cal.getTimeInMillis() + "");
        Intent intent = new Intent("alarmy");//new Intent(MainActivity.this, ReminderNotification.class);
        Log.d("ALARM","Creating notification with ID = " + notif_id);
        intent.putExtra(ReminderNotification.NOTIFICATION_ID, notif_id);
        Log.d("ALARM","notificiation id is now = " + notif_id);
        intent.putExtra(ReminderNotification.NOTIFICATION, course.getTitle() + " is starting soon!");
        PendingIntent pintent = PendingIntent.getBroadcast(getApplicationContext(), notif_id, intent, 0);
        notif_id++;
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pintent);
        Log.d("ALARM","done");

        /*
         <receiver android:process=":remote" android:name=".notification.ReminderNotification">
            <intent-filter>
                <action android:name="alarmy"/>
            </intent-filter>
        </receiver>
         */

    }

    @Override
    public void refreshAlarms() {
        // delete all notifications
        NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();

        // reschedule all notifications based off courses
        ArrayList<Course> courses = getCoursesFromFile();
        for (Course course: courses) {
            addAlarm(course);
        }
    }

    private ArrayList<Course> getCoursesFromFile() {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(MainActivity.COURSES_FILE);
            ObjectInputStream in = new ObjectInputStream(fis);
            courses = (ArrayList<Course>) in.readObject();
            in.close();
            fis.close();
        } catch (ClassNotFoundException|IOException e){
            e.printStackTrace();
        }
        return courses;
    }

    private Campus findCampus(Location location) {
        double longitude, latitude;
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        // check if in busch
        if (BUSCH_MIN_LONG <= longitude && longitude <= BUSCH_MAX_LONG
                && BUSCH_MIN_LAT <= latitude && latitude <= BUSCH_MAX_LAT) {
            return Campus.BUSCH;
        }
        else if (LIVVY_MIN_LONG <= longitude && longitude <= LIVVY_MAX_LONG
                && LIVVY_MIN_LAT <= latitude && latitude <= LIVVY_MAX_LAT) {
            return Campus.LIVINGSTON;
        }
        else if (COOK_MIN_LONG <= longitude && longitude <= COOK_MAX_LONG
                && COOK_MIN_LAT <= latitude && latitude <= COOK_MAX_LAT) {
            return Campus.COOK;
        }
        else if (CAC_MIN_LONG <= longitude && longitude <= CAC_MAX_LONG
                && CAC_MIN_LAT <= latitude && latitude <= CAC_MAX_LAT) {
            return Campus.COLLEGE_AVE;
        }
        else {
            // no campus
            return null;
        }
    }

    private Campus findNextCampus() {
        // get next class;
        Course nearestCourse = getNearestCourse(getCoursesFromFile());

        //TODO
        // get current hour, minutes, and days
        Calendar cal = new GregorianCalendar();
        return null;
    }

    private Course getNearestCourse(ArrayList<Course> courses) {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMin = calendar.get(Calendar.MINUTE);
        int currentTime = curHour*60 + curMin;
        int closestTime = 0;
        Course newCourse = null;

        for(int i = 0; i < courses.size(); i++){
            Course nextCourse = courses.get(i);

           if  (nextCourse.getDay() == day){
               int hour = nextCourse.getStartTime().getHour();
               int min = nextCourse.getStartTime().getMinute();
               int courseTime = hour*60 + min;
               if (courseTime < currentTime && courseTime > closestTime){
                       closestTime = courseTime;
                       newCourse = nextCourse;
               }
           }
        }


       // Course nextCourse = courses.get(0);
        return newCourse;
    }

    private String findBusFromCampuses(Campus currentCampus, Campus nextCampus) {
        if ((currentCampus == Campus.BUSCH && nextCampus == Campus.LIVINGSTON)
                || (currentCampus == Campus.LIVINGSTON && nextCampus == Campus.BUSCH)) {
            return B_BUS;
        }
        else if ((currentCampus == Campus.BUSCH && nextCampus == Campus.COLLEGE_AVE)
                || (currentCampus == Campus.COLLEGE_AVE && nextCampus == Campus.BUSCH)) {
            return A_BUS;
        }
        else if ((currentCampus == Campus.BUSCH && nextCampus == Campus.COOK)
                || (currentCampus == Campus.COOK && nextCampus == Campus.BUSCH)) {
            return REXB_BUS;
        }
        else if ((currentCampus == Campus.LIVINGSTON && nextCampus == Campus.COLLEGE_AVE)
                || (currentCampus == Campus.COLLEGE_AVE && nextCampus == Campus.LIVINGSTON)) {
            return LX_BUS;
        }
        else if ((currentCampus == Campus.LIVINGSTON && nextCampus == Campus.COOK)
                || (currentCampus == Campus.COOK && nextCampus == Campus.LIVINGSTON)) {
            return REXL_BUS;
        }
        else if ((currentCampus == Campus.COLLEGE_AVE && nextCampus == Campus.COOK)
                || (currentCampus == Campus.COOK && nextCampus == Campus.COLLEGE_AVE)) {
            return F_BUS;
        }
        else {
            return F_BUS;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
