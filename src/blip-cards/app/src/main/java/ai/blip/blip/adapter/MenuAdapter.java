package ai.blip.blip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.limeprotocol.DocumentContainer;
import org.limeprotocol.messaging.contents.DocumentSelectOption;
import org.limeprotocol.messaging.contents.PlainText;
import org.w3c.dom.Text;

import java.util.List;

import ai.blip.blip.R;

/**
 * Created by dirceubelem on 21/08/17.
 */

public class MenuAdapter extends BaseAdapter {

    private DocumentSelectOption[] itens;
    private Context context;

    public MenuAdapter(DocumentSelectOption[] itens, Context context) {
        this.itens = itens;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itens.length;
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

        if (itens[i].getLabel().getMediaType().toString().equals(DocumentContainer.MIME_TYPE)) {
            DocumentContainer dc = itens[i].getLabel();
            option.setText(dc.getValue().toString());
        }

        return v;
    }
}
