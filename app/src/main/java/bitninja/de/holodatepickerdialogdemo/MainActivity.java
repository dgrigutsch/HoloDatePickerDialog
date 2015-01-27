package bitninja.de.holodatepickerdialogdemo;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import bitninja.de.holodatepickerdialog.datepicker.SpinnerDatePickerDialog;
import bitninja.de.holodatepickerdialog.datepicker.SpinnerDatePickerFragment;


public class MainActivity extends ActionBarActivity {

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        EditText editText;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            editText = (EditText) rootView.findViewById(R.id.editText);
            editText.setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onClick(View v) {

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
        }
    }
}
