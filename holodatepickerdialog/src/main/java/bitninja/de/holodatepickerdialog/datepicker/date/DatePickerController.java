package bitninja.de.holodatepickerdialog.datepicker.date;


import bitninja.de.holodatepickerdialog.datepicker.CalendarDatePickerDialog;

public interface DatePickerController {

    void onYearSelected(int year);

    void onDayOfMonthSelected(int year, int month, int day);

    void registerOnDateChangedListener(CalendarDatePickerDialog.OnDateChangedListener listener);

    void unregisterOnDateChangedListener(CalendarDatePickerDialog.OnDateChangedListener listener);

    SimpleMonthAdapter.CalendarDay getSelectedDay();

    int getFirstDayOfWeek();

    int getMinYear();

    int getMaxYear();

    void tryVibrate();
}