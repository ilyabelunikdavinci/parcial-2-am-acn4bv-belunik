package com.example.misgastos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class ResumenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resumen);

        TextView txtTotalResumen = findViewById(R.id.txtTotalResumen);
        LinearLayout layoutResumen = findViewById(R.id.layoutResumen);
        Button btnVolver = findViewById(R.id.btnVolver);

        // 1. Достаём данные из посылки (твои строки!)
        ArrayList<String> categorias = getIntent().getStringArrayListExtra("categorias");
        double[] montos = getIntent().getDoubleArrayExtra("montos");
        double total = getIntent().getDoubleExtra("total", 0);

        txtTotalResumen.setText(formatear(total));

        // 2. Если трат ещё нет — показываем сообщение
        if (categorias == null || categorias.isEmpty()) {
            TextView vacio = new TextView(this);
            vacio.setText("Todavía no hay gastos cargados");
            vacio.setTextSize(16);
            layoutResumen.addView(vacio);
        } else {
            // 3. Складываем суммы по категориям
            LinkedHashMap<String, Double> sumas = new LinkedHashMap<>();
            for (int i = 0; i < categorias.size(); i++) {
                String cat = categorias.get(i);
                double anterior = sumas.getOrDefault(cat, 0.0);
                sumas.put(cat, anterior + montos[i]);
            }

            // 4. Для каждой категории — строка и полоска
            for (Map.Entry<String, Double> fila : sumas.entrySet()) {
                String cat = fila.getKey();
                double suma = fila.getValue();
                int porcentaje = (int) Math.round(suma * 100 / total);

                // Строка: «Comida (40%)  ........  $ 1.200,00»
                LinearLayout filaLayout = new LinearLayout(this);
                filaLayout.setOrientation(LinearLayout.HORIZONTAL);
                filaLayout.setPadding(0, 24, 0, 4);

                TextView txtCat = new TextView(this);
                txtCat.setText(cat + " (" + porcentaje + "%)");
                txtCat.setTextSize(16);
                txtCat.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                TextView txtSuma = new TextView(this);
                txtSuma.setText(formatear(suma));
                txtSuma.setTextSize(16);

                filaLayout.addView(txtCat);
                filaLayout.addView(txtSuma);

                // Полоска: насколько заполнена = процент
                ProgressBar barra = new ProgressBar(this, null,
                        android.R.attr.progressBarStyleHorizontal);
                barra.setMax(100);
                barra.setProgress(porcentaje);

                layoutResumen.addView(filaLayout);
                layoutResumen.addView(barra);
            }
        }

        // 5. Кнопка «Volver»
        btnVolver.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String formatear(double valor) {
        return "$ " + String.format(Locale.forLanguageTag("es-AR"), "%,.2f", valor);
    }
}