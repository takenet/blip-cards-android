package ai.blip.cards.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.limeprotocol.DocumentCollection;
import org.limeprotocol.messaging.contents.DocumentSelect;
import org.limeprotocol.messaging.contents.DocumentSelectOption;

import ai.blip.cards.R;
import ai.blip.cards.controls.MenuMultimedia;

public class CarouselRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private DocumentCollection documentCollection;
    private CarouselRecyclerAdapterListener listener;
    private int width;

    public CarouselRecyclerAdapter(Context context, DocumentCollection documentCollection, CarouselRecyclerAdapterListener listener, int width) {
        this.context = context;
        this.documentCollection = documentCollection;
        this.listener = listener;
        this.width = width;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_carousel, parent, false);

        return new MenuViewHolder(itemView, listener);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final MenuViewHolder m = (MenuViewHolder) holder;

        DocumentSelect select = (DocumentSelect) documentCollection.getItems()[position];

        final MenuMultimedia menu = new MenuMultimedia(context, select);

        menu.setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
            @Override
            public void onItemClick(DocumentSelectOption item, int index) {
                if (((MenuViewHolder) holder).listener != null) {
                    ((MenuViewHolder) holder).listener.onItemClick(position, item, index);
                }
            }
        });

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                width - (width / 5), LinearLayout.LayoutParams.WRAP_CONTENT);


        menu.setLayoutParams(lp);
        m.content.addView(menu);


    }

    @Override
    public int getItemCount() {
        return documentCollection.getItems().length;
    }

    protected class MenuViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout content;
        private CarouselRecyclerAdapterListener listener;

        public MenuViewHolder(View itemView, CarouselRecyclerAdapterListener listener) {
            super(itemView);
            this.content = itemView.findViewById(R.id.content);
            this.listener = listener;
        }
    }

    public interface CarouselRecyclerAdapterListener {
        void onItemClick(int menuIndex, DocumentSelectOption item, int index);
    }

}
