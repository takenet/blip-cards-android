package ai.blip.blip.controls;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.limeprotocol.messaging.contents.PlainText;
import org.limeprotocol.messaging.contents.SelectOption;

import java.util.Locale;

import ai.blip.blip.R;
import ai.blip.blip.ui.Builder;

/**
 * Created by dirceubelem on 14/08/17.
 */

public class Location extends BlipControl {

    public static final String STATIC_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?"
            + "center=_location&zoom=_zoom&size=_size&markers=color:red%7C_location&scale=_scale&sensor=false";

    private LinearLayout layoutName;
    private String from;
    private String date;
    private TextView text;
    private TextView dateTime;
    private TextView name;
    private ImageView map;
    private TextView goToAddress;
    private LocationListener listener;
    private Context context;

    private org.limeprotocol.messaging.contents.Location document;

    public void findItens() {
        this.layoutName = findViewById(R.id.layoutName);
        this.text = findViewById(R.id.text);
        this.dateTime = findViewById(R.id.dateTime);
        this.name = findViewById(R.id.name);
        this.map = findViewById(R.id.map);
        this.goToAddress = findViewById(R.id.goToAddress);
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

        String url = formatStaticMapApiUrl(document.getLatitude(),
                document.getLongitude(), 450, 450, 17, 2);

        Glide.with(getContext()).load(url).into(map);

        this.goToAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (listener != null) {
//                    listener.onLocationClick(document);
//                }

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", document.getLatitude(), document.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                Location.this.context.startActivity(intent);

            }
        });

        this.map.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", document.getLatitude(), document.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                Location.this.context.startActivity(intent);
            }
        });

    }

    public Location(Context context, String from, String dateTime, org.limeprotocol.messaging.contents.Location document, Builder.SIDE type) {
        super(context);

        this.from = from;

        this.context = context;

        this.date = dateTime;

        this.document = document;

        if (type == Builder.SIDE.LEFT) {
            LayoutInflater.from(context).inflate(R.layout.location_left, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.location_right, this);
        }

        findItens();

        loadItens();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins((int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15));

        setLayoutParams(layoutParams);

    }

    public static String formatStaticMapApiUrl(double latitude,
                                               double longitude, int width, int height, int zoom, int scale) {
        String location = String.format("%s,%s", latitude, longitude);
        String size = String.format("%sx%s", width, height);

        return STATIC_MAP_URL
                .replaceAll("_location", location)
                .replace("_zoom", Integer.toString(zoom))
                .replace("_size", size)
                .replace("_scale", Integer.toString(scale));
    }

    public void setListener(LocationListener listener) {
        this.listener = listener;
    }

    public interface LocationListener {
        void onLocationClick(org.limeprotocol.messaging.contents.Location location);
    }


}
