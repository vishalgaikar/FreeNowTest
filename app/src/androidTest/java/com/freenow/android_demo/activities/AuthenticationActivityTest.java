package com.freenow.android_demo.activities;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.freenow.android_demo.R;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;

@RunWith(AndroidJUnit4.class)
public class AuthenticationActivityTest extends TestCase {

    @Rule
    @JvmField
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            MainActivity.class
    );

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    private MainActivity mActivity = null;

    @Test
    public void clickLoginButton_opensLoginUi() {
        String username = "crazydog335";
        String password = "venture";
        onView(withId(R.id.edt_username)).perform(typeText(username));
        onView(withId(R.id.edt_password)).perform(typeText(password));

        onView(withId(R.id.btn_login)).perform(click());

        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + 90000;
        boolean matchingView = false;
        do{
            try {
                onView(withText("FREE NOW demo")).check(matches(isDisplayed()));
                matchingView = true;
                break;
            } catch (Exception exception) {
            }
            if (matchingView) {
                break;
            }

        } while (System.currentTimeMillis() < endTime && !matchingView);/* timeout happens*/
        onView(allOf(isAssignableFrom(AppCompatImageButton.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .perform(click());
        onView(withText("Logout")).perform(click());
    }
    @Test
    public void clickLoginButton_opensLoginUiInvalid() {
        String username = "chike";
        String password = "password";

        onView(withId(R.id.edt_username)).perform(typeText(username));
        onView(withId(R.id.edt_password)).perform(typeText(password));

        onView(withId(R.id.btn_login)).perform(click(),closeSoftKeyboard());

        onView(withText(R.string.message_login_fail)).check(matches(isDisplayed()));
    }
    @Test
    public void clickLoginButton_opensSearch() {

        mActivity = (MainActivity)activityRule.getActivity();
        String username = "crazydog335";
        String password = "venture";

        onView(withId(R.id.edt_username)).perform(typeText(username));
        onView(withId(R.id.edt_password)).perform(typeText(password));

        onView(withId(R.id.btn_login)).perform(click(),closeSoftKeyboard());
        onView(withId(R.id.textSearch)).perform(typeText("sa"),closeSoftKeyboard());

        onView(withText("Samantha Reed"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());
        onView(withText("Samantha Reed")).check(matches(isDisplayed()));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(allOf(isAssignableFrom(AppCompatImageButton.class),
                withParent(isAssignableFrom(Toolbar.class))))
                .perform(click());
        onView(withText("Logout")).perform(click());

    }

}
