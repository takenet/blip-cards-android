package ai.blip.blip.controls;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ai.blip.blip.R;

/**
 * Created by dirceubelem on 14/08/17.
 */

public class ChatState extends BlipControl {

    private ImageView loading;

    public ChatState(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.chat_state, this);

        this.loading = findViewById(R.id.loading);

        Glide.with(context).load(R.drawable.loading)
                .into(loading);
    }


}
