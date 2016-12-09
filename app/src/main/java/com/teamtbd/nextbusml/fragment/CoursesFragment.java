package com.teamtbd.nextbusml.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.teamtbd.nextbusml.MainActivity;
import com.teamtbd.nextbusml.R;
import com.teamtbd.nextbusml.model.Course;
import com.teamtbd.nextbusml.model.CourseTime;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CoursesFragment extends Fragment {
    private ArrayList<Course> courses;
    private ArrayAdapter<Course> adapter;
    private ListView coursesListView;
    private FloatingActionButton addButton;
    private OnCourseInteractionListener mListener;

    public CoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        coursesListView = (ListView) view.findViewById(R.id.courses_list_view);
        addButton = (FloatingActionButton) view.findViewById(R.id.add_button);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
            }
        });



        try {
            FileInputStream fis = getActivity().openFileInput(MainActivity.COURSES_FILE);
            ObjectInputStream in = new ObjectInputStream(fis);
            courses = (ArrayList<Course>) in.readObject();
            in.close();
            fis.close();
        } catch (ClassNotFoundException|IOException e){
            e.printStackTrace();
        }

        if (courses == null) {
            courses = new ArrayList<>();
        }
        adapter = new CoursesArrayAdapter();
        coursesListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }




    private void addCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Course");

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        float sizeInDp = getResources().getDimension(R.dimen.activity_horizontal_margin)/2;

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp*scale + 0.5f);

        layout.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

        final EditText title, startTime, endTime;

        title = new EditText(getActivity());
        title.setInputType(InputType.TYPE_CLASS_TEXT);
        title.setHint(R.string.title_hint);

        startTime = new EditText(getActivity());
        startTime.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
        startTime.setHint(R.string.start_time_hint);


        endTime = new EditText(getActivity());
        endTime.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
        endTime.setHint(R.string.end_time_hint);

        ArrayAdapter<CharSequence> campusAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.campus_array, android.R.layout.simple_spinner_item);
        campusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner campusSpinner = new Spinner(getActivity());
        campusSpinner.setAdapter(campusAdapter);

        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.day_array, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner daySpinner = new Spinner(getActivity());
        daySpinner.setAdapter(dayAdapter);

        layout.addView(title);
        layout.addView(daySpinner);
        layout.addView(startTime);
        layout.addView(endTime);
        layout.addView(campusSpinner);

        builder.setView(layout);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                String titleText = title.getText().toString();
                int startTimeHour = Integer.parseInt(startTime.getText().toString().split(":")[0]);
                int startTimeMinute = Integer.parseInt(startTime.getText().toString().split(":")[1]);
                int endTimeHour = Integer.parseInt(endTime.getText().toString().split(":")[0]);
                int endTimeMinute = Integer.parseInt(endTime.getText().toString().split(":")[1]);

                CourseTime startCourseTime = new CourseTime(startTimeHour, startTimeMinute);
                CourseTime endCourseTime = new CourseTime(endTimeHour, endTimeMinute);

                String dayText = daySpinner.getSelectedItem().toString();
                String campusText = campusSpinner.getSelectedItem().toString();

                Course c = new Course(titleText, startCourseTime, endCourseTime, campusText, dayText);
                courses.add(c);
                adapter.notifyDataSetChanged();
                try{
                    FileOutputStream fos= getActivity().openFileOutput(MainActivity.COURSES_FILE, Context.MODE_PRIVATE);
                    ObjectOutputStream oos= new ObjectOutputStream(fos);
                    oos.writeObject(courses);
                    oos.close();
                    fos.close();
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
                mListener.addAlarm(c);

            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog = builder.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textWatch(title.getText().toString(), startTime.getText().toString(), endTime.getText().toString())) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // do text watchers for starttime and endtime
        startTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textWatch(title.getText().toString(), startTime.getText().toString(), endTime.getText().toString())) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        endTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textWatch(title.getText().toString(), startTime.getText().toString(), endTime.getText().toString())) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean textWatch(String title, String start, String end) {
        Pattern p = Pattern.compile("(0?[0-9]|1[0-9]|2[01234]):(0?[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])");
        Matcher matcherStart = p.matcher(start);
        Matcher matcherEnd = p.matcher(end);
        return !title.isEmpty() && matcherStart.matches() && matcherEnd.matches();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCourseInteractionListener) {
            mListener = (OnCourseInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCourseInteractionListener {
        void addAlarm(Course course);
        void refreshAlarms();
    }

    private class CoursesArrayAdapter extends ArrayAdapter<Course> {
        public CoursesArrayAdapter() {
            super(getActivity(), R.layout.courses_list_item, courses);
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null)
                view = getActivity().getLayoutInflater().inflate(R.layout.courses_list_item, parent, false);

            Course course = courses.get(position);

            TextView title = (TextView) view.findViewById(R.id.course_title_text_view);
            TextView courseTime = (TextView) view.findViewById(R.id.course_time_text_view);
            TextView campus = (TextView) view.findViewById(R.id.campus_text_view);

            String day;
            switch(course.getDay()) {
                case Calendar.MONDAY: day = "Monday"; break;
                case Calendar.TUESDAY: day = "Tuesday"; break;
                case Calendar.WEDNESDAY: day = "Wednesday"; break;
                case Calendar.THURSDAY: day = "Thursday"; break;
                case Calendar.FRIDAY: day = "Friday"; break;
                default: day = "Monday"; break;
            }

            title.setText(course.getTitle());
            courseTime.setText(day + ": " + course.getCourseTime());
            campus.setText(course.getCampus().getCampusValue());

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    courses.remove(position);
                    try{
                        FileOutputStream fos= getActivity().openFileOutput(MainActivity.COURSES_FILE, Context.MODE_PRIVATE);
                        ObjectOutputStream oos= new ObjectOutputStream(fos);
                        oos.writeObject(courses);
                        oos.close();
                        fos.close();
                    }catch(IOException ioe){
                        ioe.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                    mListener.refreshAlarms();
                    return true;
                }
            });

            return view;
        }
    }
}
