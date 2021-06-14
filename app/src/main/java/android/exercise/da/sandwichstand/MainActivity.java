package android.exercise.da.sandwichstand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class MainActivity extends AppCompatActivity {
    int i;
    private BroadcastReceiver br = null;
    public FireBaseManager fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.fb == null) {
            this.fb = OrderApplication.getInstance().getFbm();
        }
        LiveData<Order> orderLiveData = fb.getCurrentOrder();
        orderLiveData.observe(this, order -> {
            //todo handle coming to new order after finishing last order
//            String lastOrder = OrderApplication.getInstance().getFbm().getLastOrderId();
            String lastOrder = this.fb.getLastOrderId();
            if (lastOrder.equals("")) {
//                System.out.println("----new Order from main " + i);
                i += 1;
                Intent newOrder = new Intent(MainActivity.this, NewOrderActivity.class);
                startActivity(newOrder);

            } else if (order != null) {
                String orderStatus = order.getStatus();
                if ("waiting".equals(orderStatus)) {
//                    System.out.println("----waiting from Main");
                    Intent editOrder = new Intent(MainActivity.this, EditOrderActivity.class);
                    startActivity(editOrder);
                } else if ("in-progress".equals(orderStatus)) {
//                    System.out.println("----In progress from main");
                    Intent inTheMaking = new Intent(MainActivity.this, InTheMakingActivity.class);
                    startActivity(inTheMaking);
                } else if ("ready".equals(orderStatus)) {
//                    System.out.println("----ready from Main");
                    Intent ready = new Intent(MainActivity.this, OrderIsReadyActivity.class);
                    startActivity(ready);
                }
            }
        });
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent incomingIntent) {
                if (incomingIntent == null || !incomingIntent.getAction().equals("backspace_pressed")) {
                    return;
                } else {
//                    System.out.println("----back");
                    finish();
                }

            }
        };
        registerReceiver(br, new IntentFilter("backspace_pressed"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(br);
    }
}

