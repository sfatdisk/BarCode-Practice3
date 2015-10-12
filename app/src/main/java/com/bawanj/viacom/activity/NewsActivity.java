package com.bawanj.viacom.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bawanj.viacom.R;
import com.bawanj.viacom.fragment.NewsRandomFragment;

public class NewsActivity extends AppCompatActivity {

    private CheckBox mCheckBox;
    private boolean swipeMode= false; // default Mode is primary mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        final FragmentManager fm= getSupportFragmentManager();
        Fragment fragment= fm.findFragmentById(R.id.fragment_container);

        if( fragment == null ){
            // default primary mode
            fragment= NewsRandomFragment.newInstance(swipeMode);

            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }

        // don't have to handle checkbox during configuration
        mCheckBox= (CheckBox)findViewById(R.id.switch_mode);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked ) {
                getSupportFragmentManager()
                          .beginTransaction()
                          .replace(R.id.fragment_container, NewsRandomFragment.newInstance(isChecked))
                          .commit();
            }

        });
    }
}
