package bitninja.de.holodatepickerdialog.datepicker.date;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.text.format.Time;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.nineoldandroids.animation.Keyframe;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.util.Property;

import java.util.Calendar;

/**
 * Utility helper functions for time and date pickers.
 */
public class DateTimePickerUtils {
    public static final int MONDAY_BEFORE_JULIAN_EPOCH = Time.EPOCH_JULIAN_DAY - 3;
    public static final int PULSE_ANIMATOR_DURATION = 544;
    private static final Property<View, Float> sPropertyScaleX = new Property<View, Float>(Float.class, "scaleX") {
        @Override
        public Float get(View object) {
            return ViewCompat.getScaleX(object);
        }

        @Override
        public void set(View object, Float value) {
            ViewCompat.setScaleX(object, value);
        }
    };
    private static final Property<View, Float> sPropertyScaleY = new Property<View, Float>(Float.class, "scaleY") {
        @Override
        public Float get(View object) {
            return ViewCompat.getScaleY(object);
        }

        @Override
        public void set(View object, Float value) {
            ViewCompat.setScaleY(object, value);
        }
    };

    public static boolean isJellybeanOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Try to speak the specified text, for accessibility. Only available on JB or later.
     *
     * @param text Text to announce.
     */
    @SuppressLint("NewApi")
    public static void tryAccessibilityAnnounce(View view, CharSequence text) {
        if (isJellybeanOrLater() && view != null && text != null) {
            view.announceForAccessibility(text);
        }
    }

    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return (year % 4 == 0) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    /**
     * Returns the week since {@link android.text.format.Time#EPOCH_JULIAN_DAY} (Jan 1, 1970)
     * adjusted for first day of week.
     * <p/>
     * This takes a julian day and the week start day and calculates which
     * week since {@link android.text.format.Time#EPOCH_JULIAN_DAY} that day occurs in, starting
     * at 0. *Do not* use this to compute the ISO week number for the year.
     *
     * @param julianDay      The julian day to calculate the week number for
     * @param firstDayOfWeek Which week day is the first day of the week,
     *                       see {@link android.text.format.Time#SUNDAY}
     * @return Weeks since the epoch
     */
    public static int getWeeksSinceEpochFromJulianDay(int julianDay, int firstDayOfWeek) {
        int diff = Time.THURSDAY - firstDayOfWeek;
        if (diff < 0) {
            diff += 7;
        }
        int refDay = Time.EPOCH_JULIAN_DAY - diff;
        return (julianDay - refDay) / 7;
    }

    /**
     * Render an animator to pulsate a view in place.
     *
     * @param labelToAnimate the view to pulsate.
     * @return The animator object. Use .start() to begin.
     */
    public static ObjectAnimator getPulseAnimator(View labelToAnimate, float decreaseRatio, float increaseRatio) {

        Keyframe k0 = Keyframe.ofFloat(0f, 1f);
        Keyframe k1 = Keyframe.ofFloat(0.275f, decreaseRatio);
        Keyframe k2 = Keyframe.ofFloat(0.69f, increaseRatio);
        Keyframe k3 = Keyframe.ofFloat(1f, 1f);



        PropertyValuesHolder scaleX = PropertyValuesHolder.ofKeyframe(sPropertyScaleX, k0, k1, k2, k3);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofKeyframe(sPropertyScaleY, k0, k1, k2, k3);
        ObjectAnimator pulseAnimator =
                ObjectAnimator.ofPropertyValuesHolder(labelToAnimate, scaleX, scaleY);



        pulseAnimator.setDuration(PULSE_ANIMATOR_DURATION);
        return pulseAnimator;
    }
}