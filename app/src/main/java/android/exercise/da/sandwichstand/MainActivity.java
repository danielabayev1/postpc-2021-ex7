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
    public Order myOrder = null;
    private BroadcastReceiver br = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LiveData<Order> orderLiveData = OrderApplication.getInstance().getFbm().getCurrentOrder();
        orderLiveData.observe(this, order -> {
            //todo handle coming to new order after finishing last order
            if (OrderApplication.getInstance().getFbm().getLastOrderId().equals("")) {
                System.out.println("----new Order from main " + i);
                i += 1;
                Intent newOrder = new Intent(MainActivity.this, NewOrderActivity.class);
                startActivity(newOrder);
//                finish();
            } else if (order != null) {
                myOrder = order;
                String orderStatus = order.getStatus();
                if ("waiting".equals(orderStatus)) {
                    System.out.println("----waiting from Main");
                    Intent editOrder = new Intent(MainActivity.this, EditOrderActivity.class);
                    startActivity(editOrder);
//                    finish();
                } else if ("in-progress".equals(orderStatus)) {
                    System.out.println("----In progress from main");
                    Intent inTheMaking = new Intent(MainActivity.this, InTheMakingActivity.class);
                    startActivity(inTheMaking);
//                    finish();
                } else if ("ready".equals(orderStatus)) {
                    System.out.println("----ready from Main");
                    Intent ready = new Intent(MainActivity.this, OrderIsReadyActivity.class);
                    startActivity(ready);
//                    finish();
                }
            }
        });
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent incomingIntent) {
                if (incomingIntent == null || !incomingIntent.getAction().equals("backspace_pressed")) {
                    return;
                }

                else{
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

/* todo read from fb one document*/
//        FirebaseFirestore fb = FirebaseFirestore.getInstance();
//        fb.collection("orders").document("81HwjDmVwZRrJre8ZlIz").get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Order order = documentSnapshot.toObject(Order.class);
//                order.stringMe();
//                text.setText(order.id);
//            }
//        });
