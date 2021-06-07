package android.exercise.da.sandwichstand;

import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBaseManager {
    private SharedPreferences sp;
    private FirebaseFirestore fb;
    private Order currentOrder = null;
    private String status = null;

    public FireBaseManager(SharedPreferences sp) {
        this.sp = sp;
        this.fb = FirebaseFirestore.getInstance();
        initializeFromSp();
    }

    public boolean newOrder(Order order) {
        fb.collection("orders").document(order.getId()).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("order_id", order.getId());
                editor.apply();
            }
        });
        return true;
    }

    public String getCurrentOrderStatus() {
        return status;
    }

    private void initializeFromSp() {
        String orderId = sp.getString("order_id", null);
        if (orderId != null) {
            this.fb.collection("orders").document(orderId).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            currentOrder = documentSnapshot.toObject(Order.class);
                            status = currentOrder.getStatus();
                        }
                    });
        }
    }

    public Order getCurrentOrder() {
        this.fb.collection("orders").document(currentOrder.getId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        currentOrder = documentSnapshot.toObject(Order.class);
                        status = currentOrder.getStatus();
                    }
                });
        return currentOrder;
    }


}
