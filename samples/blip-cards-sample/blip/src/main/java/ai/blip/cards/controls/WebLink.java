package ai.blip.cards.controls;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ai.blip.cards.R;
import ai.blip.cards.model.MetaTag;
import ai.blip.cards.model.MetaTask;
import ai.blip.cards.ui.Builder;

public class WebLink extends BlipControl implements View.OnClickListener {

    private LinearLayout layoutName;
    private LinearLayout boxWebData;
    private String from;
    private String date;
    private TextView text;
    private TextView dateTime;
    private TextView name;

    private TextView title;
    private TextView description;
    private ImageView image;

    private LinearLayout weblink;

    private org.limeprotocol.messaging.contents.WebLink document;

    public void findItens() {
        this.layoutName = findViewById(R.id.layoutName);
        this.text = findViewById(R.id.text);
        this.dateTime = findViewById(R.id.dateTime);
        this.name = findViewById(R.id.name);

        this.title = findViewById(R.id.title);
        this.description = findViewById(R.id.description);
        this.image = findViewById(R.id.image);

        this.boxWebData = findViewById(R.id.boxWebData);

        this.weblink = findViewById(R.id.weblink);
        this.weblink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Uri uri = Uri.parse(document.getUri().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        getContext().startActivity(intent);

    }

    public void loadItens() {

        if (from == null) {
            this.layoutName.setVisibility(GONE);
        } else {
            this.layoutName.setVisibility(VISIBLE);
            this.name.setText(from);

            if (date != null) {
                this.dateTime.setText(date);
            }
        }

        text.setText(document.getText());

        MetaTask c = new MetaTask() {

            @Override
            protected void onPostExecute(MetaTag m) {

                boolean show = false;

                if (m.getTitle() != null) {
                    title.setText(m.getTitle());
                    show = true;
                }
                if (m.getDescription() != null) {
                    description.setText(m.getDescription());
                    show = true;
                }
                if (m.getImage() != null) {
                    Glide.with(getContext()).load(m.getImage()).into(image);
                    show = true;
                }

                if (show) {
                    boxWebData.setVisibility(VISIBLE);
                } else {
                    boxWebData.setVisibility(GONE);
                }

            }

        };

        c.execute(document.getUri().toString());


    }

    public WebLink(Context context, String from, String dateTime, org.limeprotocol.messaging.contents.WebLink document, Builder.SIDE type) {
        super(context);

        this.from = from;

        this.date = dateTime;

        this.document = document;

        if (type == Builder.SIDE.LEFT) {
            LayoutInflater.from(context).inflate(R.layout.weblink_left, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.weblink_right, this);
        }

        findItens();

        loadItens();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins((int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15));

        setLayoutParams(layoutParams);

    }


}
