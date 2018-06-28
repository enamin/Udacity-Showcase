package ena.min.android.udacityshowcase.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ena.min.android.udacityshowcase.R;
import ena.min.android.udacityshowcase.business_logic_models.Course;
import ena.min.android.udacityshowcase.data.SelectedCourse;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.VH> {

    private List<Course> items;
    private PublishSubject<SelectedCourse> itemClicksStream = PublishSubject.create();

    public CourseAdapter(List<Course> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        Context context = holder.itemView.getContext();
        final Course item = items.get(position);

        holder.tvCourseTitle.setText(item.title);
        holder.tvCourseSubtitle.setText(item.summary);

        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .centerInside()
                        .circleCrop()
                )
                .load(item.image)
                .into(holder.ivCourse);

        holder.itemView.setOnClickListener(v -> itemClicksStream.onNext(new SelectedCourse(item, position, holder.itemView)));
    }

    public Observable<SelectedCourse> getItemClicksStream() {
        return itemClicksStream;
    }

    public void reset(List<Course> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCourse)
        ImageView ivCourse;

        @BindView(R.id.tvCourseTitle)
        TextView tvCourseTitle;

        @BindView(R.id.tvCourseSubtitle)
        TextView tvCourseSubtitle;

        VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
