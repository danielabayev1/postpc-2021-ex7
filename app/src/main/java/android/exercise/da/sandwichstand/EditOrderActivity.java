package android.exercise.da.sandwichstand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class EditOrderActivity extends AppCompatActivity {
    Order myOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        FireBaseManager fbm = OrderApplication.getInstance().getFbm();
        LiveData<Order> currentOrder = fbm.getCurrentOrder();
        //find views
        EditText customerName = findViewById(R.id.editCustomerName);
        Button addPickle = findViewById(R.id.editAddPickle);
        Button removePickle = findViewById(R.id.editRemovePickle);
        TextView pickleCounter = findViewById(R.id.editPicklesCounter);
        CheckBox hummus = findViewById(R.id.editHummusCheckBox);
        CheckBox tahini = findViewById(R.id.editTahiniCheckBox);
        EditText comment = findViewById(R.id.editComment);
        Button cancelButton = findViewById(R.id.editCancelButton);
        Button saveButton = findViewById(R.id.editSaveButton);

        currentOrder.observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                if (order != null) {
                    //change details
                    customerName.setText(order.getCustomerName());
                    pickleCounter.setText(String.valueOf(order.getPickles()));
                    hummus.setChecked(order.isHummus());
                    tahini.setChecked(order.isTahini());
                    comment.setText(order.getComment());
                    myOrder = order;

                    String orderStatus = order.getStatus();
                    if (orderStatus == null || orderStatus.equals("done")) {
                        Intent newOrder = new Intent(EditOrderActivity.this, NewOrderActivity.class);
                        startActivity(newOrder);
                        finish();
                    } else {
                        if (orderStatus.equals("in-progress")) {
                            Intent inTheMaking = new Intent(EditOrderActivity.this, InTheMakingActivity.class);
                            startActivity(inTheMaking);
                            finish();
                        }
                        if (orderStatus.equals("ready")) {
                            System.out.println("from start");
                            Intent ready = new Intent(EditOrderActivity.this, OrderIsReadyActivity.class);
                            startActivity(ready);
                            finish();
                        }
                    }
                }
            }
        });

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
                    myOrder.setCustomerName(name);
                    myOrder.setHummus(hummus.isChecked());
                    myOrder.setTahini(tahini.isChecked());
                    myOrder.setPickles(Integer.parseInt(pickleCounter.getText().toString()));
                    myOrder.setComment(comment.getText().toString());
                    fbm.updateOrder(myOrder);
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbm.deleteOrder(myOrder);
            }
        });

    }

}
