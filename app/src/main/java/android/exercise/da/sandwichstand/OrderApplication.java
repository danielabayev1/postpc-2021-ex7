package android.exercise.da.sandwichstand;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class OrderApplication extends Application {


    private SharedPreferences sp;
    private static OrderApplication instance;

    public SharedPreferences getCurrentOrder() {
        return sp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.sp = this.getSharedPreferences("sandwich_db", Context.MODE_PRIVATE);
    }

    public static OrderApplication getInstance() {
        return instance;
    }


}

