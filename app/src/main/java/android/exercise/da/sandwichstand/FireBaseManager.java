package android.exercise.da.sandwichstand;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class FireBaseManager {
    private SharedPreferences sp;
    private FirebaseFirestore fb;
    private Order currentOrder = null;
    private String orderId=null;
    MutableLiveData<Order> liveData;
    private String status = null;
    private ListenerRegistration listener = null;

    public FireBaseManager(SharedPreferences sp) {
        this.sp = sp;
        this.fb = FirebaseFirestore.getInstance();
        this.liveData = new MutableLiveData<>();
        this.liveData.setValue(null);
        initFromSp();
    }

    private void initFromSp() {
         this.orderId = sp.getString("order_id", null);
         this.listener = fb.collection("orders").document(this.orderId)
                 .addSnapshotListener(new EventListener<DocumentSnapshot>() {
             @Override
             public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                 if (error != null) {
                     System.out.println("error in listener");
                 } else if (value == null) {
                     System.out.println("val==null");
                     //don't know yet
                 } else if (!value.exists()) {
                     System.out.println("val not exist");
                     //todo open newOrder
                 } else {
                     Order order = value.toObject(Order.class);
                     liveData.setValue(order);
                     }
             }
         });
    }

    public void newOrder(Order order) {
        fb.collection("orders").document(order.getId()).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("order_id", order.getId());
                editor.apply();
            }
        });
        liveData.setValue(order);
    }

    public void updateOrder(Order order){
        fb.collection("orders").document(order.getId()).set(order);
        liveData.setValue(order);
    }

    public void deleteOrder(Order order){

    }

    public String getCurrentOrderStatus() {
        return status;
    }

    public LiveData<Order> getCurrentOrder(){
        return liveData;
    }

    public LiveData<Order> getCurrentOrderLiveData() {
        String orderId = sp.getString("order_id", null);
        if (orderId != null) {
            this.fb.collection("orders").document(orderId).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            currentOrder = documentSnapshot.toObject(Order.class);
                            status = currentOrder.getStatus();
                            liveData.setValue(currentOrder);
                        }
                    });
        }
        return liveData;
    }
    public DocumentReference getOrderRef(){
        return fb.collection("orders").document("6444da5e-b2cf-4876-9cb9-9e1c2c68b122");
    }

}



