package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;
import de.uni_koeln.phil_fak.spinfo.javamobile.picman.data.PicItem;


public class FullScreenActivity extends FragmentActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private List<PicItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        Intent intent = getIntent();
        items = (List<PicItem>) intent.getSerializableExtra("items");

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //Adapter
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = new ScreenSlidePageFragment();

            Bundle b = new Bundle();
            b.putParcelable("img", BitmapFactory.decodeFile(items.get(position).getData(PicItem.ITEM_IMAGE_PATH)));
            b.putString("text", items.get(position).getDisplayString());
            f.setArguments(b);

            return f;
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }
}