package ena.min.android.udacityshowcase.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import ena.min.android.udacityshowcase.R;
import ena.min.android.udacityshowcase.adapters.CourseAdapter;
import ena.min.android.udacityshowcase.utils.NavigationHelper;
import ena.min.android.udacityshowcase.view_models.MainViewModel;

public class MainFragment extends AbsFragment<MainViewModel> {

    @BindView(R.id.rvMain)
    RecyclerView rvMain;

    @BindView(R.id.fabMain)
    FloatingActionButton fabMain;

    @BindView(R.id.srlMain)
    SwipeRefreshLayout srlMain;

    private CourseAdapter adapter;
    private AlertDialog infoDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.view_main;
    }

    @Override
    protected Class getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void onViewModelSet(Bundle savedInstanceState) {
        prepareView();
        loadList();

        addDisposable(viewModel.getNavigationCommand().subscribe(
                command -> {
                    if (command.equals(NavigationHelper.NAVIGATE_TO_COURSE_DETAIL_VIEW)) {
                        navigate(R.id.action_mainFragment_to_courseDetailFragment);
                    }
                },
                throwable -> {
                    showError();
                }
        ));
    }

    private void loadList() {
        disposables.clear();

        srlMain.setRefreshing(true);

        viewModel.observeCourses(this, courses -> {
            adapter.reset(courses);
            srlMain.setRefreshing(false);
        });

    }

    private void prepareView() {
        //Swipe Refresh Layout:
        srlMain.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        srlMain.setOnRefreshListener(() -> loadList());

        //RecyclerView:
        rvMain.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new CourseAdapter(new ArrayList<>());
        rvMain.setAdapter(adapter);
        rvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabMain.getVisibility() == View.VISIBLE) {
                    fabMain.hide();
                } else if (dy < 0 && fabMain.getVisibility() != View.VISIBLE) {
                    fabMain.show();
                }
            }
        });

        //Floating Action Button:
        fabMain.setOnClickListener(v -> showInfoDialog());

        //Send Events to ViewModel
        viewModel.setCourseSelectStream(adapter.getItemClicksStream());
    }

    private void showInfoDialog() {
        if (infoDialog != null && infoDialog.isShowing()) {
            infoDialog.dismiss();
        }

        infoDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Info")
                .setMessage(R.string.info_dialog_contents)
                .setPositiveButton("LinkedIn", (dialog, which) -> {
                    openWebPage("https://www.linkedin.com/in/amin-enami-b239b589/");
                })
                .setNegativeButton("Github", (dialog, which) -> {
                    openWebPage("https://github.com/enamin");
                })
                .create();

        infoDialog.show();
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        getActivity().startActivity(intent);
    }

    private void showError() {
        final Snackbar snackbar = Snackbar.make(rvMain, getContext().getString(R.string.somethingHappaned), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getContext().getString(R.string.tryAgain), v -> {
            snackbar.dismiss();
            loadList();
        });

        snackbar.show();
    }

}
