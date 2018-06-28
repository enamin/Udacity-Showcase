package ena.min.android.udacityshowcase;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ena.min.android.udacityshowcase.utils.StreamHelper;
import ena.min.android.udacityshowcase.view_models.CourseDetailViewModel;
import ena.min.android.udacityshowcase.view_models.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectViewModels();
    }

    private void connectViewModels() {
        ViewModelProviders.of(this)
                .get(CourseDetailViewModel.class)
                .connectToSelectedCourseStream(StreamHelper.getInstance().getSelectedCourseStream());
    }

    private void disconnectViewModels() {
        ViewModelProviders.of(this)
                .get(MainViewModel.class)
                .release();

        ViewModelProviders.of(this)
                .get(CourseDetailViewModel.class)
                .release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectViewModels();
    }
}
