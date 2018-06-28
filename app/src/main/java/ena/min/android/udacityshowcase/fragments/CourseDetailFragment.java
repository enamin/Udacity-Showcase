package ena.min.android.udacityshowcase.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import butterknife.BindView;
import ena.min.android.udacityshowcase.R;
import ena.min.android.udacityshowcase.business_logic_models.Course;
import ena.min.android.udacityshowcase.business_logic_models.Instructor;
import ena.min.android.udacityshowcase.utils.MathHelper;
import ena.min.android.udacityshowcase.view_models.CourseDetailViewModel;

public class CourseDetailFragment extends AbsFragment<CourseDetailViewModel> {

    @BindView(R.id.ivCourse)
    ImageView ivCourse;

    @BindView(R.id.ctlCourseDetail)
    CollapsingToolbarLayout ctlCourseDetail;

    @BindView(R.id.toolbarCourseDetail)
    Toolbar toolbarCourseDetail;

    @BindView(R.id.tvCourseTitle)
    TextView tvCourseTitle;

    @BindView(R.id.tvCourseSummary)
    TextView tvCourseSummary;

    @BindView(R.id.tvInstructorName)
    TextView tvInstructorName;

    @BindView(R.id.tvInstructorBio)
    TextView tvInstructorBio;

    @BindView(R.id.ivInstructor)
    ImageView ivInstructor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarCourseDetail.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbarCourseDetail.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    protected void onViewModelSet(Bundle savedInstanceState) {
        Course course = viewModel.getCourse();
        updateCourseView(course);

        if (course.instructors.size() > 0) {
            updateInstructorView(course.instructors.get(0));
        }
    }

    private void updateInstructorView(Instructor instructor) {
        if (instructor.name.length() > 0) {
            tvInstructorName.setText(instructor.name);
        }

        if (instructor.bio.length() > 0) {
            tvInstructorBio.setText(instructor.bio);
        }

        if (instructor.image.length() > 0) {
            Glide.with(getActivity())
                    .setDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.shape_place_holder_gray_rect)
                            .transform(new RoundedCorners(MathHelper.convertDpToPx(getActivity(), 5))))
                    .load(instructor.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivInstructor);
        }
    }

    private void updateCourseView(Course course) {
        ctlCourseDetail.setTitle(course.title);

        if (course.title.length() > 0) {
            tvCourseTitle.setText(course.title);
        }

        if (course.summary.length() > 0) {
            tvCourseSummary.setText(course.summary);
        }

        Glide.with(getActivity())
                .setDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.shape_place_holder_gray_rect)
                        .signature(new ObjectKey(System.currentTimeMillis())))
                .load(course.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivCourse);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.view_course_detail;
    }

    @Override
    protected Class<CourseDetailViewModel> getViewModelClass() {
        return CourseDetailViewModel.class;
    }
}
