package bitninja.de.holodatepickerdialog.datepicker.date;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;

import bitninja.de.holodatepickerdialog.R;

/**
 * A text view which, when pressed or activated, displays a blue circle around the text.
 */
public class TextViewWithCircularIndicator extends TextView {
    private static final int SELECTED_CIRCLE_ALPHA = 120;
    private final String mItemIsSelectedText;
    Paint mCirclePaint = new Paint();
    private boolean mDrawCircle;

    public TextViewWithCircularIndicator(Context context) {
        this(context, null);
    }

    public TextViewWithCircularIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dateTimePickerStyle);
    }

    public TextViewWithCircularIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DateTimePicker, defStyle, R.style.Holo_DateTimePicker);
        mCirclePaint.setColor(a.getColor(R.styleable.DateTimePicker_dateYearSelectorColor, 0));
        a.recycle();
        mCirclePaint.setFakeBoldText(true);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setTextAlign(Align.CENTER);
        mCirclePaint.setStyle(Style.FILL);
        mCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);
        mItemIsSelectedText = context.getResources().getString(R.string.item_is_selected);
        init();
    }

    private void init() {
    }

    public void drawIndicator(boolean drawCircle) {
        mDrawCircle = drawCircle;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mDrawCircle) {
            final int width = getWidth();
            final int height = getHeight();
            int radius = Math.min(width, height) / 2;
            canvas.drawCircle(width / 2, height / 2, radius, mCirclePaint);
        }
        super.onDraw(canvas);
    }

    @Override
    public CharSequence getContentDescription() {
        CharSequence itemText = getText();
        if (mDrawCircle) {
            return String.format(mItemIsSelectedText, itemText);
        } else {
            return itemText;
        }
    }
}
