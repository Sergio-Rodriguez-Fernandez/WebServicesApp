package com.example.webservices;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.webservices.api.ApiService;
import com.example.webservices.api.RetrofitClient;
import com.example.webservices.model.Report;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResults = findViewById(R.id.tvResults);
        apiService = RetrofitClient.getApiService();

        // Operación GET (Punto 2)
        fetchReportsFromServer();

        // Operación POST (Punto 3)
        sendNewReportToServer();
    }

    private void fetchReportsFromServer() {
        Call<List<Report>> call = apiService.getAllReports();
        call.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Report> reports = response.body();
                    StringBuilder builder = new StringBuilder();
                    for (Report report : reports) {
                        builder.append("ID: ").append(report.getId())
                                .append("\nMunicipio: ").append(report.getMunicipality())
                                .append("\nSíntomas: ").append(report.getSymptomSummary())
                                .append("\n\n");
                    }
                    tvResults.setText(builder.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
                tvResults.setText("Error de conexión: " + t.getMessage());
            }
        });
    }

    // AQUÍ ESTÁ EL CÓDIGO PARA EL PUNTO 3 (POST)
    private void sendNewReportToServer() {
        // Objeto de prueba para enviar al servidor
        Report newReport = new Report("Colima", "Fiebre leve", "Bajo");

        Call<Report> call = apiService.createReport(newReport);
        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Report createdReport = response.body();

                    // 1. Muestra un mensaje Toast en la pantalla del emulador con el ID generado (ej: 101)
                    Toast.makeText(MainActivity.this,
                            "POST Exitoso. Nuevo ID creado: " + createdReport.getId(),
                            Toast.LENGTH_LONG).show();

                    // 2. Registra la confirmación en la consola de Logcat
                    Log.d("API_POST_SUCCESS", "Reporte creado exitosamente con ID: " + createdReport.getId());
                } else {
                    Log.e("API_POST_ERROR", "Error al crear: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                Log.e("API_POST_FAILURE", "Fallo de red al enviar POST: " + t.getMessage());
            }
        });
    }
}