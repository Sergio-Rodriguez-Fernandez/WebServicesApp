package com.example.webservices.api;

import com.example.webservices.model.Report;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface ApiService {
    // GET: Obtener todos los reportes de monitoreo
    @GET("posts")
    Call<List<Report>> getAllReports();

    // GET: Obtener un reporte específico por su ID
    @GET("reports/{id}")
    Call<Report> getReportById(@Path("id") int reportId);

    // POST: Crear una nueva entrada de reporte
    @POST("posts")
    Call<Report> createReport(@Body Report report);

    // DELETE: Eliminar un reporte por su ID
    @DELETE("posts/{id}")
    Call<Void> deleteReport(@Path("id") int reportId);
}