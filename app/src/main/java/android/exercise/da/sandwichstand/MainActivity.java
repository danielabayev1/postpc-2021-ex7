package android.exercise.da.sandwichstand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    public String orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = OrderApplication.getInstance().getCurrentOrder();

        String orderId = sp.getString("order_id", null);
        if (orderId == null) {
            Intent newOrder = new Intent(MainActivity.this, NewOrderActivity.class);
            startActivity(newOrder);
            finish();
        } else {
            FirebaseFirestore fb = FirebaseFirestore.getInstance();
            fb.collection("orders").document(orderId).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Order order = documentSnapshot.toObject(Order.class);
                            orderStatus = order.getStatus();
                            if (orderStatus.equals("waiting")) {
                                Intent editOrder = new Intent(MainActivity.this, EditOrderActivity.class);
                                startActivity(editOrder);
                            }
                        }
                    });

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
    }
}