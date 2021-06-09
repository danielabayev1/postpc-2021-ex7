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

/*todo understand the flow of new order, new order after done and adjust the livedata accordingly*/
public class FireBaseManager {
    private final SharedPreferences sp;
    private final FirebaseFirestore fb;
    private Order currentOrder = null;
    private String orderId = "";
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

    private void setListener() {
        if (orderId != null && !orderId.equals("")) {
            this.listener = fb.collection("orders").document(this.orderId)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                System.out.println("----error in listener");
                            } else if (value == null) {
                                System.out.println("----val==null");
                                //don't know yet
                            } else if (!value.exists()) {
                                System.out.println("----val not exist");
                                //todo open newOrder
                            } else {
                                Order order = value.toObject(Order.class);
                                System.out.println("---- from ld, status:" + order.getStatus());
                                if (order.getStatus().equals("done")) {
                                    orderId = "";
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("order_id", orderId);
                                    editor.apply();
                                    liveData.postValue(null);
                                } else {
                                        liveData.postValue(order);

                                }
                            }
                        }
                    });
        }
    }

    private void initFromSp() {
        this.orderId = sp.getString("order_id", null);
        setListener();

    }

    public void newOrder(Order order) {
        orderId = order.getId();
        fb.collection("orders").document(order.getId()).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("order_id", order.getId());
                editor.apply();
//                liveData.postValue(order);
                setListener();
            }
        });
    }

    public void updateOrder(Order order) {
        fb.collection("orders").document(order.getId()).set(order);
//        liveData.postValue(order);
    }

    public void deleteOrder(Order order) {

    }

    public void markOrderDone(){
        fb.collection("orders").document(orderId).update("status","done");
        orderId="";

    }

    public String getLastOrderId() {
        return orderId;
    }

    public LiveData<Order> getCurrentOrder() {
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
}



