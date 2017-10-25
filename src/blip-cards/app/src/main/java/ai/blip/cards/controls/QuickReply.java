package ai.blip.cards.controls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.limeprotocol.messaging.contents.Select;
import org.limeprotocol.messaging.contents.SelectOption;

import ai.blip.cards.R;
import ai.blip.cards.adapter.QuickReplyAdapter;
import ai.blip.cards.ui.Builder;

public class QuickReply extends BlipControl {

    private QuickReply.QuickReplyListener listener;

    private LinearLayout layoutName;
    private String from;
    private String date;
    private TextView text;
    private TextView dateTime;
    private TextView name;
    private RecyclerView list;

    private Select document;

    public void findItens() {
        this.layoutName = findViewById(R.id.layoutName);
        this.text = findViewById(R.id.text);
        this.dateTime = findViewById(R.id.dateTime);
        this.name = findViewById(R.id.name);
        this.list = findViewById(R.id.list);
    }

    public void setQuickReplyListener(QuickReplyListener listener) {
        this.listener = listener;
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

        QuickReplyAdapter adapter = new QuickReplyAdapter(getContext(), document.getOptions(), new QuickReplyAdapter.QuickReplyAdapterListener() {
            @Override
            public void onItemClick(SelectOption item, int index) {
                if (listener != null) {
                    listener.onItemClick(item, index);
                }
            }
        });
        list.setAdapter(adapter);


    }

    public QuickReply(Context context, String from, String dateTime, Select document, Builder.SIDE type) {
        super(context);

        this.from = from;

        this.date = dateTime;

        this.document = document;

        if (type == Builder.SIDE.LEFT) {
            LayoutInflater.from(context).inflate(R.layout.quickreply_left, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.quickreply_right, this);
        }

        findItens();

        loadItens();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins((int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15));

        setLayoutParams(layoutParams);

    }

    public interface QuickReplyListener {
        void onItemClick(SelectOption item, int index);
    }


}
