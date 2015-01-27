package bitninja.de.holodatepickerdialog.datepicker;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SpinnerDatePickerFragment extends DialogFragment {

    static final DateFormat DATE_FORMAT = new SimpleDateFormat("d/M/y");
    CalendarDatePickerDialog.OnDateSetListener onDateSetListener;
    SpinnerDatePickerDialog datePickerDialog;
    Calendar mMaxDate;
    Calendar mMinDate;

    public SpinnerDatePickerFragment() {

    }

    public static SpinnerDatePickerFragment newInstance(CalendarDatePickerDialog.OnDateSetListener onDateSetListener) {
        SpinnerDatePickerFragment fragment = new SpinnerDatePickerFragment();
        fragment.onDateSetListener = onDateSetListener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar cal = Calendar.getInstance();
        datePickerDialog = new SpinnerDatePickerDialog(
                getActivity(),
                new SpinnerDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        onDateSetListener.onDateSet(SpinnerDatePickerFragment.this, year, monthOfYear, dayOfMonth);
                    }
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        if(mMaxDate != null && mMinDate != null){
            setYearRange(mMinDate,mMaxDate);
        }

        return datePickerDialog;
    }

    public void setYearRange(Calendar startYear, Calendar endYear) {
        this.mMinDate = startYear;
        this.mMaxDate = endYear;
        if(datePickerDialog != null) {
            datePickerDialog.setYearRange(
                    startYear.getTimeInMillis(),
                    endYear.getTimeInMillis());
        }
    }
}