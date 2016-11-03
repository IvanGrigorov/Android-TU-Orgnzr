package com.androidprojects.tudevs.tu_orgnzr;

import android.util.Log;

import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.FridayFragment;
import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.MondayFragment;
import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.ThursdayFragment;
import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.TuesdayFragment;
import com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments.WednesdayFragment;
import com.androidprojects.tudevs.tu_orgnzr.Programm_Set_Activity;

import org.junit.Assert;

import org.junit.Test;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Ivan Grigorov on 07/08/2016.
 *
 * Testing the Program PagerAdapter
 * andrey.andreev05@gmail.com
 */

@RunWith(Enclosed.class)
public class ProgramPagerAdapterTest {

    public static class ProgramPagerAdapterNonParameterizedTest {

        @Test
        public void ProgramPagerAdapter_GetCount_ShouldReturn5AsNumberOfPages() {

            // Arrange
            Programm_Set_Activity prgrammSetActivity = new Programm_Set_Activity();

            Programm_Set_Activity.ProgramPagerAdapter pagerAdapter = prgrammSetActivity.new ProgramPagerAdapter(prgrammSetActivity.getSupportFragmentManager());

            // Act

            int excpectedValue = 5;

            // Assert
            Assert.assertEquals(excpectedValue, pagerAdapter.getCount());
        }

        @Test
        public void ProgramPagerAdapter_GetItem_ShouldReturnNullWhenGivingInvalidPositionOfFragment() {
            // Arrange
            Programm_Set_Activity programmSetActivity = new Programm_Set_Activity();

            Programm_Set_Activity.ProgramPagerAdapter pagerAdapter = programmSetActivity.new ProgramPagerAdapter(programmSetActivity.getSupportFragmentManager());

            // Arrange
            int invalidPosition = 6;

            //Assert
            Assert.assertEquals(null, pagerAdapter.getItem(invalidPosition));
        }
    }

    @RunWith(Parameterized.class)
    public static class ProgramPagerAdapterParameterizedTest {

        @Parameterized.Parameters
        public static Collection<Object[]> parameters_GetItem_Test() {
            return Arrays.asList(new Object[][]{
                    {0, MondayFragment.class}, {1, TuesdayFragment.class},
                    {2, WednesdayFragment.class}, {3, ThursdayFragment.class},
                    {4, FridayFragment.class}
            });
        }

        @Parameterized.Parameter(value = 0)
        public /* NOT private */ int fInput;

        @Parameterized.Parameter(value = 1)
        public /* NOT private */ Class fExpected;

        @Test
        public void ProgramPagerAdapter_GetItem_ShouldReturnCorrectFragment() {

            // Arrange
            Programm_Set_Activity prgrammSetActivity = new Programm_Set_Activity();

            Programm_Set_Activity.ProgramPagerAdapter pagerAdapter = prgrammSetActivity.new ProgramPagerAdapter(prgrammSetActivity.getSupportFragmentManager());

            // Assert
            org.junit.Assert.assertEquals(fExpected.getName(), pagerAdapter.getItem(fInput).getClass().getName());


        }

    }

}
