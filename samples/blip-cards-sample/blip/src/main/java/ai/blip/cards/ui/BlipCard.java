package ai.blip.cards.ui;

import android.content.Context;

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
