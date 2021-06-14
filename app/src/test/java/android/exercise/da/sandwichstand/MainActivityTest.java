package android.exercise.da.sandwichstand;

import android.app.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28}, application = Application.class)
public class MainActivityTest {
    @Test
    public void testo(){
        assertTrue(1==1);
    }
}