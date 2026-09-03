package com.example.meijo_android_2026;

import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meijo_android_2026.databinding.ActivityMain6Binding;

import java.util.stream.Collectors;

public class MainActivity6 extends AppCompatActivity {

    private ActivityMain6Binding binding;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- ここから ---
        connectivityManager = getSystemService(ConnectivityManager.class);
        // 起動時にネットワークの状態を更新
        // updateNetwork();

        binding.buttonUpdate.setOnClickListener(view -> {
            // ボタンを押したら状態を更新
            updateNetwork();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // アプリの画面が表示されたら更新
        updateNetwork();
    }

    private void updateNetwork() {
        var currentNetwork = connectivityManager.getActiveNetwork();
        updateTransport(currentNetwork);
        updateIpAddress(currentNetwork);
    }

    private void updateTransport(Network network) {
        var caps = connectivityManager.getNetworkCapabilities(network);

        if (caps != null) {
            String transport;
            if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                transport = "モバイル通信";
            } else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                transport = "WiFi";
            } else {
                transport = "その他";
            }

            binding.textTransport.setText(transport);
        }
    }

    private void updateIpAddress(Network network) {
        var linkProperties = connectivityManager.getLinkProperties(network);

        if (linkProperties != null) {
            var ipAddresses = linkProperties.getLinkAddresses()
                    .stream()
                    .map(LinkAddress::toString)
                    .collect(Collectors.joining("\n"));

            binding.textIpAddress.setText(ipAddresses);
        }
    }
}