package ai.blip.blip.ui;

import android.content.Context;

/**
 * Created by dirceubelem on 29/08/17.
 */

public final class BlipCard {

    private Context context;

    public BlipCard(Context context) {
        this.context = context;
    }

    public Builder left() {
        return Builder.left(context);
    }

    public Builder right() {
        return Builder.right(context);
    }


}
