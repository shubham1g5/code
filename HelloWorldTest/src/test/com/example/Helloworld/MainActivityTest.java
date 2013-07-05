package test.com.example.Helloworld;


import static org.hamcrest.CoreMatchers.equalTo;
import main.com.example.helloworld.MainActivity;
import main.com.example.helloworld.NewActivity;
import main.com.example.helloworld.R;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import android.content.Intent;
import android.widget.Button;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
	
	private MainActivity activity;
	private Button mButton;
	
	@Before
	public void setup() throws Exception{
		activity = Robolectric.buildActivity(MainActivity.class).create().get();
		mButton = (Button) activity.findViewById(R.id.button);
	}

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String hello = activity.getResources().getString(R.string.hello_world);
        assertThat(hello, equalTo("Hello world!"));
    }
    
    @Test
    public void buttonShouldSayPressMe() throws Exception {
    	assertThat((String) mButton.getText(), equalTo("press me"));
    }
    
    @Test
    public void pressingButtonShouldStartNewActivity() throws Exception{
    	mButton.performClick();
    	ShadowActivity mShadowActivty = Robolectric.shadowOf(activity);
    	Intent startedIntent = mShadowActivty.getNextStartedActivity();
    	ShadowIntent mShadowIntent = Robolectric.shadowOf(startedIntent);
    	assertThat(mShadowIntent.getComponent().getClassName(), equalTo(NewActivity.class.getName()));
    } 
    
    
}
