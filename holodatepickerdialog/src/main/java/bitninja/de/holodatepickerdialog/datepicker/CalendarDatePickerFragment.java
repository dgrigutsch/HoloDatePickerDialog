package bitninja.de.holodatepickerdialog.datepicker;


import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarDatePickerFragment extends CalendarDatePickerDialog {

    static final DateFormat DATE_FORMAT = new SimpleDateFormat("d/m/y");
    CalendarDatePickerDialog.OnDateSetListener onDateSetListener;

    public static CalendarDatePickerFragment newInstance(CalendarDatePickerDialog.OnDateSetListener onDateSetListener) {
        CalendarDatePickerFragment fragment = new CalendarDatePickerFragment();
        fragment.onDateSetListener = onDateSetListener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(onDateSetListener, 1996, Calendar.APRIL, 14);
    }
//    @Override
//    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, monthOfYear);
//        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        Toast.makeText(getActivity(), "Set date: " + DATE_FORMAT.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
//    }
}