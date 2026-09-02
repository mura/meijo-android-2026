package com.example.meijo_android_2026;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meijo_android_2026.databinding.ActivityMain2Binding;

import java.util.Optional;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case RESULT_OK -> {
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(text -> "Result: " + text)
                                .ifPresent(text -> binding.textResult.setText(text));
                    }
                    case RESULT_CANCELED -> {
                        binding.textResult.setText("Result: Canceled");
                    }
                    default -> {
                        var text = "Result: Unknown(" + result.getResultCode() + ")";
                        binding.textResult.setText(text);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.buttonA.setOnClickListener(view -> {
            // 明示的Intent
            var intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
        });

        binding.buttonB.setOnClickListener(view -> {
            // 暗黙的Intent
            var intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.yahoo.co.jp/"));
            startActivity(intent);
        });

        binding.buttonSend.setOnClickListener(view -> {
            // 送信ボタン
            var intent = new Intent(this, MainActivity3.class);
            // Extra の "text" に EditText の中身を入れる
            var text = binding.editText.getText().toString();
            intent.putExtra("text", text);
            startActivity(intent);
        });

        binding.buttonLaunch.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity3.class);
            intent.putExtra("text", "起動ボタンを押した");
            getActivityResult.launch(intent);
        });
    }
}