package com.fengbangquan.facephoto;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements PhotoFragment.ShowViewPagerListener {

    private PhotoFragment mPhotoFragment;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        mFragmentTransaction = fm.beginTransaction();
        mPhotoFragment = new PhotoFragment();
        mPhotoFragment.setShowViewPagerListener(this);
        mFragmentTransaction.add(R.id.fragments_content, mPhotoFragment, "PhotoFragment");
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void showViewPager(int position, String uriString) {
        PhotoPagerFragment photoPagerFragment = new PhotoPagerFragment();
        Bundle args = new Bundle();
        args.putInt("vPosition", position);
        args.putString("vUriSting", uriString);
        photoPagerFragment.setArguments(args);
        mFragmentTransaction.add(R.id.fragments_content, photoPagerFragment, "PhotoPagerFragment");
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }
}
