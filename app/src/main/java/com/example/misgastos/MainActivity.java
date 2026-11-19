package com.example.misgastos;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Spinner spCategoria;
    Button btnAdd;
    EditText edDescription;
    EditText edAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        spCategoria = findViewById(R.id.spCategoria);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);

        btnAdd = findViewById(R.id.btnAdd);
        edDescription = findViewById(R.id.edDescription);
        edAmount = findViewById(R.id.edAmount);

        btnAdd.setOnClickListener(v -> agregarGasto());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void agregarGasto() {
        String description = edDescription.getText().toString().trim();
        String texto = edAmount.getText().toString().trim();

        if (description.isEmpty()) {
            edDescription.setError("Escribí una descripción");
            return;
        }
        if (texto.isEmpty()) {
            edAmount.setError("Ingresá un monto");
            return;
        }

        Toast.makeText(this, description + " - " + texto, Toast.LENGTH_SHORT).show();

        edDescription.setText("");
        edAmount.setText("");
        spCategoria.setSelection(0);
    }
}