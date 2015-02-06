package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.ImageHelper;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.TimeStamper;


public class PicDetailsActivity extends ActionBarActivity {

    private Bitmap thumbnail;
    private String pic_details;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_details);

        Intent intent = getIntent();
        uri = intent.getStringExtra("uri");

        ImageView imageView = (ImageView)findViewById(R.id.image_view);
        ImageHelper imageHelper = ImageHelper.getInstance();
        thumbnail = imageHelper.getThumbnailBitmapFromPath(uri);
        imageView.setImageBitmap(thumbnail);

        TextView text = (TextView)findViewById(R.id.pic_details_timestamp);
        pic_details = TimeStamper.getInstance().generateTimestamp(true);
        text.setText(pic_details);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pic_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void saveImage(View view) {
        ImageHelper imageHelper = ImageHelper.getInstance();
        imageHelper.saveImageData(getApplicationContext(),
                TimeStamper.getInstance().generateTimestamp(false),
                uri,
                thumbnail,
                getText(),
                TimeStamper.getInstance().generateTimestamp(true));
        Intent intent = new Intent(this, PicManActivity.class);
        startActivity(intent);
        finish();
    }

    public String getText() {
        TextView comment = (TextView) findViewById(R.id.pic_details_comment);
        return comment.getText().toString();
    }
}









































