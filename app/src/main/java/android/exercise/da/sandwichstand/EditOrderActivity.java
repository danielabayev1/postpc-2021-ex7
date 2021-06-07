package android.exercise.da.sandwichstand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class EditOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        FireBaseManager fbm = OrderApplication.getInstance().getFbm();
        Order currentOrder = fbm.getCurrentOrder();

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

        customerName.setText(currentOrder.getCustomerName());
        pickleCounter.setText(String.valueOf(currentOrder.getPickles()));
        hummus.setChecked(currentOrder.isHummus());
        tahini.setChecked(currentOrder.isTahini());
        comment.setText(currentOrder.getComment());


    }
}