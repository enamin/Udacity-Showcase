package ena.min.android.udacityshowcase.data.live;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;

import ena.min.android.udacityshowcase.business_logic_models.Course;
import ena.min.android.udacityshowcase.network.Network;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * This LiveData provide the most recent list of all Udacity courses.
 */
public class CoursesLiveData extends LiveData<ArrayList<Course>> {
    private static CoursesLiveData instance;
    private Disposable requestDisposable;

    public static CoursesLiveData getInstance() {
        if (instance == null) {
            instance = new CoursesLiveData();
        }

        return instance;
    }

    private void updateData() {
        requestDisposable = Network.getInstance()
                .requestCourses()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        courseNetworkModel -> setValue(courseNetworkModel.courses),
                        throwable -> {
                            requestDisposable.dispose();
                            updateData();
                        }
                );
    }

    @Override
    protected void onActive() {
        super.onActive();
        updateData();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        requestDisposable.dispose();
    }


}
