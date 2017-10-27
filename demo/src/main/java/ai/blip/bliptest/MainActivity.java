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
        option1.setLabel(new DocumentContainer(new PlainText("Menu Multimedia")));
        option1.setOrder(1);
        DocumentSelectOption option2 = new DocumentSelectOption();
        option2.setLabel(new DocumentContainer(new PlainText("Carousel")));
        option2.setOrder(2);
        DocumentSelectOption option3 = new DocumentSelectOption();
        option3.setLabel(new DocumentContainer(new PlainText("Text")));
        option3.setOrder(3);
        DocumentSelectOption option4 = new DocumentSelectOption();
        option4.setLabel(new DocumentContainer(new PlainText("Web Link")));
        option4.setOrder(4);
        DocumentSelectOption option5 = new DocumentSelectOption();
        option5.setLabel(new DocumentContainer(new PlainText("Quick Reply")));
        option5.setOrder(5);
        DocumentSelectOption option6 = new DocumentSelectOption();
        option6.setLabel(new DocumentContainer(new PlainText("Chat State")));
        option6.setOrder(6);
        DocumentSelectOption option7 = new DocumentSelectOption();
        option7.setLabel(new DocumentContainer(new PlainText("Location")));
        option7.setOrder(7);
        DocumentSelectOption option8 = new DocumentSelectOption();
        option8.setLabel(new DocumentContainer(new PlainText("Media Audio")));
        option8.setOrder(8);
        DocumentSelectOption option9 = new DocumentSelectOption();
        option9.setLabel(new DocumentContainer(new PlainText("Media Video")));
        option9.setOrder(9);
        DocumentSelectOption option10 = new DocumentSelectOption();
        option10.setLabel(new DocumentContainer(new PlainText("Media Image")));
        option10.setOrder(10);
        DocumentSelectOption option11 = new DocumentSelectOption();
        option11.setLabel(new DocumentContainer(new PlainText("Media Document")));
        option11.setOrder(11);

        DocumentSelectOption[] options = new DocumentSelectOption[11];
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
                            menuMultiMidiaClick();
                        } else if (i == 1) {
                            carouselClick();
                        } else if (i == 2) {
                            textClick();
                        } else if (i == 3) {
                            webLinkClick();
                        } else if (i == 4) {
                            quickReplyClick();
                        } else if (i == 5) {
                            chatStateClick();
                        } else if (i == 6) {
                            locationClick();
                        } else if (i == 7) {
                            audioClick();
                        } else if (i == 8) {
                            videoClick();
                        } else if (i == 9) {
                            imageClick();
                        } else if (i == 10) {
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
        p.setText("Document PDF");
        p.setType(MediaType.parse("application/pdf"));
        p.setUri(URI.create("https://s3-sa-east-1.amazonaws.com/i.imgtake.takenet.com.br/d6ztq/d6ztq.pdf"));

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom("Virtual Assistant")
                .setDateTime("15:15")
                .build();

        content.addView(v);

        sendToDown();
    }

    public void imageClick() {
        MediaLink p = new MediaLink();
        p.setText("Here is a cat image for you!");
        p.setType(MediaType.parse("image/jpeg"));
        p.setUri(URI.create("http://2.bp.blogspot.com/-pATX0YgNSFs/VP-82AQKcuI/AAAAAAAALSU/Vet9e7Qsjjw/s1600/Cat-hd-wallpapers.jpg"));
        p.setPreviewType(MediaType.parse("image/jpeg"));
        p.setPreviewUri(URI.create("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS8qkelB28RstsNxLi7gbrwCLsBVmobPjb5IrwKJSuqSnGX4IzX"));

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom("Virtual Assistant")
                .setDateTime("15:15")
                .build();

        content.addView(v);

        sendToDown();
    }

    public void videoClick() {
        MediaLink p = new MediaLink();
//        p.setText("Há 20 anos, nós do Skank, entrávamos em estúdio pra gravar \"Garota Nacional\", música do álbum O Samba Poconé.");
        p.setType(MediaType.parse("video/mp4"));
//        p.setUri(URI.create("http://www.onirikal.com/videos/mp4/nestlegold.mp4"));
        p.setUri(URI.create("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4"));

        p.setPreviewType(MediaType.parse("image/jpeg"));
        p.setPreviewUri(URI.create("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS8qkelB28RstsNxLi7gbrwCLsBVmobPjb5IrwKJSuqSnGX4IzX"));


        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom("Assistente Virtual")
                .setDateTime("15:15")
                .build();

        content.addView(v);

        sendToDown();
    }

    public void audioClick() {
        MediaLink p = new MediaLink();
        p.setText("Há 20 anos, nós do Skank, entrávamos em estúdio pra gravar \"Garota Nacional\", música do álbum O Samba Poconé.");
        p.setType(MediaType.parse("audio/mp3"));
        p.setUri(URI.create("http://blaamandagjazzband.dk/jazz/mp3/basin_street_blues.mp3"));

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom("Assistente Virtual")
                .setDateTime("15:15")
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
                .setFrom("Assistente Virtual")
                .setLocationClickListener(new ai.blip.cards.controls.Location.LocationListener() {
                    @Override
                    public void onLocationClick(Location l) {
                        Toast.makeText(getBaseContext(), "LocationClick " + l.getLatitude() + "," + l.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime("15:15")
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
        op1.setText("Opção 1");
        options[0] = op1;
        SelectOption op2 = new SelectOption();
        op2.setText("Opção 2");
        options[1] = op2;
        SelectOption op3 = new SelectOption();
        op3.setText("Opção 3");
        options[2] = op3;
        SelectOption op4 = new SelectOption();
        op4.setText("Opção 4");
        options[3] = op4;

        p.setOptions(options);

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .left()
                .setMessage(m)
                .setFrom("Assistente Virtual")
                .setQuickReplyListener(new QuickReply.QuickReplyListener() {
                    @Override
                    public void onItemClick(SelectOption selectOption, int i) {
                        Toast.makeText(getBaseContext(), "" + selectOption.getText(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime("15:15")
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
        p.setText("text");
        p.setTitle("titulo");
        p.setUri(URI.create("http://www.uol.com.br"));

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .right()
                .setMessage(m)
                .setFrom("Assistente Virtual")
                .setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
                    @Override
                    public void onItemClick(DocumentSelectOption documentSelectOption, int i) {
                        Toast.makeText(getBaseContext(), "" + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime("15:15")
                .build();

        content.addView(v);

        sendToDown();
    }

    public void textClick() {

        PlainText p = new PlainText("Tire dúvidas sobre, proposta, código SMS, modelo de veículo, entre outros");

        Message m = new Message();
        m.setContent(p);

        View v = new BlipCard(this)
                .right()
                .setMessage(m)
                .setFrom("Assistente Virtual")
                .setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
                    @Override
                    public void onItemClick(DocumentSelectOption documentSelectOption, int i) {
                        Toast.makeText(getBaseContext(), "" + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime("15:15")
                .build();

        content.addView(v);

        sendToDown();
    }

    public void menuMultiMidiaClick() {

        Message m = new Message();
        m.setContent(criaSelect("Preench. de Proposta", "Tire dúvidas sobre, proposta, código SMS, modelo de veículo, entre outros", "http://files.lojas.club/blip.png"));

        View v = new BlipCard(this)
                .right()
                .setMessage(m)
                .setFrom("Assistente Virtual")
                .setMenuMultimediaClickListener(new MenuMultimedia.MenuMultimediaListener() {
                    @Override
                    public void onItemClick(DocumentSelectOption documentSelectOption, int i) {
                        Toast.makeText(getBaseContext(), "" + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .setDateTime("15:15")
                .build();

        content.addView(v);

        sendToDown();

    }

    private DocumentSelect criaSelect(String titulo, String texto, String imagem) {
        DocumentSelectOption option1 = new DocumentSelectOption();
        option1.setLabel(new DocumentContainer(new PlainText("Opção 1")));
        option1.setOrder(1);
        DocumentSelectOption option2 = new DocumentSelectOption();
        option2.setLabel(new DocumentContainer(new PlainText("Opção 2")));
        option2.setOrder(2);
        DocumentSelectOption[] options = new DocumentSelectOption[2];
        options[0] = option1;
        options[1] = option2;

        DocumentSelect select = new DocumentSelect();
        select.setHeader(new DocumentContainer(new PlainText("Choose your lunch")));
        select.setOptions(options);

        String uri = imagem;
        String title = titulo;
        String text = texto;

        MediaLink link = new MediaLink();
        try {
            link.setUri(new URI(uri));
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
        docs[0] = criaSelect("Preench. de Proposta 1", "Tire dúvidas sobre, proposta, código SMS, modelo de veículo, entre outros", "http://files.lojas.club/blip.png");
        docs[1] = criaSelect("Preench. de Proposta 2", "Tire dúvidas sobre, proposta, código SMS, modelo de veículo, entre outros", "http://files.lojas.club/blip2.png");
        docs[2] = criaSelect("Preench. de Proposta 1", "Tire dúvidas sobre, proposta, código SMS, modelo de veículo, entre outros", "http://files.lojas.club/blip.png");

        docCol.setItems(docs);

        m.setContent(docCol);

        View v = new BlipCard(this)
                .right()
                .setMessage(m)
                .setFrom("Assistente Virtual")
                .setCaroulselClickListener(new Carousel.CarouselListener() {
                    @Override
                    public void onItemClick(int i, DocumentSelectOption documentSelectOption, int i1) {
                        Toast.makeText(getBaseContext(), "Menu: " + i + " opção: " + i1, Toast.LENGTH_SHORT).show();
                    }
                })
                .setFrom("Selecione um dos itens abaixo")
                .setDateTime("15:15")
                .build();

        content.addView(v);

        sendToDown();

    }
}
