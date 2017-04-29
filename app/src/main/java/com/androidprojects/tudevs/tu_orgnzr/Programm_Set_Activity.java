package com.androidprojects.tudevs.tu_orgnzr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.FridayFragment;
import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.MondayFragment;
import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.ThursdayFragment;
import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.TuesdayFragment;
import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.WednesdayFragment;

public class Programm_Set_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programm__set_);

        ProgramPagerAdapter pagerAdapter = new ProgramPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.Programm_ViewPager);
        viewPager.setAdapter(pagerAdapter);
    }

    public class ProgramPagerAdapter extends FragmentPagerAdapter {

        public ProgramPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MondayFragment();
                case 1:
                    return new TuesdayFragment();
                case 2:
                    return new WednesdayFragment();
                case 3:
                    return new ThursdayFragment();
                case 4:
                    return new FridayFragment();
                default:
                    return null;
            }
        }
    }
}
