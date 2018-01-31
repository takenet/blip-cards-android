package ai.blip.bliptest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.limeprotocol.Document;
import org.limeprotocol.DocumentCollection;
import org.limeprotocol.DocumentContainer;
import org.limeprotocol.EnvelopeId;
import org.limeprotocol.MediaType;
import org.limeprotocol.Message;
import org.limeprotocol.messaging.contents.ChatState;
import org.limeprotocol.messaging.contents.DocumentSelect;
import org.limeprotocol.messaging.contents.DocumentSelectOption;
import org.limeprotocol.messaging.contents.Location;
import org.limeprotocol.messaging.contents.MediaLink;
import org.limeprotocol.messaging.contents.PlainText;
import org.limeprotocol.messaging.contents.Select;
import org.limeprotocol.messaging.contents.SelectOption;
import org.limeprotocol.messaging.contents.WebLink;
import org.limeprotocol.util.StringUtils;

import java.net.URI;

import ai.blip.cards.controls.Carousel;
import ai.blip.cards.controls.MenuMultimedia;
import ai.blip.cards.controls.QuickReply;
import ai.blip.cards.ui.BlipCard;

public class MainActivity extends AppCompatActivity {

    private LinearLayout content;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = (LinearLayout) findViewById(R.id.content);
        scroll = (ScrollView) findViewById(R.id.scroll);

        options();

    }

    public void options() {

        Message m = new Message();

        DocumentSelectOption option1 = new DocumentSelectOption();
        option1.setLabel(new DocumentContainer(new PlainText(getString(R.string.multimedia_menu_label))));
        option1.setOrder(1);
        DocumentSelectOption option2 = new DocumentSelectOption();
        option2.setLabel(new DocumentContainer(new PlainText(getString(R.string.carousel_label))));
        option2.setOrder(2);
        DocumentSelectOption option3 = new DocumentSelectOption();
        option3.setLabel(new DocumentContainer(new PlainText(getString(R.string.text_label))));
        option3.setOrder(3);
        DocumentSelectOption option4 = new DocumentSelectOption();
        option4.setLabel(new DocumentContainer(new PlainText(getString(R.string.text_json_label))));
        option4.setOrder(4);
        DocumentSelectOption option5 = new DocumentSelectOption();
        option5.setLabel(new DocumentContainer(new PlainText(getString(R.string.weblink_label))));
        option5.setOrder(5);
        DocumentSelectOption option6 = new DocumentSelectOption();
        option6.setLabel(new DocumentContainer(new PlainText(getString(R.string.quick_reply_label))));
        option6.setOrder(6);
        DocumentSelectOption option7 = new DocumentSelectOption();
        option7.setLabel(new DocumentContainer(new PlainText(getString(R.string.chatstate_label))));
        option7.setOrder(7);
        DocumentSelectOption option8 = new DocumentSelectOption();
        option8.setLabel(new DocumentContainer(new PlainText(getString(R.string.location_label))));
        option8.setOrder(8);
        DocumentSelectOption option9 = new DocumentSelectOption();
        option9.setLabel(new DocumentContainer(new PlainText(getString(R.string.media_audio_label))));
        option9.setOrder(9);
        DocumentSelectOption option10 = new DocumentSelectOption();
        option10.setLabel(new DocumentContainer(new PlainText(getString(R.string.media_video_label))));
        option10.setOrder(10);
        DocumentSelectOption option11 = new DocumentSelectOption();
        option11.setLabel(new DocumentContainer(new PlainText(getString(R.string.media_image_label))));
        option11.setOrder(11);
        DocumentSelectOption option12 = new DocumentSelectOption();
        option12.setLabel(new DocumentContainer(new PlainText(getString(R.string.media_document_label))));
        option12.setOrder(12);

        DocumentSelectOption[] options = new DocumentSelectOption[12];
        options[0] = option1;
        options[1] = option2;
        options[2] = option3;
        options[3] = option4;
        options[4] = option5;
        options[5] = option6;
        options[6] = option7;
        options[7] = option8;
        options[8] = option9;
        options[9] = option10;
        options[10] = option11;
        options[11] = option12;

        DocumentSelect select = new DocumentSelect();
        select.setHeader(new DocumentContainer(new PlainText("Select one option")));
        select.setOptions(options);

        m.setContent(select);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
                    @Override
                    public void onItemClick(DocumentSelectOption documentSelectOption, int i) {
                        if (i == 0) {
                            menuMultiMediaClick();
                        } else if (i == 1) {
                            carouselClick();
                        } else if (i == 2) {
                            textClick();
                        } else if (i == 3) {
                            textJsonClick();
                        } else if (i == 4) {
                            webLinkClick();
                        } else if (i == 5) {
                            quickReplyClick();
                        } else if (i == 6) {
                            chatStateClick();
                        } else if (i == 7) {
                            locationClick();
                        } else if (i == 8) {
                            audioClick();
                        } else if (i == 9) {
                            videoClick();
                        } else if (i == 10) {
                            imageClick();
                        } else if (i == 11) {
                            documentClick();
                        }
                    }
                })
                .setFrom("Blip")
                .build();

        content.addView(v);

    }

    public void documentClick() {
        MediaLink p = new MediaLink();
        p.setSize(new Long(3124123));
        p.setText(getString(R.string.pdf_content_text));
        p.setType(MediaType.parse("application/pdf"));
        p.setUri(URI.create("https://s3-sa-east-1.amazonaws.com/i.imgtake.takenet.com.br/d6ztq/d6ztq.pdf"));

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();
    }

    public void imageClick() {
        MediaLink p = new MediaLink();
        p.setText(getString(R.string.image_text));
        p.setType(MediaType.parse("image/jpeg"));
        p.setUri(URI.create("http://2.bp.blogspot.com/-pATX0YgNSFs/VP-82AQKcuI/AAAAAAAALSU/Vet9e7Qsjjw/s1600/Cat-hd-wallpapers.jpg"));
        p.setPreviewType(MediaType.parse("image/jpeg"));
        p.setPreviewUri(URI.create("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS8qkelB28RstsNxLi7gbrwCLsBVmobPjb5IrwKJSuqSnGX4IzX"));

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();
    }

    public void videoClick() {
        MediaLink p = new MediaLink();
        p.setType(MediaType.parse("video/mp4"));
        p.setUri(URI.create("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4"));

        p.setPreviewType(MediaType.parse("image/jpeg"));
        p.setPreviewUri(URI.create("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS8qkelB28RstsNxLi7gbrwCLsBVmobPjb5IrwKJSuqSnGX4IzX"));


        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();
    }

    public void audioClick() {
        MediaLink p = new MediaLink();
        p.setText(getString(R.string.skank_story));
        p.setType(MediaType.parse("audio/mp3"));
        p.setUri(URI.create("http://blaamandagjazzband.dk/jazz/mp3/basin_street_blues.mp3"));

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();
    }

    public void locationClick() {

        Location p = new Location();
        p.setText("Take's place");
        p.setLatitude(-19.918899);
        p.setLongitude(-43.959275);

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setLocationClickListener(new ai.blip.cards.controls.Location.LocationListener() {
                    @Override
                    public void onLocationClick(Location l) {
                        Toast.makeText(getBaseContext(), "LocationClick " + l.getLatitude() + "," + l.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();
    }

    public void chatStateClick() {
        ChatState p = new ChatState();

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .right()
                .setMessage(m)
                .build();

        content.addView(v);

        sendToDown();
    }

    public void quickReplyClick() {
        Select p = new Select();
        p.setText("text");

        SelectOption[] options = new SelectOption[4];
        SelectOption op1 = new SelectOption();
        op1.setText(getString(R.string.option_1));
        options[0] = op1;
        SelectOption op2 = new SelectOption();
        op2.setText(getString(R.string.option_2));
        options[1] = op2;
        SelectOption op3 = new SelectOption();
        op3.setText(getString(R.string.option_3));
        options[2] = op3;
        SelectOption op4 = new SelectOption();
        op4.setText(getString(R.string.option_4));
        options[3] = op4;

        p.setOptions(options);

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setQuickReplyListener(new QuickReply.QuickReplyListener() {
                    @Override
                    public void onItemClick(SelectOption selectOption, int i) {
                        Toast.makeText(getBaseContext(), "" + selectOption.getText(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();
    }

    public void sendToDown() {
        scroll.post(new Runnable() {
            @Override
            public void run() {

                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        scroll.fullScroll(View.FOCUS_DOWN);

                    }
                }, 300);

            }
        });
    }

    public void webLinkClick() {

        WebLink p = new WebLink();
        p.setText(getString(R.string.link_text));
        p.setTitle(getString(R.string.link_title));
        p.setUri(URI.create(getString(R.string.url_text)));

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .right()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
                    @Override
                    public void onItemClick(DocumentSelectOption documentSelectOption, int i) {
                        Toast.makeText(getBaseContext(), "" + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();
    }

    public void textClick() {

        PlainText p = new PlainText(getString(R.string.clear_everything_up));

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .right()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
                    @Override
                    public void onItemClick(DocumentSelectOption documentSelectOption, int i) {
                        Toast.makeText(getBaseContext(), "" + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();
    }

    public void textJsonClick() {

        String id = EnvelopeId.newId();
        String from = getString(R.string.virtual_assistant);
        String randomKey1 = "randomString1";
        String randomKey2 = "randomString2";
        String randomString1 = "xpto";
        String randomString2 = "xpto";

        String text = getString(R.string.clear_everything_up);

        String json = StringUtils.format(
                "{\"type\":\"text/plain\",\"content\":\"{0}\",\"id\":\"{1}\",\"from\":\"{2}\",\"to\":\"{3}\",\"metadata\":{\"{4}\":\"{5}\",\"{6}\":\"{7}\"}}",
                text,
                id,
                from,
                from,
                randomKey1,
                randomString1,
                randomKey2,
                randomString2
        );

        View v = new BlipCard(this)
                .right()
                .setJSON(json)
                .setFrom(getString(R.string.virtual_assistant))
                .setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
                    @Override
                    public void onItemClick(DocumentSelectOption documentSelectOption, int i) {
                        Toast.makeText(getBaseContext(), "" + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();
    }

    public void menuMultiMediaClick() {

        Message m = new Message();

        m.setContent(createSelect(
                getString(R.string.fill_out_proposal_1),
                getString(R.string.clear_everything_up),
                getString(R.string.image_url)));

        View v = new BlipCard(this)
                .right()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
                    @Override
                    public void onItemClick(DocumentSelectOption documentSelectOption, int i) {
                        Toast.makeText(getBaseContext(), "" + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();

    }

    private DocumentSelect createSelect(String title, String text, String imageUri) {
        DocumentSelectOption option1 = new DocumentSelectOption();
        option1.setLabel(new DocumentContainer(new PlainText(getString(R.string.option_1))));
        option1.setOrder(1);
        DocumentSelectOption option2 = new DocumentSelectOption();
        option2.setLabel(new DocumentContainer(new PlainText(getString(R.string.option_2))));
        option2.setOrder(2);
        DocumentSelectOption[] options = new DocumentSelectOption[2];
        options[0] = option1;
        options[1] = option2;

        DocumentSelect select = new DocumentSelect();
        select.setHeader(new DocumentContainer(new PlainText(getString(R.string.choose_lunch_msg))));
        select.setOptions(options);

        MediaLink link = new MediaLink();
        try {
            link.setUri(new URI(imageUri));
        } catch (Exception e) {
            Log.e("xxx", e.getMessage());

        }
        link.setText(text);
        link.setTitle(title);

        DocumentContainer dc = new DocumentContainer();
        dc.setValue(link);

        select.setHeader(dc);

        return select;
    }

    public void carouselClick() {

        Message m = new Message();

        DocumentCollection docCol = new DocumentCollection();

        Document[] docs = new Document[3];
        docs[0] = createSelect(getString(R.string.fill_out_proposal_1), getString(R.string.clear_everything_up), getString(R.string.image_url));
        docs[1] = createSelect(getString(R.string.fill_out_proposal_2), getString(R.string.clear_everything_up), getString(R.string.image_url));
        docs[2] = createSelect(getString(R.string.fill_out_proposal_1), getString(R.string.clear_everything_up), getString(R.string.image_url));

        docCol.setItems(docs);

        m.setContent(docCol);

        View v = new BlipCard(this)
                .right()
                .setMessage(m)
                .setFrom(getString(R.string.virtual_assistant))
                .setCaroulselClickListener(new Carousel.CarouselListener() {
                    @Override
                    public void onItemClick(int i, DocumentSelectOption documentSelectOption, int i1) {
                        Toast.makeText(getBaseContext(), "Menu: " + i + " option: " + i1, Toast.LENGTH_SHORT).show();
                    }
                })

                .setFrom(getString(R.string.select_msg))
                .setDateTime(getString(R.string.time_of_day))
                .build();

        content.addView(v);

        sendToDown();

    }
}
