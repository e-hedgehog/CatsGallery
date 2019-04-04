package com.ehedgehog.android.catsgallery;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CatsGalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CatsGalleryFragment.newInstance();
    }
}
