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
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.StorageManager;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.TimeStamper;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity.util.Toaster;


public class PicDetailsActivity extends ActionBarActivity {

    private Bitmap pic;
    private String pic_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_details);

        Intent intent = getIntent();
        pic = (Bitmap)intent.getParcelableExtra("pic");

        ImageView imageView = (ImageView)findViewById(R.id.image_view);
        imageView.setImageBitmap(pic);

        TextView text = (TextView)findViewById(R.id.pic_details_timestamp);
        pic_details = TimeStamper.getInstance().generateTimestamp(true);

        text.setText(pic_details);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pic_details, menu);
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

    public void saveImage(View view) {
        ImageHelper imageHelper = ImageHelper.getInstance();
        StorageManager storageManager = new StorageManager(getApplicationContext());
        imageHelper.saveImageData(getApplicationContext(), storageManager, getPicture(), getText());
        Intent intent = new Intent(this, PicManActivity.class);
        startActivity(intent);
        Toaster.toastWrap(getApplicationContext(), "Image saved...");
        finish();
    }

    public Bitmap getPicture() {
        return pic;
    }

    public String getText() {
        TextView comment = (TextView) findViewById(R.id.pic_details_comment);
        return pic_details += " - " + comment.getText();
    }
}









































