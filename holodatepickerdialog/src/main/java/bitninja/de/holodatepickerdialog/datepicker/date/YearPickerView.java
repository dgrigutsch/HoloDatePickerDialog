package bitninja.de.holodatepickerdialog.datepicker.date;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bitninja.de.holodatepickerdialog.R;
import bitninja.de.holodatepickerdialog.datepicker.CalendarDatePickerDialog;

/**
 * Displays a selectable list of years.
 */
public class YearPickerView extends GridView implements OnItemClickListener, CalendarDatePickerDialog.OnDateChangedListener {
    private final DatePickerController mController;
    private YearAdapter mAdapter;
    private TextViewWithCircularIndicator mSelectedView;

    /**
     * @param context
     */
    public YearPickerView(Context context, DatePickerController controller) {
        super(context);
        mController = controller;
        mController.registerOnDateChangedListener(this);
        ViewGroup.LayoutParams frame = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        setLayoutParams(frame);
        Resources res = context.getResources();
        setVerticalFadingEdgeEnabled(true);
        init(context);
        setOnItemClickListener(this);
        setSelector(new StateListDrawable());
        onDateChanged();
        setNumColumns(res.getInteger(R.integer.date_year_view_num_columns));
        setGravity(Gravity.CENTER);
    }

    private void init(Context context) {
        ArrayList<String> years = new ArrayList<String>();
        for (int year = mController.getMinYear(); year <= mController.getMaxYear(); year++) {
            years.add(String.format("%d", year));
        }
        mAdapter = new YearAdapter(context, R.layout.year_label_text_view, years);
        setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mController.tryVibrate();
        TextViewWithCircularIndicator clickedView = (TextViewWithCircularIndicator) view;
        if (clickedView != null) {
            if (clickedView != mSelectedView) {
                if (mSelectedView != null) {
                    mSelectedView.drawIndicator(false);
                    mSelectedView.requestLayout();
                }
                clickedView.drawIndicator(true);
                clickedView.requestLayout();
                mSelectedView = clickedView;
            }
            mController.onYearSelected(getYearFromTextView(clickedView));
            mAdapter.notifyDataSetChanged();
        }
    }

    private int getYearFromTextView(TextView view) {
        return Integer.valueOf(view.getText().toString());
    }

    public void postSetSelectionCentered(final int position) {
        postSetSelection(position);
    }

    public void postSetSelection(final int position) {
        post(new Runnable() {
            @Override
            public void run() {
                setSelection(position);
                requestLayout();
            }
        });
    }

    public int getFirstPositionOffset() {
        final View firstChild = getChildAt(0);
        if (firstChild == null) {
            return 0;
        }
        return firstChild.getTop();
    }

    @Override
    public void onDateChanged() {
        mAdapter.notifyDataSetChanged();
        postSetSelectionCentered(mController.getSelectedDay().year - mController.getMinYear());
    }

    @Override
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            event.setFromIndex(0);
            event.setToIndex(0);
        }
    }

    private class YearAdapter extends ArrayAdapter<String> {
        public YearAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextViewWithCircularIndicator v = (TextViewWithCircularIndicator)
                    super.getView(position, convertView, parent);
            v.requestLayout();
            int year = getYearFromTextView(v);
            boolean selected = mController.getSelectedDay().year == year;
            v.drawIndicator(selected);
            if (selected) {
                mSelectedView = v;
            }
            return v;
        }
    }
}