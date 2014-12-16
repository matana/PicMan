package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.ImageHelper;

public class FullScreenActivity extends Activity {

    private ImageView imageView;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        imageView = (ImageView)findViewById(R.id.fullscreen_image_view);
        text = (TextView)findViewById(R.id.fullscreen_text_view);
    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        String desc = intent.getStringExtra("desc");

        ImageHelper imageHelper = ImageHelper.getInstance();
        Bitmap thumbnail = imageHelper.getWindowSizedBitmapFromPath(uri, getWindowManager());

        imageView.setImageBitmap(thumbnail);
        text.setText(desc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.full_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
