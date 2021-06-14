package android.exercise.da.sandwichstand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class NewOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

//        System.out.println("----from new order");

        //get db instance
        FireBaseManager fbm = OrderApplication.getInstance().getFbm();

        // find views
        EditText customerName = findViewById(R.id.customerName);
        Button addPickle = findViewById(R.id.addPickle);
        Button removePickle = findViewById(R.id.removePickle);
        TextView pickleCounter = findViewById(R.id.picklesCounter);
        CheckBox hummus = findViewById(R.id.hummusCheckBox);
        CheckBox tahini = findViewById(R.id.tahiniCheckBox);
        EditText comment = findViewById(R.id.comment);
        Button saveButton = findViewById(R.id.saveButton);

        customerName.setText(fbm.getLastCustomerName());

        addPickle.setOnClickListener(v -> {
            int pickles = Integer.parseInt(pickleCounter.getText().toString());
            if (pickles < 10) {
                pickleCounter.setText(String.valueOf(pickles + 1));
            }
        });

        removePickle.setOnClickListener(v -> {
            int pickles = Integer.parseInt(pickleCounter.getText().toString());
            if (pickles > 0) {
                pickleCounter.setText(String.valueOf(pickles - 1));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = customerName.getText().toString();
                if (!name.equals("")) {
                    String newId = UUID.randomUUID().toString();
                    boolean addHummus = hummus.isChecked();
                    boolean addTahini = tahini.isChecked();
                    int pickles = Integer.parseInt(pickleCounter.getText().toString());
                    String sComment = comment.getText().toString();
                    String status = "waiting";
                    Order newOrder = new Order(newId, name, pickles, addHummus, addTahini, sComment, status);
                    fbm.newOrder(newOrder);
//                    Intent editActivity = new Intent(NewOrderActivity.this, MainActivity.class);
//                    startActivity(editActivity);
                    finish();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        sendBroadcast(new Intent("backspace_pressed"));
        super.onBackPressed();

    }
}