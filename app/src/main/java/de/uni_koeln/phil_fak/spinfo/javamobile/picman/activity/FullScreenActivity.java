package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;

public class FullScreenActivity extends Activity {

    private ImageView imageView;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        imageView = (ImageView)findViewById(R.id.image_view);
        text = (TextView)findViewById(R.id.pic_details_timestamp);
    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        Bitmap pic = intent.getParcelableExtra("pic");
        String desc = intent.getStringExtra("desc");

        imageView.setImageBitmap(pic);
        text.setText(desc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.full_screen, menu);
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
