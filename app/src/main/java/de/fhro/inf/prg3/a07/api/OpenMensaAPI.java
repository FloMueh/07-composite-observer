package de.fhro.inf.prg3.a07.api;

import java.util.List;

import de.fhro.inf.prg3.a07.model.Meal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Peter Kurfer on 11/19/17.
 */

public interface OpenMensaAPI {
    // TODO add method to get meals of a day
    @GET("api/v2/canteens/229/days/{date}/meals")
    Call<List<Meal>> GetMeals(@Path("date") String date);
}
