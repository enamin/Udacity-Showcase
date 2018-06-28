package ena.min.android.udacityshowcase.view_models;

import org.junit.Before;
import org.junit.Test;

import ena.min.android.udacityshowcase.business_logic_models.Course;
import ena.min.android.udacityshowcase.data.SelectedCourse;
import ena.min.android.udacityshowcase.utils.NavigationHelper;
import ena.min.android.udacityshowcase.utils.StreamHelper;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MainViewModelTest {

    private Course testCourse;
    private SelectedCourse testSelectedCourse;

    @Before
    public void setup() {
        testCourse = new Course();
        testSelectedCourse = new SelectedCourse(testCourse, null, null);
    }

    @Test
    public void setCourseSelectStreamTest() {
        MainViewModel viewModel = new MainViewModel();
        MainViewModel spy = spy(viewModel);
        Observable<SelectedCourse> testObservable = Observable.empty();
        spy.setCourseSelectStream(testObservable);
        verify(spy).consumeCourseSelectEvents(testObservable);
    }

    @Test
    public void consumeCourseSelectEvents() {
        MainViewModel viewModel = new MainViewModel();
        TestObserver<SelectedCourse> testObserver1 = StreamHelper.getInstance().getSelectedCourseStream().test();
        TestObserver<String> testObserver2 = viewModel.getNavigationCommand().test();
        Observable<SelectedCourse> testObservable = Observable.just(testSelectedCourse);
        viewModel.consumeCourseSelectEvents(testObservable);

        testObserver1.assertValue(testSelectedCourse);
        testObserver1.assertValueCount(1);

        testObserver2.assertValue(NavigationHelper.NAVIGATE_TO_COURSE_DETAIL_VIEW);
        testObserver2.assertValueCount(1);
    }

    @Test
    public void releaseTest() {
        MainViewModel viewModel = new MainViewModel();
        Observable<SelectedCourse> testObservable = Observable.just(testSelectedCourse);
        viewModel.setCourseSelectStream(testObservable);

        assertNotNull(viewModel.courseSelectDisposable);

        viewModel.release();

        assertNull(viewModel.courseSelectDisposable);
    }
}
