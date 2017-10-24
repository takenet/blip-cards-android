package ai.blip.blip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.limeprotocol.DocumentCollection;
import org.limeprotocol.messaging.contents.DocumentSelect;
import org.limeprotocol.messaging.contents.DocumentSelectOption;
import org.limeprotocol.messaging.contents.SelectOption;

import ai.blip.blip.R;
import ai.blip.blip.controls.MenuMultimedia;

/**
 * Created by dirceubelem on 12/09/17.
 */

public class QuickReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private SelectOption[] itens;
    private QuickReplyAdapter.QuickReplyAdapterListener listener;

    public QuickReplyAdapter(Context context, SelectOption[] itens, QuickReplyAdapter.QuickReplyAdapterListener listener) {
        this.context = context;
        this.itens = itens;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_quickreply, parent, false);

        return new QuickReplyAdapter.ItemViewHolder(itemView, listener);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final QuickReplyAdapter.ItemViewHolder m = (QuickReplyAdapter.ItemViewHolder) holder;

        final SelectOption option = itens[position];

        ((ItemViewHolder) holder).text.setText(option.getText());

        ((ItemViewHolder) holder).item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(option, position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return itens.length;
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item;
        private TextView text;
        private QuickReplyAdapter.QuickReplyAdapterListener listener;

        public ItemViewHolder(View itemView, final QuickReplyAdapter.QuickReplyAdapterListener listener) {
            super(itemView);
            this.item = itemView.findViewById(R.id.item);
            this.text = itemView.findViewById(R.id.text);
            this.listener = listener;
        }
    }

    public interface QuickReplyAdapterListener {
        void onItemClick(SelectOption item, int index);
    }

}
