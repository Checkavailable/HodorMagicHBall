package bz.bosco.hodormagichball;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {


    private CrystalBall mCrystalBall = new CrystalBall();
    private VideoView mVideo;
    private TextView mAnswerLabel;
    private ImageView mHodorImage;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning views from layout files
        mAnswerLabel = (TextView) findViewById(R.id.textView1);
        mVideo = (VideoView) findViewById(R.id.videoView);
        mVideo.setVisibility(View.INVISIBLE);
        mHodorImage = (ImageView) findViewById(R.id.imageView1);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener(){

            public void onShake(){
                handleNewAnswer();
            }
        });

        Toast welcomeToast = Toast.makeText(this, "Ask Hodor a question and shake the phone to hear his answer!",Toast.LENGTH_LONG);
        welcomeToast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        welcomeToast.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(mShakeDetector,mAccelerometer,
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
        //hello
        //Hello again
    }

    private void handleNewAnswer() {
        String answer = mCrystalBall.getAnAnswer();
        mAnswerLabel.setText("Hodor's Magic H Ball");
        //animateCrystalBall();
        animateAnswer();
        playHodor(answer);
    }

    private void animateCrystalBall(){
        mHodorImage.setImageResource(R.drawable.hodor);
        //AnimationDrawable ballAnimation = (AnimationDrawable) mHodorImage.getDrawable();
        //if(ballAnimation.isRunning()){ballAnimation.stop();}
       // ballAnimation.start();
    }

    private void animateAnswer(){
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(1500);
        fadeInAnimation.setFillAfter(true);
        mAnswerLabel.setAnimation(fadeInAnimation);
    }

    private void playHodor(String answer){


        int videoResource = getResources().getIdentifier(answer, "raw", getPackageName());
        String path = "android.resource://" + getPackageName() + "/" + videoResource;

        mVideo.setVisibility(View.VISIBLE);
        //String fileName = "android.resource://" + getPackageName() + "/" + R.raw.hodor1;
        mVideo.setVideoURI(Uri.parse(path));
        mVideo.start();

        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mVideo.setVisibility(View.INVISIBLE);
            }
        });

        /*MediaPlayer player = MediaPlayer.create(this,R.raw.hodor1);
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
