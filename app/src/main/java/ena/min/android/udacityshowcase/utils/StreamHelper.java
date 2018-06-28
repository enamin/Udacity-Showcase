package ena.min.android.udacityshowcase.utils;

import ena.min.android.udacityshowcase.data.SelectedCourse;
import io.reactivex.subjects.PublishSubject;

/**
 * A singleton class to provide streams of data to all components.
 */
public class StreamHelper {
    private static StreamHelper instance;

    private PublishSubject<SelectedCourse> selectedCourseStream = PublishSubject.create();

    private StreamHelper() {
    }

    public static synchronized StreamHelper getInstance() {
        if (instance == null) {
            instance = new StreamHelper();
        }

        return instance;
    }

    public PublishSubject<SelectedCourse> getSelectedCourseStream() {
        return selectedCourseStream;
    }
}
