package com.slatvinskiy.formater.ui;

import android.os.Build;

import com.slatvinskiy.formater.BuildConfig;
import com.slatvinskiy.formater.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class MainActivityTest {

    private MainActivity mainActivity;
    private MainPresenter mainPresenter;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        mainPresenter = new MainPresenter();
    }

    @Test
    public void testString1_isCorrect() throws Exception {

        String str = mainActivity.getString(R.string.test_string_1);
        mainPresenter.processText(str).test()
                .assertNoErrors()
                .assertValue("With its powerful tools and dazzling effects, keynote makes it easy to create stunning and memorable presentations.")
                .assertComplete();
    }

    @Test
    public void testString2_isCorrect() throws Exception {

        String str = mainActivity.getString(R.string.test_string_2);
        mainPresenter.processText(str).test()
                .assertNoErrors()
                .assertValue("See who you’re working with... While you’re editing, a separate list lets you quickly see who else is in the presentation.")
                .assertComplete();
    }

    @Test
    public void testString3_isCorrect() throws Exception {

        String str = mainActivity.getString(R.string.test_string_3);
        mainPresenter.processText(str).test()
                .assertNoErrors()
                .assertValue("Hi there!!!? Can we do something with it? And more...! There is my device! Take it easy, be happy and have a fun! Test, commas, here. You’re so happy.")
                .assertComplete();
    }
}