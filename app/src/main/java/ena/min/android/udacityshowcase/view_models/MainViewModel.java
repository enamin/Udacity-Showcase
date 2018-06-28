package ena.min.android.udacityshowcase.view_models;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import ena.min.android.udacityshowcase.business_logic_models.Course;
import ena.min.android.udacityshowcase.data.SelectedCourse;
import ena.min.android.udacityshowcase.data.live.CoursesLiveData;
import ena.min.android.udacityshowcase.utils.NavigationHelper;
import ena.min.android.udacityshowcase.utils.StreamHelper;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class MainViewModel extends ViewModel {

    Disposable courseSelectDisposable;
    private PublishSubject<String> navigationCommand = PublishSubject.create();

    /**
     * View should call this method to get the courses. This ViewModel provide the courses by
     * means of {@link android.arch.lifecycle.LiveData}
     *
     * @param owner    {@link LifecycleOwner} of the LiveData
     * @param observer Observer which consumes the LiveData
     */
    public void observeCourses(LifecycleOwner owner, Observer<ArrayList<Course>> observer) {
        CoursesLiveData.getInstance().observe(owner, observer);
    }

    /**
     * This ViewModel emit its navigation commands through this method. View should call and
     * subscribe to it and consume the navigation commands.
     *
     * @return Observable<String> which emits commands defined in {@link NavigationHelper}
     */
    public Observable<String> getNavigationCommand() {
        return navigationCommand;
    }

    /**
     * The View should expose a stream to this ViewModel and send SelectedCourse events when user
     * interacts with UI
     *
     * @param selectedCourseStream Observable<SelectedCourse>
     */
    public void setCourseSelectStream(Observable<SelectedCourse> selectedCourseStream) {
        if (courseSelectDisposable != null) {
            courseSelectDisposable.dispose();
        }

        courseSelectDisposable = consumeCourseSelectEvents(selectedCourseStream);
    }

    /**
     * This method handles the business logic of MainView (MainFragment)
     * It receives a stream of SelectedCourse, dispatch it to StreamHelper,
     * and then navigates to the CourseDetailFragment
     *
     * @param selectedCourseStream
     */
    @NonNull
    Disposable consumeCourseSelectEvents(Observable<SelectedCourse> selectedCourseStream) {
        return selectedCourseStream.subscribe(selectedCourse -> {
            StreamHelper.getInstance().getSelectedCourseStream().onNext(selectedCourse);
            navigationCommand.onNext(NavigationHelper.NAVIGATE_TO_COURSE_DETAIL_VIEW);
        }, throwable -> {
        });
    }

    public void release() {
        if (courseSelectDisposable != null) {
            courseSelectDisposable.dispose();
            courseSelectDisposable = null;
        }
    }
}
