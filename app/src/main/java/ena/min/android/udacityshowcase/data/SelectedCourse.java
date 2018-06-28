package ena.min.android.udacityshowcase.data;

import android.view.View;

import ena.min.android.udacityshowcase.business_logic_models.Course;

public class SelectedCourse {
    Course course;

    /**
     * NOTE: We keep the position and view of the selected course for future utilities. When Navigation
     * Architecture component supports Fragment transitions, we need these data.
     * @param course
     * @param position
     * @param itemView
     */
    public SelectedCourse(Course course, Integer position, View itemView) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
