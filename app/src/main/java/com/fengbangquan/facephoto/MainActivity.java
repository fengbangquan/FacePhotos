package com.fengbangquan.facephoto;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fengbangquan.facephoto.cache.Cache;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements PhotoFragment.ShowViewPagerListener {

    private PhotoFragment mPhotoFragment;
    private final static String FACES_CACHE = "FacesCache";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCache();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        mPhotoFragment = new PhotoFragment();
        mPhotoFragment.setShowViewPagerListener(this);
        fragmentTransaction.add(R.id.fragments_content, mPhotoFragment, "PhotoFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void showViewPager(int position, String uriString) {
        PhotoPagerFragment photoPagerFragment = new PhotoPagerFragment();
        Bundle args = new Bundle();
        args.putInt("vPosition", position);
        args.putString("vUriSting", uriString);
        photoPagerFragment.setArguments(args);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragments_content, photoPagerFragment, "PhotoPagerFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Uses DiskCache to save photos' paths having human faces
     * and faces' id that returns from Face++ website
     */
    private void initCache() {
        long maxDiskSize = 1024*1024*10;/*bytes*/
        int maxMemorySize = 20;/*just objects' counts*/
        File directory = new File(getExternalCacheDir(), FACES_CACHE);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            Cache.open(directory, 1, 1, maxDiskSize, maxMemorySize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
