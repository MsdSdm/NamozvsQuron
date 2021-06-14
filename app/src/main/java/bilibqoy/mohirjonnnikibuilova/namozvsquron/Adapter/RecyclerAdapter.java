package bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

/**
 * Created by Taslima on 3/12/2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    List<NameModel> seekerCycleModels;
    Activity activity;
    NameModel seekerCycleModel;
    MediaPlayer mediaPlayer;
    AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    public RecyclerAdapter(Activity activity, List<NameModel> seekerCycleModels) {
        this.activity = activity;
        this.seekerCycleModels = seekerCycleModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_update,parent,false);
            return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
            seekerCycleModel = seekerCycleModels.get(position);
            holder.banname.setText(seekerCycleModel.getBanName());
            holder.enname.setText(seekerCycleModel.getEnName());
            holder.englishmean.setText(seekerCycleModel.getEnglishmean());
            mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releaseMediaPlayer();
                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer = MediaPlayer.create(activity, seekerCycleModels.get(position).getMp3File());
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return seekerCycleModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView banname;
        TextView enname;
        TextView englishmean;
        ImageView play;

        public MyViewHolder(View itemView) {
            super(itemView);
            banname = (TextView) itemView.findViewById(R.id.ban);
            enname = (TextView) itemView.findViewById(R.id.eng);
            englishmean = (TextView) itemView.findViewById(R.id.englishMean);
            play = (ImageView) itemView.findViewById(R.id.play);
        }
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    public void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
