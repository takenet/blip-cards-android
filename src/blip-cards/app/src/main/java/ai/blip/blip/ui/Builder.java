package ai.blip.blip.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import org.limeprotocol.Document;
import org.limeprotocol.DocumentCollection;
import org.limeprotocol.Message;
import org.limeprotocol.messaging.contents.ChatState;
import org.limeprotocol.messaging.contents.DocumentSelect;
import org.limeprotocol.messaging.contents.DocumentSelectOption;
import org.limeprotocol.messaging.contents.Location;
import org.limeprotocol.messaging.contents.MediaLink;
import org.limeprotocol.messaging.contents.PlainText;
import org.limeprotocol.messaging.contents.Select;
import org.limeprotocol.messaging.contents.WebLink;

import ai.blip.blip.controls.Carousel;
import ai.blip.blip.controls.Media;
import ai.blip.blip.controls.MenuMultimedia;
import ai.blip.blip.controls.QuickReply;
import ai.blip.blip.controls.Text;

/**
 * Created by dirceubelem on 29/08/17.
 */

public class Builder extends LinearLayout {

    public enum SIDE {
        LEFT,
        RIGHT
    }

    private Message message;
    private String from;
    private String dateTime;
    private SIDE side;
    private MenuMultimedia.MenuMultimediaListener listenerMenu;
    private Carousel.CarouselListener listenerCarousel;
    private QuickReply.QuickReplyListener listenerQuickReply;
    private ai.blip.blip.controls.Location.LocationListener listenerLocation;

    public Builder(Context context) {
        super(context);
        this.side = SIDE.LEFT;
    }

    private Builder(Context context, SIDE side) {
        super(context);
        this.side = side;
    }

    public Builder setMessage(Message message) {
        this.message = message;
        return this;
    }

    public Builder setFrom(String from) {
        this.from = from;
        return this;
    }

    public Builder setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Builder setQuickReplyListener(QuickReply.QuickReplyListener listenerQuickReply) {
        this.listenerQuickReply = listenerQuickReply;
        return this;
    }

    public Builder setMenuMultimediaClickListener(MenuMultimedia.MenuMultimediaListener listenerMenu) {
        this.listenerMenu = listenerMenu;
        return this;
    }

    public Builder setCaroulselClickListener(Carousel.CarouselListener listenerCarousel) {
        this.listenerCarousel = listenerCarousel;
        return this;
    }

    public Builder setLocationClickListener(ai.blip.blip.controls.Location.LocationListener listenerLocation) {
        this.listenerLocation = listenerLocation;
        return this;
    }

    public static Builder left(Context context) {
        return new Builder(context, SIDE.LEFT);
    }

    public static Builder right(Context context) {
        return new Builder(context, SIDE.RIGHT);
    }

    public View build() {

        switch (message.getType().toString()) {
            case DocumentSelect.MIME_TYPE:
                return buildMenu();
            case DocumentCollection.MIME_TYPE:
                return buildCarousel();
            case PlainText.MIME_TYPE:
                return buildText();
            case WebLink.MIME_TYPE:
                return buildWebLink();
            case Select.MIME_TYPE:
                return quickReplyClick();
            case ChatState.MIME_TYPE:
                return chatStateCreate();
            case Location.MIME_TYPE:
                return locationCreate();
            case MediaLink.MIME_TYPE:
                return mediaCreate();
            default:
                return null;
        }
    }

    private View mediaCreate() {
        MediaLink s = (MediaLink) message.getContent();
        Media t = new Media(getContext(), from, dateTime, s, side);
        return t;
    }

    private View locationCreate() {
        Location s = (Location) message.getContent();
        ai.blip.blip.controls.Location t = new ai.blip.blip.controls.Location(getContext(), from, dateTime, s, side);
        t.setListener(listenerLocation);
        return t;
    }

    private View chatStateCreate() {
        ai.blip.blip.controls.ChatState t = new ai.blip.blip.controls.ChatState(getContext());
        return t;
    }

    private View quickReplyClick() {
        Select s = (Select) message.getContent();
        QuickReply t = new QuickReply(getContext(), from, dateTime, s, side);
        t.setQuickReplyListener(listenerQuickReply);
        return t;
    }

    private View buildWebLink() {
        WebLink s = (WebLink) message.getContent();
        ai.blip.blip.controls.WebLink t = new ai.blip.blip.controls.WebLink(getContext(), from, dateTime, s, side);
        return t;
    }

    private View buildText() {
        PlainText s = (PlainText) message.getContent();
        Text t = new Text(getContext(), from, dateTime, s, side);
        return t;
    }

    private View buildMenu() {
        DocumentSelect s = (DocumentSelect) message.getContent();
        MenuMultimedia m = new MenuMultimedia(getContext(), from, dateTime, s, side);
        m.setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
            @Override
            public void onItemClick(DocumentSelectOption item, int index) {
                if (listenerMenu != null) {
                    listenerMenu.onItemClick(item, index);
                }
            }
        });
        return m;
    }

    private View buildCarousel() {
        DocumentCollection d = (DocumentCollection) message.getContent();
        Carousel c = new Carousel(getContext(), from, dateTime, d, side);

        c.setCarouselClickListener(new Carousel.CarouselListener() {
            @Override
            public void onItemClick(int menuIndex, DocumentSelectOption item, int index) {
                if (listenerCarousel != null) {
                    listenerCarousel.onItemClick(menuIndex, item, index);
                }
            }
        });

        return c;
    }

}
