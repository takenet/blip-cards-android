package ai.blip.cards.controls;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.limeprotocol.MediaType;
import org.limeprotocol.messaging.contents.MediaLink;

import java.util.concurrent.TimeUnit;

import ai.blip.cards.R;
import ai.blip.cards.activities.ImageActivity;
import ai.blip.cards.activities.VideoActivity;
import ai.blip.cards.ui.Builder;

public class Media extends BlipControl {

    private LinearLayout layoutName;
    private String from;
    private String date;
    private TextView text;
    private TextView dateTime;
    private TextView name;
    private MediaPlayer player;
    private SeekBar seekBar;
    private TextView audioTime;
    private LinearLayout mediaAudio;
    private ImageView audioButton;

    private RelativeLayout mediaVideo;
    private ImageView imageVideo;
    private ImageView buttonPlay;

    private RelativeLayout mediaImage;
    private ImageView image;

    private RelativeLayout mediaDocument;
    private ImageView mediaButton;
    private ImageView downloadButton;
    private ProgressBar loading;
    private TextView documentText;
    private TextView documentDetail;

    private boolean playing = false;
    private int position = 0;
    private Handler handler = new Handler();
    private Handler myHandler = new Handler();

    private boolean prepared = false;
    private boolean stopped = false;
    private boolean downloaded = false;

    private MediaLink document;

    private Context context;

    public Media(Context context, String from, String dateTime, MediaLink document, Builder.SIDE type) {
        super(context);

        this.context = context;

        this.from = from;

        this.date = dateTime;

        this.document = document;

        if (type == Builder.SIDE.LEFT) {
            LayoutInflater.from(context).inflate(R.layout.media_left, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.media_right, this);
        }

        findItems();

        loadItems();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins((int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15), (int) convertDpToPixel(15));

        setLayoutParams(layoutParams);

    }

    public void findItems() {
        this.layoutName = findViewById(R.id.layoutName);
        this.text = findViewById(R.id.text);
        this.dateTime = findViewById(R.id.dateTime);
        this.name = findViewById(R.id.name);

        this.mediaAudio = findViewById(R.id.mediaAudio);
        this.mediaVideo = findViewById(R.id.mediaVideo);
        this.mediaImage = findViewById(R.id.mediaImage);
        this.mediaDocument = findViewById(R.id.mediaDocument);

        this.loading = findViewById(R.id.loading);

    }

    public void loadItems() {

        if (from == null) {
            this.layoutName.setVisibility(GONE);
        } else {
            this.layoutName.setVisibility(VISIBLE);
            this.name.setText(from);

            if (date != null) {
                this.dateTime.setText(date);
            }
        }

        if (document.getText() != null && !document.getText().isEmpty()) {
            text.setText(document.getText());
        } else {
            text.setVisibility(GONE);
        }

        if (document.getType().equals(MediaType.parse("audio/mp3"))) {
            prepareAudio();
        } else if (document.getType().equals(MediaType.parse("video/mp4"))) {
            prepareVideo();
        } else if (document.getType().equals(MediaType.parse("image/jpeg"))) {
            prepareImage();
        } else if (document.getType().equals(MediaType.parse("application/pdf"))) {
            prepareDocument();
        }
    }

    public void prepareDocument() {
        this.mediaAudio.setVisibility(GONE);
        this.mediaVideo.setVisibility(GONE);
        this.mediaImage.setVisibility(GONE);
        this.mediaDocument.setVisibility(VISIBLE);

        this.downloadButton = findViewById(R.id.downloadButton);
        this.documentText = findViewById(R.id.documentText);
        this.documentDetail = findViewById(R.id.documentDetail);

        this.documentText.setText(document.getText());

        this.text.setVisibility(GONE);

        double value = (document.getSize() * 1.0) / 1024.0 / 1024.0;

        this.documentDetail.setText(String.format("PDF %.2f MB", value));

        this.downloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(document.getUri().toString()), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    (getContext()).startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(context, "Nenhum aplicativo leitor de PDF encontrado em seu dispositivo. ", Toast.LENGTH_SHORT).show();

//                    PackageManager pm = context.getPackageManager();
//                    Intent intent = pm.getLaunchIntentForPackage("com.google.android.apps.docs");
//                    intent.setType("application/pdf");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(document.getUri().toString()));
//                    intent.putExtra(Intent.EXTRA_SUBJECT, "attach a file test");
//                    intent.addCategory(Intent.ACTION_ATTACH_DATA);
//                    (getContext()).startActivity(intent);
                }

            }
        });
    }

    public void prepareImage() {

        this.mediaAudio.setVisibility(GONE);
        this.mediaVideo.setVisibility(GONE);
        this.mediaImage.setVisibility(VISIBLE);
        this.mediaDocument.setVisibility(GONE);

        this.image = findViewById(R.id.image);

        Glide.with(getContext())
                .load(document.getPreviewUri().toString())
                .into(this.image);

        this.image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ImageActivity.class);
                i.putExtra("url", document.getUri().toString());
                (getContext()).startActivity(i);
            }
        });
    }

    public void prepareVideo() {

        this.mediaAudio.setVisibility(GONE);
        this.mediaImage.setVisibility(GONE);
        this.mediaVideo.setVisibility(VISIBLE);
        this.mediaDocument.setVisibility(GONE);

        this.buttonPlay = findViewById(R.id.buttonPlay);
        this.imageVideo = findViewById(R.id.imageVideo);

        if (document.getPreviewUri() != null) {
            Glide.with(getContext())
                    .load(document.getPreviewUri().toString())
                    .into(this.imageVideo);
        } else {
            Glide.with(getContext())
                    .load(document.getUri().toString())
                    .into(this.imageVideo);
        }

        this.buttonPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), VideoActivity.class);
                i.putExtra("url", document.getUri().toString());
                (getContext()).startActivity(i);
            }
        });

    }

    public void prepareAudio() {

        this.mediaAudio.setVisibility(VISIBLE);
        this.mediaVideo.setVisibility(GONE);
        this.mediaImage.setVisibility(GONE);
        this.mediaDocument.setVisibility(GONE);

        this.seekBar = findViewById(R.id.seekBar);
        this.audioTime = findViewById(R.id.audioTime);
        this.audioButton = findViewById(R.id.audioButton);

        audioButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playing) {
                    play(position);
                } else {
                    stop();
                }
            }
        });

        prepare();
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {

            updateTime();

            seekBar.setProgress(player.getCurrentPosition());
            if (playing) {
                myHandler.postDelayed(this, 1000);
            }
        }
    };

    public void updateTime() {

        this.loading.setVisibility(GONE);
        this.audioButton.setVisibility(VISIBLE);


        position = player.getCurrentPosition();

        int pos = player.getDuration() - player.getCurrentPosition();

        audioTime.setText(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) pos),
                TimeUnit.MILLISECONDS.toSeconds((long) pos) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) pos)))
        );

    }

    private void prepare() {
        try {
            player = new MediaPlayer();
            Uri uri = Uri.parse(document.getUri().toString());
            player.setDataSource(getContext(), uri);


            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Log.i("player", "prepared");
                    prepared = true;
                    updateTime();

                    if (playing && !downloaded) {
                        downloaded = true;
                        play(position);
                    }
                }
            });


        } catch (Exception e) {
            Log.e("Audio", e.getMessage());
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stop();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                play(seekBar.getProgress());
            }
        });
    }

    private void stop() {
        try {
            audioButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.play_bola_25_x_25));

            playing = false;
            stopped = true;
            player.stop();

            myHandler.removeCallbacks(UpdateSongTime);
        } catch (Exception e) {
            Log.e("Audio", e.getMessage());
        }
    }

    private void play(final int position) {
        try {

            if (!prepared) {
                player.prepareAsync();
            }

            audioButton.setVisibility(GONE);
            audioButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.pauseaudio));
            playing = true;

            if (prepared) {

                Runnable r = new Runnable() {
                    public void run() {

                        try {

                            Log.i("loading", "visible");
                            loading.setVisibility(VISIBLE);
                            loading.setIndeterminate(true);

                            if (stopped) {
                                player.prepare();
                            }
//                        player.start();
                            player.seekTo(position);

                            player.start();
                            seekBar.setMax(player.getDuration());

                            myHandler.postDelayed(UpdateSongTime, 1000);

                            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    stop();
                                    seekBar.setProgress(0);
                                    Media.this.position = 0;
                                }
                            });

                        } catch (Exception e) {

                        }

                    }
                };

                handler.post(r);

            } else {

            }

        } catch (Exception e) {
            Log.e("Audio", e.getMessage());
        }
    }


}
