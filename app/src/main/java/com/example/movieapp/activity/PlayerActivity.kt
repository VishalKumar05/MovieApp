package com.example.movieapp.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.movieapp.R
import com.google.android.exoplayer2.util.Log
import com.ltts.lttsplayer.LTTSPlayerView
import com.ltts.lttsplayer.configuration.PlayerConfig
import com.ltts.lttsplayer.events.Error
import com.ltts.lttsplayer.events.listeners.MediaControllerEvents
import com.ltts.lttsplayer.events.listeners.VideoPlayerEvents
import com.ltts.lttsplayer.playlists.PlaylistItem
import com.ltts.lttsplayer.ui.MediaPlayerControl
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity(), MediaControllerEvents, VideoPlayerEvents.OnPlayerSetupListener,
    VideoPlayerEvents.OnPlayerEventListener {

    private lateinit var playlist : ArrayList<PlaylistItem>
    private lateinit var mediaPlayerControl: MediaPlayerControl
    private var isFullScreen = false
    private var videoPlayer: LTTSPlayerView? = null

    companion object{
        val TAG: String = PlayerActivity::class.java.simpleName
        const val videoUrl:String = "https://html5demos.com/assets/dizzy.mp4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setup()
    }

    private fun setup() {
        mediaPlayerControl = MediaPlayerControl(this)
        (mediaPlayerControl as MediaPlayerControl).setFullscreenButtonListener {
            requestedOrientation = if(!isFullScreen)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        playlist = ArrayList()
        val playlistItem = PlaylistItem.Builder()
            .setFile(videoUrl)
            .build()

        playlist.add(playlistItem)
        initVideoPlayer(playlist)
    }

    private fun initVideoPlayer(playlist: ArrayList<PlaylistItem>) {
        val playerConfig = PlayerConfig.Builder()
            .setPlaylistItems(playlist)
            .setAutoStart(true)
            .build()

        playerContainer.addEventListener(this)
        playerContainer.addSetupListener(this)
        playerContainer.setMediaPlayerControl(mediaPlayerControl)
        playerContainer.addMediaControlEventListener(this)
        playerContainer.setup(playerConfig)
    }

    // configuration change event listener for making player fullscreen
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val params = ConstraintLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            playerContainer?.layoutParams = params
            isFullScreen = true
        } else {
            val params = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 800)
            playerContainer?.layoutParams = params
            isFullScreen = false
        }
    }

    override fun onMediaControlsClick(view: View?) {
        if(view?.id == R.id.exo_fullscreen){
            requestedOrientation = if(!isFullScreen)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onPlayerReady(videoPlayer: LTTSPlayerView?) {
        this.videoPlayer = videoPlayer
    }

    override fun onPlayerSetupError(error: Error?) {
        Log.d(TAG,error.toString())
    }

    override fun onAudioListReady() {

    }

    override fun onPlayerError(error: Error?) {
        Log.d(TAG,error.toString())
    }

    override fun onPlayerEvent(event: Int) {
        when (event) {
            VideoPlayerEvents.PLAYER_RENDERED_FIRST_FRAME -> {
                android.util.Log.e(TAG,"PLAYER_RENDERED_FIRST_FRAME")
            }
            VideoPlayerEvents.PLAYER_STATE_IDLE ->{
                android.util.Log.e(TAG,"PLAYER_STATE_IDLE")
            }
            VideoPlayerEvents.PLAYER_STATE_BUFFERING ->{
                android.util.Log.e(TAG,"PLAYER_STATE_BUFFERING")
            }
            VideoPlayerEvents.PLAYER_STATE_PAUSED ->{
                android.util.Log.e(TAG,"PLAYER_STATE_PAUSED")
            }
            VideoPlayerEvents.PLAYER_STATE_READY -> {
                android.util.Log.e(TAG,"PLAYER_STATE_READY")
            }
            VideoPlayerEvents.PLAYER_STATE_FINISHED -> {
                android.util.Log.e(TAG,"PLAYER_STATE_FINISHED")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        videoPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        videoPlayer?.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoPlayer?.stop()
        videoPlayer?.removeSetupListener(this)
        videoPlayer?.removeEventListener(this)
    }

}