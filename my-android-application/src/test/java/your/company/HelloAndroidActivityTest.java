import your.company.HelloAndroidActivity;
import your.company.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class HelloAndroidActivityTest {

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String appName = new HelloAndroidActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("my-android-application"));
    }
}