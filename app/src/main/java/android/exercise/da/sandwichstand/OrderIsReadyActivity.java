package android.exercise.da.sandwichstand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class OrderIsReadyActivity extends AppCompatActivity {
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_is_ready);
        if (savedInstanceState != null) {
            this.counter = savedInstanceState.getInt("counter");
        }
        Button gotIt = findViewById(R.id.gotItButton);

        FireBaseManager fbm = OrderApplication.getInstance().getFbm();
//        LiveData<Order> currentOrder = fbm.getCurrentOrder();
//        currentOrder.observe(this, new Observer<Order>() {
//            @Override
//            public void onChanged(Order order) {
//                if (order != null) {
//                    String orderStatus = order.getStatus();
//                    if (!orderStatus.equals("ready") && counter == 0) {
//                        counter += 1;
//                        System.out.println("----call main from Ready");
//                        Intent newOrder = new Intent(OrderIsReadyActivity.this, MainActivity.class);
//                        startActivity(newOrder);
//                        finish();
//                    }
//                }
//            }
//        });
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        fbm.markOrderDone();
                        finish();
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