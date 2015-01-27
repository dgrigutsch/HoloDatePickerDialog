package bitninja.de.holodatepickerdialog.datepicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;


import java.util.Calendar;

import bitninja.de.holodatepickerdialog.R;

public class SpinnerDatePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, DatePicker.OnDateChangedListener {

    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    private final Calendar mCalendar;
    private final OnDateSetListener mCallBack;
    private final DatePicker mDatePicker;
    private boolean mTitleNeedsUpdate = true;

    private static final int DEFAULT_START_YEAR = 1900;
    private long mMinYear = DEFAULT_START_YEAR;
    private static final int DEFAULT_END_YEAR = 2100;
    private long mMaxYear = DEFAULT_END_YEAR;

    public SpinnerDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, theme);
        mCallBack = callBack;
        mCalendar = Calendar.getInstance();
        setButton(DialogInterface.BUTTON_POSITIVE,
                getContext().getText(R.string.date_time_done), (OnClickListener) this);
        setButton(DialogInterface.BUTTON_NEGATIVE,
                getContext().getText(android.R.string.cancel), (OnClickListener) this);
        setIcon(0);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.date_picker_dialog, null);
        setView(view);
        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        mDatePicker.init(year, monthOfYear, dayOfMonth, this);
        updateTitle(year, monthOfYear, dayOfMonth);
    }

    public SpinnerDatePickerDialog(Context context, OnDateSetListener callBack,
                                   int year, int monthOfYear, int dayOfMonth) {
        this(context, 0, callBack, year, monthOfYear, dayOfMonth);
    }

    public DatePicker getDatePicker() {
        return mDatePicker;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            tryNotifyDateSet();
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        mDatePicker.init(year, month, day, this);
        updateTitle(year, month, day);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int year = savedInstanceState.getInt(SpinnerDatePickerDialog.YEAR);
        int month = savedInstanceState.getInt(SpinnerDatePickerDialog.MONTH);
        int day = savedInstanceState.getInt(SpinnerDatePickerDialog.DAY);
        mDatePicker.init(year, month, day, this);
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(SpinnerDatePickerDialog.YEAR, mDatePicker.getYear());
        state.putInt(SpinnerDatePickerDialog.MONTH, mDatePicker.getMonth());
        state.putInt(SpinnerDatePickerDialog.DAY, mDatePicker.getDayOfMonth());
        return state;
    }

    private void tryNotifyDateSet() {
        if (mCallBack != null) {
            mDatePicker.clearFocus();
            mCallBack.onDateSet(mDatePicker, mDatePicker.getYear(),
                    mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        }
    }

    public void updateDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker.updateDate(year, monthOfYear, dayOfMonth);
    }

    private void updateTitle(int year, int month, int day) {
        if (!mDatePicker.getCalendarViewShown()) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            String title = DateUtils.formatDateTime(getContext(),
                    mCalendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE
                            | DateUtils.FORMAT_SHOW_WEEKDAY
                            | DateUtils.FORMAT_SHOW_YEAR
                            | DateUtils.FORMAT_ABBREV_MONTH
                            | DateUtils.FORMAT_ABBREV_WEEKDAY);
            setTitle(title);
            mTitleNeedsUpdate = true;
        } else {
            if (mTitleNeedsUpdate) {
                mTitleNeedsUpdate = false;
                setTitle(R.string.date_picker_dialog_title);
            }
        }
    }

    public interface OnDateSetListener {
        void onDateSet(DatePicker view, int year, int monthOfYear,
                       int dayOfMonth);
    }

    public void setYearRange(long startYear, long endYear) {
        if (endYear <= startYear) {
            throw new IllegalArgumentException("Year end must be larger than year start");
        }
        mMinYear = startYear;
        mMaxYear = endYear;
        if (mDatePicker != null) {
            mDatePicker.setMinDate(mMinYear);
            mDatePicker.setMaxDate(mMaxYear);
        }
    }

    public void setDate(int year, int month, int day) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        if (mDatePicker != null) {
           updateDate(year,month,day);
           updateTitle(year,month,day);
        }
    }
}
