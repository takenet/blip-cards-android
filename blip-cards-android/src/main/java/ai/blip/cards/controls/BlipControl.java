package ai.blip.cards.controls;

import android.content.Context;
import android.widget.LinearLayout;

public class BlipControl extends LinearLayout {

    private Context context;

    public BlipControl(Context context) {
        super(context);
        this.context = context;
    }

    public float convertPixelsToDp(final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public float convertDpToPixel(final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
