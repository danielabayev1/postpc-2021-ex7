package test.android.exercise.da.sandwichstand;

import android.app.Application;
import android.content.Intent;
import android.exercise.da.sandwichstand.EditOrderActivity;
import android.exercise.da.sandwichstand.FireBaseManager;
import android.exercise.da.sandwichstand.InTheMakingActivity;
import android.exercise.da.sandwichstand.MainActivity;
import android.exercise.da.sandwichstand.NewOrderActivity;
import android.exercise.da.sandwichstand.Order;
import android.exercise.da.sandwichstand.OrderIsReadyActivity;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28}, application = Application.class)
public class MainActivityFlowTest {

    private ActivityController<MainActivity> activityController;
    private MainActivity activityUnderTest;
    private FireBaseManager mockFireBase;

    /**
     * initialize main activity with a mock FireBaseManager
     */
    @Before
    public void setup() {
        mockFireBase = Mockito.mock(FireBaseManager.class);
        activityController = Robolectric.buildActivity(MainActivity.class);
        activityUnderTest = activityController.get();
        activityUnderTest.fb = mockFireBase;

    }

    @Test
    public void when_noCurrentOrder_then_openNewOrderActivity() {
        //setup
        Mockito.when(mockFireBase.getLastOrderId()).thenReturn("");
        Mockito.when(mockFireBase.getCurrentOrder()).thenReturn(new MutableLiveData<Order>(null));
        // test
        activityController.create().start().resume();
        Intent expectedIntent = new Intent(activityUnderTest, NewOrderActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();

        //verify
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void when_currentOrderInStatusWaiting_then_openEditOrderActivity(){
        //setup
        Order order = new Order("123","dani",3,true,true,"","waiting");
        Mockito.when(mockFireBase.getLastOrderId()).thenReturn("123");
        Mockito.when(mockFireBase.getCurrentOrder()).thenReturn(new MutableLiveData<Order>(order));

        // test
        activityController.create().start().resume();
        Intent expectedIntent = new Intent(activityUnderTest, EditOrderActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();

        //verify
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
    @Test
    public void when_currentOrderInStatusInProgress_then_openInProgressActivity(){
        //setup
        Order order = new Order("123","dani",3,true,true,"","in-progress");
        Mockito.when(mockFireBase.getLastOrderId()).thenReturn("123");
        Mockito.when(mockFireBase.getCurrentOrder()).thenReturn(new MutableLiveData<Order>(order));

        // test
        activityController.create().start().resume();
        Intent expectedIntent = new Intent(activityUnderTest, InTheMakingActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();

        //verify
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void when_currentOrderIsReady_then_openOrderIsReadyActivity(){
        //setup
        Order order = new Order("123","dani",3,true,true,"","ready");
        Mockito.when(mockFireBase.getLastOrderId()).thenReturn("123");
        Mockito.when(mockFireBase.getCurrentOrder()).thenReturn(new MutableLiveData<Order>(order));

        // test
        activityController.create().start().resume();
        Intent expectedIntent = new Intent(activityUnderTest, OrderIsReadyActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();

        //verify
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}