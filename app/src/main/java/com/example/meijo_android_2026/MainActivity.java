package com.example.meijo_android_2026;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meijo_android_2026.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prefDataStore = PrefDataStore.getInstance(this);
        prefDataStore.getString("text")
                .ifPresent(text -> {
                    if ("a".equals(text)) {
                        binding.textView.setText("Aの画像");
                        binding.imageView.setImageResource(R.drawable.ic_add_home);
                    } else if ("b".equals(text)) {
                        binding.textView.setText("Bの画像");
                        binding.imageView.setImageResource(R.drawable.ic_add_location);
                    } else {
                        binding.textView.setText("知らない画像");
                    }
                });

        binding.changeButton.setOnClickListener(view -> {
            String text = binding.editTextText.getText().toString();
            binding.textView.setText(text);
        });

        binding.saveButton.setOnClickListener(view -> {
            String text = binding.editTextText.getText().toString();
            if ("a".equals(text)) {
                binding.imageView.setImageResource(R.drawable.ic_add_home);
            } else if ("b".equals(text)) {
                binding.imageView.setImageResource(R.drawable.ic_add_location);
            } else {
                text = "unknown";
            }
            prefDataStore.setString("text", text);
        });
    }
}