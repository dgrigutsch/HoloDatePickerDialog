package bitninja.de.holodatepickerdialogdemo;

import android.app.DatePickerDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bitninja.de.holodatepickerdialog.datepicker.CalendarDatePickerDialog;
import bitninja.de.holodatepickerdialog.datepicker.CalendarDatePickerFragment;
import bitninja.de.holodatepickerdialog.datepicker.SpinnerDatePickerDialog;
import bitninja.de.holodatepickerdialog.datepicker.SpinnerDatePickerFragment;


public class MainActivity extends ActionBarActivity {

    private boolean isLight = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_change_theme) {
            this.isLight = !isLight;
            changeTheme(isLight);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeTheme(boolean isLight) {
        setTheme(isLight ? R.style.AppTheme : R.style.AppTheme_Dark);
        getApplication().setTheme(isLight ? R.style.AppTheme : R.style.AppTheme_Dark);
    }

    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        EditText editText;
        EditText editText2;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            editText = (EditText) rootView.findViewById(R.id.editText);
            editText2 = (EditText) rootView.findViewById(R.id.editText2);
            editText.setOnClickListener(this);
            editText2.setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.editText){

                SpinnerDatePickerFragment datePickerFragment = SpinnerDatePickerFragment
                        .newInstance(new CalendarDatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                try {
                                    Calendar c = Calendar.getInstance();
                                    c.set(year, monthOfYear, dayOfMonth);
                                    SimpleDateFormat sdf_a = new SimpleDateFormat("yyyy-MM-dd");
                                    DateFormat sdf_b = SimpleDateFormat.getDateInstance();
                                    String mDateString = sdf_b.format(c.getTime());
                                    editText.setText(mDateString);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                datePickerFragment.show(((FragmentActivity) getActivity())
                        .getSupportFragmentManager(), "DATEPICKER");

            }else if(v.getId() == R.id.editText2){

                CalendarDatePickerFragment datePickerFragment = CalendarDatePickerFragment
                        .newInstance(new CalendarDatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                try {
                                    Calendar c = Calendar.getInstance();
                                    c.set(year, monthOfYear, dayOfMonth);
                                    SimpleDateFormat sdf_a = new SimpleDateFormat("yyyy-MM-dd");
                                    DateFormat sdf_b = SimpleDateFormat.getDateInstance();
                                    String mDateString = sdf_b.format(c.getTime());
                                    editText2.setText(mDateString);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                datePickerFragment.show(((FragmentActivity) getActivity())
                        .getSupportFragmentManager(), "DATEPICKER2");

            }
        }
    }
}
