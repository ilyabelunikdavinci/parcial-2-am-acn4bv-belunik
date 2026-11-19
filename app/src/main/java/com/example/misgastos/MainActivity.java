package com.example.misgastos;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner spCategoria;
    Button btnAdd;
    Button btnResumen;
    EditText edDescription;
    EditText edAmount;
    EditText edComentario;
    TextView txtTotal;
    LinearLayout layoutMovimientos;

    double total = 0;

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
        txtTotal = findViewById(R.id.txtTotal);
        layoutMovimientos = findViewById(R.id.layoutMovimientos);
        edComentario = findViewById(R.id.edComentario);

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String elegida = parent.getItemAtPosition(position).toString();
                if (elegida.equals("Otros")) {
                    edComentario.setVisibility(View.VISIBLE);
                } else {
                    edComentario.setVisibility(View.GONE);
                    edComentario.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnAdd.setOnClickListener(v -> agregarGasto());

        btnResumen = findViewById(R.id.btnResumen);
        btnResumen.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResumenActivity.class);
            startActivity(intent);
        });


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

        double amount;
        try {
            amount = Double.parseDouble(texto.replace(",", "."));
        } catch (NumberFormatException e) {
            edAmount.setError("Monto inválido");
            return;
        }
        if (amount <= 0) {
            edAmount.setError("El monto debe ser mayor a 0");
            return;
        }

        String categoria = spCategoria.getSelectedItem().toString();

        TextView movimiento = new TextView(this);
        String comentario = edComentario.getText().toString().trim();
        String linea = categoria + " · " + description;
        if (!comentario.isEmpty()) {
            linea = linea + " (" + comentario + ")";
        }
        movimiento.setText(linea + "    " + formatear(amount));
        movimiento.setTextSize(16);
        movimiento.setPadding(0, 8, 0, 8);
        layoutMovimientos.addView(movimiento);

        total = total + amount;
        txtTotal.setText(formatear(total));

        Toast.makeText(this, "Gasto agregado", Toast.LENGTH_SHORT).show();

        edDescription.setText("");
        edAmount.setText("");
        edComentario.setText("");
        spCategoria.setSelection(0);
    }

    private String formatear(double valor) {
        return "$ " + String.format(Locale.forLanguageTag("es-AR"), "%,.2f", valor);
    }
}