package android.exercise.da.sandwichstand;

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
    ListenerRegistration listener = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_the_making);
        FireBaseManager fbm = OrderApplication.getInstance().getFbm();
        LiveData<Order> currentOrder = fbm.getCurrentOrder();
        currentOrder.observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                if (order != null) {
                    String orderStatus = order.getStatus();
                    if (orderStatus == null || orderStatus.equals("done")) {
                        Intent newOrder = new Intent(InTheMakingActivity.this, NewOrderActivity.class);
                        startActivity(newOrder);
                        finish();
                    } else {
                        if (orderStatus.equals("ready")) {
                            System.out.println("from start");
                            Intent ready = new Intent(InTheMakingActivity.this, OrderIsReadyActivity.class);
                            startActivity(ready);
                            finish();
                        }
                    }
                }
            }
        });
    }
}