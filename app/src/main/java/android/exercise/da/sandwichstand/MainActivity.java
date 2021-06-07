package android.exercise.da.sandwichstand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class MainActivity extends AppCompatActivity {
    public String orderStatus;
    public Order myOrder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LiveData<Order> orderLiveData = OrderApplication.getInstance().getFbm().getCurrentOrderLiveData();
        orderLiveData.observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                if (order != null) {
                    myOrder = order;
                    String orderStatus = order.getStatus();
                    if (orderStatus == null || orderStatus.equals("done")) {
                        Intent newOrder = new Intent(MainActivity.this, NewOrderActivity.class);
                        startActivity(newOrder);
                        finish();
                    } else {
                        if (orderStatus.equals("waiting")) {
                            Intent editOrder = new Intent(MainActivity.this, EditOrderActivity.class);
                            startActivity(editOrder);
                            finish();
                        }
                        if (orderStatus.equals("in-progress")) {
                            Intent inTheMaking = new Intent(MainActivity.this, InTheMakingActivity.class);
                            startActivity(inTheMaking);
                            finish();
                        }
                        if (orderStatus.equals("ready")) {
                            System.out.println("from start");
                            Intent ready = new Intent(MainActivity.this, OrderIsReadyActivity.class);
                            startActivity(ready);
                            finish();
                        }
                    }


                } else {
                    System.out.println("noooooooMain");
                }
            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
