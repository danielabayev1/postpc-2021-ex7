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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_is_ready);

        Button gotIt = findViewById(R.id.gotItButton);

        FireBaseManager fbm = OrderApplication.getInstance().getFbm();

        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        fbm.markOrderDone();
                        finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        sendBroadcast(new Intent("backspace_pressed"));
        super.onBackPressed();
    }
}