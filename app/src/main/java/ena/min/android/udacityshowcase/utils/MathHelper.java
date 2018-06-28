package ena.min.android.udacityshowcase.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class MathHelper {
    public static  int convertDpToPx(Context context, int dp) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
