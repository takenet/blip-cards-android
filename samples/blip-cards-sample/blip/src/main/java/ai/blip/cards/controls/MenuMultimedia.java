package ai.blip.cards.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.limeprotocol.messaging.contents.DocumentSelect;
import org.limeprotocol.messaging.contents.DocumentSelectOption;
import org.limeprotocol.messaging.contents.MediaLink;
import org.limeprotocol.messaging.contents.PlainText;

import ai.blip.cards.R;
import ai.blip.cards.adapter.MenuAdapter;
import ai.blip.cards.ui.Builder;

public class MenuMultimedia extends BlipControl implements AdapterView.OnItemClickListener {

    private MenuMultimediaListener listener;

    private LinearLayout layoutName;
    private String from;
    private String date;
    private TextView title;
    private TextView text;
    private TextView dateTime;
    private TextView name;
    private ImageView image;
    private ListView optionsMedia;
    private View distance;

    private DocumentSelect document;

    public void findItens() {
        this.layoutName = findViewById(R.id.layoutName);
        this.title = findViewById(R.id.title);
        this.text = findViewById(R.id.text);
        this.dateTime = findViewById(R.id.dateTime);
        this.name = findViewById(R.id.name);
        this.image = findViewById(R.id.image);
        this.optionsMedia = findViewById(R.id.options_media);
        this.distance = findViewById(R.id.distance);
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

        if (document.getHeader() != null) {

            this.title.setVisibility(GONE);
            this.text.setVisibility(GONE);
            this.image.setVisibility(GONE);
            this.distance.setVisibility(GONE);

            String mediaType = this.document.getHeader().getValue().getMediaType().toString();

            if (mediaType.equals(MediaLink.MIME_TYPE)) {

                MediaLink media = (MediaLink) document.getHeader().getValue();
                if (media.getTitle() != null && !media.getTitle().isEmpty()) {
                    this.title.setText(media.getTitle());
                    this.title.setVisibility(VISIBLE);
                }

                if (media.getText() != null && !media.getText().isEmpty()) {
                    this.text.setText(media.getText());
                    this.text.setVisibility(VISIBLE);
                }

                if (media.getUri() != null) {
                    this.image.setVisibility(VISIBLE);
                    Glide.with(getContext()).load(media.getUri().toString()).into(image);
                }

            } else if (mediaType.equals(PlainText.MIME_TYPE)) {

                String value = document.getHeader().getValue().toString();
                this.title.setText(value);
                this.title.setVisibility(VISIBLE);
                this.distance.setVisibility(VISIBLE);

            }


        }

    }

    public MenuMultimedia(Context context, String from, String dateTime, DocumentSelect document, Builder.SIDE type) {
        super(context);

        this.from = from;
        this.date = dateTime;

        this.document = document;

        if (type == Builder.SIDE.LEFT) {
            LayoutInflater.from(context).inflate(R.layout.menu_multimedia_left, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.menu_multimedia_right, this);
        }

        findItens();

        loadItens();

        setItens();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins((int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15));

        setLayoutParams(layoutParams);

    }

    public MenuMultimedia(Context context, DocumentSelect document) {
        super(context);

        this.from = null;
        this.date = null;

        this.document = document;

        LayoutInflater.from(context).inflate(R.layout.menu_multimedia, this);

        findItens();

        loadItens();

        setItens();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins((int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15));

        setLayoutParams(layoutParams);

    }

    public void setItens() {
        MenuAdapter adapter = new MenuAdapter(document.getOptions(), getContext());
        optionsMedia.setAdapter(adapter);
        optionsMedia.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (listener != null) {
            listener.onItemClick(document.getOptions()[i], i);
        }
    }

    public void setMenuMultimediaClickListener(MenuMultimediaListener listener) {
        this.listener = listener;
    }

    public interface MenuMultimediaListener {
        void onItemClick(DocumentSelectOption item, int index);
    }

}
