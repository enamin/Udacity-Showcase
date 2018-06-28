package ena.min.android.udacityshowcase.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.navigation.Navigation;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * BaseFragment class which supports: connecting to ViewModel, keeping and disposing disposables,
 * and navigation
 * @param <V>
 */
public abstract class AbsFragment<V extends ViewModel> extends Fragment {

    protected V viewModel;
    protected ArrayList<Disposable> disposables = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }

        viewModel = ViewModelProviders.of(activity).get(getViewModelClass());
        onViewModelSet(savedInstanceState);
    }

    protected void navigate(int actionResId) {
        View view = getView();
        if (view != null) {
            Navigation.findNavController(view).navigate(actionResId);
        }
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected void disposeAll() {
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        disposables.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposeAll();
    }

    /**
     * Start all your logic inside this method!
     * @param savedInstanceState
     */
    protected abstract void onViewModelSet(Bundle savedInstanceState);

    /**
     * Return the layout resource id of your view
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * Specify the class of your ViewModel
     * @return
     */
    protected abstract Class<V> getViewModelClass();
}
