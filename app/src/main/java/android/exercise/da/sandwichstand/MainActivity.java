package android.exercise.da.sandwichstand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    public String orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String orderStatus = OrderApplication.getInstance().getFbm().getCurrentOrderStatus();

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
                Intent ready = new Intent(MainActivity.this, OrderIsReadyActivity.class);
                startActivity(ready);
                finish();
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
    }
}