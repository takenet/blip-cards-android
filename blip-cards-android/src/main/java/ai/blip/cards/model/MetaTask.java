package ai.blip.cards.model;

import android.os.AsyncTask;

public class MetaTask extends AsyncTask<String, Void, MetaTag> {

    @Override
    protected MetaTag doInBackground(String... voids) {

        MetaTag m = new MetaTag(voids[0]);
        return m;

    }
}
