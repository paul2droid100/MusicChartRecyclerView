package com.example.paulo.assignment2again;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.example.paulo.assignment2again.Fragments.Fragment1;
import com.example.paulo.assignment2again.Fragments.Fragment2;
import com.example.paulo.assignment2again.Fragments.Fragment3;
import io.realm.Realm;



public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment1 fragment1 = new Fragment1();
                    swapFragment(fragment1,"Fragment Rock");
                    return true;
                case R.id.navigation_dashboard:
                    Fragment2 fragment2 = new Fragment2();
                    swapFragment(fragment2,"Fragment Classic");
                    return true;
                case R.id.navigation_notifications:
                    Fragment3 fragment3 = new Fragment3();
                    swapFragment(fragment3,"Fragment Pop");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        Fragment1 fragment1 = new Fragment1();
        swapFragment(fragment1, "Fragment 1");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void swapFragment(Fragment fragment, String tag){
        getFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment, tag).commit();

    }

}
