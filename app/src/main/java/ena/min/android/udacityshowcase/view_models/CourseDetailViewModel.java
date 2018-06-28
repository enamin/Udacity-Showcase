package ena.min.android.udacityshowcase.view_models;

import android.arch.lifecycle.ViewModel;

import ena.min.android.udacityshowcase.business_logic_models.Course;
import ena.min.android.udacityshowcase.data.SelectedCourse;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class CourseDetailViewModel extends ViewModel {
    private Course course;
    Disposable streamDisposable;

    /**
     * @return Last selected course which has been consumed by {@link CourseDetailViewModel#connectToSelectedCourseStream}
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Call this method when the application starts to connect this ViewModel to the stream of
     * SelectedCourse; So whenever the view connects to it, it has the fresh data to provide.
     *
     * @param stream
     */
    public void connectToSelectedCourseStream(Observable<SelectedCourse> stream) {
        streamDisposable = stream.subscribe(selectedCourse -> course = selectedCourse.getCourse());
    }

    public void release() {
        if (streamDisposable != null) {
            streamDisposable.dispose();
            streamDisposable = null;
        }
    }
}
