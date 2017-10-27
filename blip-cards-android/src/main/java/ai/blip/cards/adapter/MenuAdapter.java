package ai.blip.cards.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.limeprotocol.DocumentContainer;
import org.limeprotocol.messaging.contents.DocumentSelectOption;

import ai.blip.cards.R;

public class MenuAdapter extends BaseAdapter {

    private DocumentSelectOption[] items;
    private Context context;

    public MenuAdapter(DocumentSelectOption[] itens, Context context) {
        this.items = itens;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.menu_option_item, null);

        TextView option = v.findViewById(R.id.option);

        if (items[i].getLabel().getMediaType().toString().equals(DocumentContainer.MIME_TYPE)) {
            DocumentContainer dc = items[i].getLabel();
            option.setText(dc.getValue().toString());
        }

        return v;
    }
}
