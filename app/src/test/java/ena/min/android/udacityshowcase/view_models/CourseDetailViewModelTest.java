package ena.min.android.udacityshowcase.view_models;

import org.junit.Before;
import org.junit.Test;

import ena.min.android.udacityshowcase.business_logic_models.Course;
import ena.min.android.udacityshowcase.data.SelectedCourse;
import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CourseDetailViewModelTest {

    private Course testCourse;
    private SelectedCourse testSelectedCourse;

    @Before
    public void setup() {
        testCourse = new Course();
        testSelectedCourse = new SelectedCourse(testCourse, null, null);
    }

    @Test
    public void connectToSelectedCourseStreamTest() {
        CourseDetailViewModel viewModel = new CourseDetailViewModel();

        viewModel.connectToSelectedCourseStream(Observable.just(testSelectedCourse));
        assertEquals(testCourse, viewModel.getCourse());
    }

    @Test
    public void releaseTest()
    {
        CourseDetailViewModel viewModel = new CourseDetailViewModel();
        viewModel.connectToSelectedCourseStream(Observable.just(testSelectedCourse));

        assertNotNull(viewModel.streamDisposable);

        viewModel.release();

        assertNull(viewModel.streamDisposable);
    }
}
