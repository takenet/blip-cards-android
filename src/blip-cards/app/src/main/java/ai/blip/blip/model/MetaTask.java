package ai.blip.blip.model;

import android.os.AsyncTask;

/**
 * Created by dirceubelem on 12/09/17.
 */

public class MetaTask extends AsyncTask<String, Void, MetaTag> {

    @Override
    protected MetaTag doInBackground(String... voids) {

        MetaTag m = new MetaTag(voids[0]);
        return m;

    }
}
