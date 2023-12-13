package com.uasmobilev1;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uasmobilev1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public void setSelectedNavItem(int itemId) {
        binding.navigation.setSelectedItemId(itemId);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    int itemId = item.getItemId();

                    if (itemId == R.id.navigation_home) {
                        fragment = new com.uasmobilev1.HomeFragment();
                    }
                    else if (itemId == R.id.navigation_ticket) {
                        fragment = new TicketFragment();
                    }
                    else if (itemId == R.id.navigation_account) {
                        fragment = new com.uasmobilev1.AccountFragment();
                    }
                    if (fragment != null) {
                        switchFragment(fragment);
                        return true;
                    }

                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null) {
            if (binding.navigation != null) {
                binding.navigation.setSelectedItemId(R.id.navigation_home);
            }
        }
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_layout, fragment,
                        fragment.getClass().getSimpleName())
                .commit();
    }
}
