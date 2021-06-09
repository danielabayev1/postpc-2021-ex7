package android.exercise.da.sandwichstand;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderApplication extends Application {


    private SharedPreferences sp;
    private static OrderApplication instance;
    private FireBaseManager fbm;

    public FireBaseManager getFbm() {
        return this.fbm;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.sp = this.getSharedPreferences("sandwich_db", Context.MODE_PRIVATE);
        System.out.println("----"+sp.getString("order_id", null));
        this.fbm = new FireBaseManager(this.sp);
    }

    public static OrderApplication getInstance() {
        return instance;
    }

}

