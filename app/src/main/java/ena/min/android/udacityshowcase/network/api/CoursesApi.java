package ena.min.android.udacityshowcase.network.api;

import ena.min.android.udacityshowcase.network.models.CourseNetworkModel;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CoursesApi {
    @GET("public-api/v0/courses")
    Observable<CourseNetworkModel> call();
}
