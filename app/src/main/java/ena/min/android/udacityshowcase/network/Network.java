package ena.min.android.udacityshowcase.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import ena.min.android.udacityshowcase.network.api.CoursesApi;
import ena.min.android.udacityshowcase.network.models.CourseNetworkModel;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides Network facilities via Retrofit
 */
public class Network {

    private static Network instance;
    private Retrofit retrofit;

    private Network() {
    }

    public static Network getInstance() {
        if (instance == null) {
            instance = new Network();

            instance.retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.udacity.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return instance;
    }

    private <T> Observable<T> schedule(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.newThread());
    }

    public Observable<CourseNetworkModel> requestCourses() {
        CoursesApi api = retrofit.create(CoursesApi.class);
        return schedule(api.call());
    }

}
