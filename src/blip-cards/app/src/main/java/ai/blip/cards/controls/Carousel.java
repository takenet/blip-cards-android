package ai.blip.cards.controls;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.limeprotocol.DocumentCollection;
import org.limeprotocol.messaging.contents.DocumentSelectOption;

import ai.blip.cards.R;
import ai.blip.cards.adapter.CarouselRecyclerAdapter;
import ai.blip.cards.ui.Builder;

public class Carousel extends BlipControl {

    private Carousel.CarouselListener listener;
    private RecyclerView content;
    private View contentView;
    private int width;
    private LinearLayout layoutName;
    private TextView name;
    private TextView dateTime;

    private DocumentCollection documentCollection;


    public Carousel(Context context, String from, String dateTime, DocumentCollection documentCollection, Builder.SIDE side) {
        super(context);
        if (side == Builder.SIDE.LEFT) {
            LayoutInflater.from(context).inflate(R.layout.carousel_left, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.carousel_right, this);
        }

        this.layoutName = findViewById(R.id.layoutName);
        this.name = findViewById(R.id.name);
        this.dateTime = findViewById(R.id.dateTime);

        if (from != null && !from.isEmpty()) {
            name.setText(from);
            this.layoutName.setVisibility(VISIBLE);

            if (dateTime != null) {
                this.dateTime.setText(dateTime);
            }

        } else {
            this.layoutName.setVisibility(GONE);
        }

        this.documentCollection = documentCollection;

        this.contentView = this;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins((int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15));

        setLayoutParams(layoutParams);


        content = findViewById(R.id.content);

        content.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(content);

        setItens();

    }

    public void setItens() {

        post(new Runnable() {

            @Override
            public void run() {
                width = contentView.getWidth();

                content.setAdapter(new CarouselRecyclerAdapter(getContext(), documentCollection, new CarouselRecyclerAdapter.CarouselRecyclerAdapterListener() {
                    @Override
                    public void onItemClick(int menuIndex, DocumentSelectOption item, int index) {
                        if (listener != null) {
                            listener.onItemClick(menuIndex, item, index);
                        }
                    }
                }, width));

            }

        });


    }

    public void setCarouselClickListener(Carousel.CarouselListener listener) {
        this.listener = listener;
    }

    public interface CarouselListener {
        void onItemClick(int menuIndex, DocumentSelectOption item, int index);
    }


}
