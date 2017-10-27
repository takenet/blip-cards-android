package ai.blip.cards.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.limeprotocol.messaging.contents.PlainText;

import ai.blip.cards.R;
import ai.blip.cards.ui.Builder;

public class Text extends BlipControl {

    private LinearLayout layoutName;
    private String from;
    private String date;
    private TextView text;
    private TextView dateTime;
    private TextView name;

    private PlainText document;

    public void findItems() {
        this.layoutName = findViewById(R.id.layoutName);
        this.text = findViewById(R.id.text);
        this.dateTime = findViewById(R.id.dateTime);
        this.name = findViewById(R.id.name);
    }

    public void loadItems() {

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

    }

    public Text(Context context, String from, String dateTime, PlainText document, Builder.SIDE type) {
        super(context);

        this.from = from;

        this.date = dateTime;

        this.document = document;

        if (type == Builder.SIDE.LEFT) {
            LayoutInflater.from(context).inflate(R.layout.text_left, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.text_right, this);
        }

        findItems();

        loadItems();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins((int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15));

        setLayoutParams(layoutParams);

    }


}
