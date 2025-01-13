package vn.edu.rmit.circlelink;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilterSortBottomSheetFragment extends BottomSheetDialogFragment {

    private boolean isFilterView = true; // To decide if it's Filter or Sort
    private int currentTabIndex = 0;  // Default tab index

    public static FilterSortBottomSheetFragment newInstance(boolean isFilterView, int currentTabIndex) {
        FilterSortBottomSheetFragment fragment = new FilterSortBottomSheetFragment();
        Bundle args = new Bundle();
        args.putBoolean("isFilterView", isFilterView); // Pass the type of view to show
        args.putInt("currentTabIndex", currentTabIndex); // Pass the current tab index
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_sort_bottom_sheet, container, false);

        // Get the passed arguments (Filter/Sort type and current tab index)
        if (getArguments() != null) {
            isFilterView = getArguments().getBoolean("isFilterView");
            currentTabIndex = getArguments().getInt("currentTabIndex");
        }

        setupBottomSheetContent(view); // Update content based on Filter/Sort and tab

        // Set up Apply button listener
        Button applyButton = view.findViewById(R.id.applyButton);
        applyButton.setOnClickListener(v -> applyFilterSort());

        return view;
    }

    private void setupBottomSheetContent(View view) {
        TextView filterLabelBS = view.findViewById(R.id.filterLabelBS);
        TextView sortLabelBS = view.findViewById(R.id.sortLabelBS);

        if (isFilterView) {
            filterLabelBS.setVisibility(View.VISIBLE);
            setupFilterUI(view);
        } else {
            sortLabelBS.setVisibility(View.VISIBLE);
            setupSortUI(view);
        }
    }

    private void setupFilterUI(View view) {
        Spinner filterSpinner = view.findViewById(R.id.filterSpinner);

        switch (currentTabIndex) {
            case 0: // Event tab
                // Setup filter for the Events tab
                break;
            case 1: // Group tab
                // Setup filter for the Groups tab
                break;
            case 2: // User tab
                // Setup filter for the Users tab
                break;
            default:
                // Default filter setup
                break;
        }
    }

    private void setupSortUI(View view) {

        switch (currentTabIndex) {
            case 0: // Event tab
                eventSortUI(view);
                break;
            case 1: // Group tab
                groupSortUI(view);
                break;
            case 2: // User tab
                userSortUI(view);
                break;
            default:
                // Default sort setup
                break;
        }
    }

    private void userSortUI(View view) {
        LinearLayout userSortLayout = view.findViewById(R.id.userSortLayout);
        RadioGroup sortRadioGroup = view.findViewById(R.id.userSortRadioGroup);
        RadioButton userNameSortAsc = view.findViewById(R.id.userNameSortAsc);
        RadioButton userNameSortDesc = view.findViewById(R.id.userNameSortDesc);
        RadioButton userBirthdateSortAsc = view.findViewById(R.id.userBirthdateSortAsc);
        RadioButton userBirthdateSortDesc = view.findViewById(R.id.userBirthdateSortDesc);

        userSortLayout.setVisibility(View.VISIBLE);

        sortRadioGroup.setOnCheckedChangeListener((user, checkedId) -> {
            if (checkedId == R.id.userNameSortAsc) {
                // Sort in ascending order by event title
//                eventSortEvents(true);
                ((SuperUserActivity) getActivity()).applyFilterSortEvent(true);
            } else if (checkedId == R.id.userNameSortDesc) {
                // Sort in descending order by event title
//                eventSortEvents(false);
            }
        });
    }

    private void eventSortUI(View view) {
        LinearLayout eventSortLayout = view.findViewById(R.id.eventSortLayout);
        RadioGroup sortRadioGroup = view.findViewById(R.id.eventSortRadioGroup);
        RadioButton eventSortAsc = view.findViewById(R.id.eventSortAsc);
        RadioButton eventSortDesc = view.findViewById(R.id.eventSortDesc);

        eventSortLayout.setVisibility(View.VISIBLE);

        sortRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.eventSortAsc) {
                // Sort in ascending order by event title
//                eventSortEvents(true);
                ((SuperUserActivity) getActivity()).applyFilterSortEvent(true);
            } else if (checkedId == R.id.eventSortDesc) {
                // Sort in descending order by event title
//                eventSortEvents(false);
            }
        });
    }

    private void groupSortUI(View view) {
        LinearLayout groupSortLayout = view.findViewById(R.id.groupSortLayout);
        RadioGroup sortRadioGroup = view.findViewById(R.id.groupSortRadioGroup);
        RadioButton groupNameSortAsc = view.findViewById(R.id.groupNameSortAsc);
        RadioButton groupNameSortDesc = view.findViewById(R.id.groupNameSortDesc);
        RadioButton groupDateSortAsc = view.findViewById(R.id.groupDateSortAsc);
        RadioButton groupDateSortDesc = view.findViewById(R.id.groupDateSortDesc);

        groupSortLayout.setVisibility(View.VISIBLE);

        sortRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.groupNameSortAsc) {
                // Sort in ascending order by event title
//                eventSortEvents(true);
                ((SuperUserActivity) getActivity()).applyFilterSortEvent(true);
            } else if (checkedId == R.id.groupNameSortDesc) {
                // Sort in descending order by event title
//                eventSortEvents(false);
            }
        });
    }

    private void applyFilterSort() {
        // Get the selected filter and sort options
        Spinner filterSpinner = getView().findViewById(R.id.filterSpinner);
        Spinner sortSpinner = getView().findViewById(R.id.sortSpinner);

        String selectedFilter = filterSpinner.getSelectedItem().toString();
        String selectedSort = sortSpinner.getSelectedItem().toString();

        // Send the filter and sort values back to the Activity or Fragment
        if (getActivity() instanceof SuperUserActivity) {
            ((SuperUserActivity) getActivity()).applyFilterSort(selectedFilter, selectedSort);
        }

        dismiss(); // Close the BottomSheet
    }
}
