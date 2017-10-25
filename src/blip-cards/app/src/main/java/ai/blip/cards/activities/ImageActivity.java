package ai.blip.cards.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ai.blip.cards.R;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        String url = getIntent().getStringExtra("url");

        ImageView image = (ImageView) findViewById(R.id.image);

        Glide.with(this)
                .load(url)
                .into(image);

    }
}
