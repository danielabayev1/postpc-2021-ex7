package android.exercise.da.sandwichstand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class InTheMakingActivity extends AppCompatActivity {
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_the_making);

        if (savedInstanceState != null) {
            this.counter = savedInstanceState.getInt("counter");
        }

        FireBaseManager fbm = OrderApplication.getInstance().getFbm();
        LiveData<Order> currentOrder = fbm.getCurrentOrder();
        currentOrder.observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                if (order != null) {
                    String orderStatus = order.getStatus();
                    if (!orderStatus.equals("in-progress")) {
                        counter += 1;
                        System.out.println("----call main from in-progress");
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.putInt("counter", this.counter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sendBroadcast(new Intent("backspace_pressed"));
    }
}