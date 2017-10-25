package ai.blip.cards.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import ai.blip.cards.R;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageView videoButton;
    private ImageView videoAudio;
    private SeekBar seekBar;
    private RelativeLayout bar;
    private String url;
    private int currentPosition = 0;
    private MediaPlayer mediaPlayer;

    private Handler handler = new Handler();

    private boolean isPlaying;
    private boolean isAudioOn;
    private int volume = 100;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Baixando vídeo");
        progressDialog.show();

        videoView = (VideoView) findViewById(R.id.videoView);
        videoButton = (ImageView) findViewById(R.id.videoButton);
        videoAudio = (ImageView) findViewById(R.id.videoAudio);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        bar = (RelativeLayout) findViewById(R.id.bar);
        bar.setVisibility(View.GONE);

        isAudioOn = true;

        currentPosition = getIntent().getIntExtra("currentPosition", 0);

        play(currentPosition);

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

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    stop();
                } else {
                    play(currentPosition);
                }
            }
        });

        videoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {

                    AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                    if (isAudioOn) {
                        videoAudio.setImageDrawable(ContextCompat.getDrawable(VideoActivity.this, R.drawable.com));
                        volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                    } else {
                        videoAudio.setImageDrawable(ContextCompat.getDrawable(VideoActivity.this, R.drawable.sem));
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
                    }
                    isAudioOn = !isAudioOn;
                }
            }
        });

    }

    private void play(int position) {

        videoButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pause));

        isPlaying = false;

        url = getIntent().getStringExtra("url");
        Uri video = Uri.parse(url);
        videoView.setVideoURI(video);
        videoView.seekTo(position);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                progressDialog.hide();


                mediaPlayer = mp;
                if (!isAudioOn) {
                    AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                }

                seekBar.setMax(videoView.getDuration());

                isPlaying = true;
                videoView.start();

                handler.postDelayed(updateTime, 500);
            }
        });
    }

    private void stop() {
        isPlaying = false;
        videoButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play_button_3));
        videoView.stopPlayback();
    }

    private Runnable updateTime = new Runnable() {
        public void run() {
            if (isPlaying) {
                currentPosition = videoView.getCurrentPosition();
                getIntent().putExtra("currentPosition", currentPosition);
                bar.setVisibility(View.VISIBLE);
                seekBar.setProgress(currentPosition);
                handler.postDelayed(this, 500);
            }
        }
    };
}
