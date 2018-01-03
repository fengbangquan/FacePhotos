package com.fengbangquan.facephoto;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements PhotoFragment.ShowViewPagerListener {

    private PhotoFragment mPhotoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
