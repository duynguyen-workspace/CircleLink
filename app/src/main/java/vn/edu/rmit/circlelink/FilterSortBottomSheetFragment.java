package vn.edu.rmit.circlelink;

import android.app.DatePickerDialog;
import android.app.sdksandbox.LoadSdkException;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.rmit.circlelink.model.Event;
import vn.edu.rmit.circlelink.model.Group;
import vn.edu.rmit.circlelink.model.User;

public class FilterSortBottomSheetFragment extends BottomSheetDialogFragment {

    private boolean isFilterView = true; // To decide if it's Filter or Sort
    private int currentTabIndex = 0;  // Default tab index

    private Button applyButton;

    private LocalDate groupStartDate;
    private LocalDate groupEndDate;
    private LocalDate userStartBirthdate;
    private LocalDate userEndBirthdate;

    private String userSelectedSortOrder;
    private String groupSelectedSortOrder;
    private String eventSelectedSortOrder;

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
        applyButton = view.findViewById(R.id.applyButton);

        setupBottomSheetContent(view); // Update content based on Filter/Sort and tab

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

        // Default filter setup
        if (currentTabIndex == 0) { // Event tab
            // Setup filter for the Events tab
        } else if (currentTabIndex == 1) { // Group tab
            groupFilterUI(view);
        } else if (currentTabIndex == 2) { // User tab
            userFilterUI(view);
        }
    }

    private void groupFilterUI(View view) {
        LinearLayout groupFilterLayout = view.findViewById(R.id.groupFilterLayout);
        groupFilterLayout.setVisibility(View.VISIBLE);

        Button startDateButton = view.findViewById(R.id.startDateButton);
        Button endDateButton = view.findViewById(R.id.endDateButton);

        startDateButton.setOnClickListener(v -> showDatePickerGroup(true));
        endDateButton.setOnClickListener(v -> showDatePickerGroup(false));

        applyButton.setOnClickListener(v -> {
            if (groupStartDate != null && groupEndDate != null) {
                ArrayList<Group> filteredGroups = SortUtils.filterGroupsByDateRange(SuperUserActivity.groupList, groupStartDate, groupEndDate);
                ((SuperUserActivity) getActivity()).applyFilterSortGroup(filteredGroups);
            }

            dismiss();
        });
    }

    private void userFilterUI(View view) {
        LinearLayout userFilterLayout = view.findViewById(R.id.userFilterLayout);
        userFilterLayout.setVisibility(View.VISIBLE);

        Button startDateButton = view.findViewById(R.id.startBirthdateButton);
        Button endDateButton = view.findViewById(R.id.endBirthdateButton);

        startDateButton.setOnClickListener(v -> showDatePickerUser(true));
        endDateButton.setOnClickListener(v -> showDatePickerUser(false));

        applyButton.setOnClickListener(v -> {
            if (userStartBirthdate != null && userEndBirthdate != null) {
                ArrayList<User> filteredUsers = SortUtils.filterUsersByDateRange(SuperUserActivity.userList, userStartBirthdate, userEndBirthdate);
                ((SuperUserActivity) getActivity()).applyFilterSortUser(filteredUsers);
            }

            dismiss();
        });
    }

    private void showDatePickerGroup(boolean isStartDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    // Convert selected date to LocalDate
                    LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);

                    // Update the appropriate button text
                    if (isStartDate) {
                        groupStartDate = selectedDate;
                        ((Button) getView().findViewById(R.id.startDateButton))
                                .setText(groupStartDate.toString());
                    } else {
                        groupEndDate = selectedDate;
                        ((Button) getView().findViewById(R.id.endDateButton))
                                .setText(groupEndDate.toString());
                    }
                },
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue() - 1,
                LocalDate.now().getDayOfMonth());

        datePickerDialog.show();
    }
    private void showDatePickerUser(boolean isStartDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    // Convert selected date to LocalDate
                    LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);

                    // Update the appropriate button text
                    if (isStartDate) {
                        userStartBirthdate = selectedDate;
                        ((Button) getView().findViewById(R.id.startBirthdateButton))
                                .setText(userStartBirthdate.toString());
                    } else {
                        userEndBirthdate = selectedDate;
                        ((Button) getView().findViewById(R.id.endBirthdateButton))
                                .setText(userEndBirthdate.toString());
                    }
                },
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue() - 1,
                LocalDate.now().getDayOfMonth());

        datePickerDialog.show();
    }


    private void setupSortUI(View view) {

        // Default sort setup
        if (currentTabIndex == 0) { // Event tab
            eventSortUI(view);
        } else if (currentTabIndex == 1) { // Group tab
            groupSortUI(view);
        } else if (currentTabIndex == 2) { // User tab
            userSortUI(view);
        }
    }


    private void userSortUI(View view) {
        LinearLayout userSortLayout = view.findViewById(R.id.userSortLayout);
        RadioGroup sortRadioGroup = view.findViewById(R.id.userSortRadioGroup);
        userSortLayout.setVisibility(View.VISIBLE);

        sortRadioGroup.setOnCheckedChangeListener((user, checkedId) -> {
            if (checkedId == R.id.userNameSortAsc) {
                userSelectedSortOrder = "ascName";
            } else if (checkedId == R.id.userNameSortDesc) {
                userSelectedSortOrder = "descName";
            } else if (checkedId == R.id.userBirthdateSortAsc) {
                userSelectedSortOrder = "ascDate";
            } else if (checkedId == R.id.userBirthdateSortDesc) {
                userSelectedSortOrder = "descDate";
            }
        });

        applyButton.setOnClickListener(v -> {
            if ("ascName".equals(userSelectedSortOrder)) {
                ArrayList<User> sortedUsers = SuperUserActivity.userList;
                SortUtils.sortUsersByName(sortedUsers, true);
                ((SuperUserActivity) getActivity()).applyFilterSortUser(sortedUsers);
            } else if ("descName".equals(userSelectedSortOrder)) {
                ArrayList<User> sortedUsers = SuperUserActivity.userList;
                SortUtils.sortUsersByName(sortedUsers, false);
                ((SuperUserActivity) getActivity()).applyFilterSortUser(sortedUsers);
            } else if ("ascDate".equals(userSelectedSortOrder)) {
                ArrayList<User> sortedUsers = SuperUserActivity.userList;
                SortUtils.sortUsersByDate(sortedUsers, true);
                ((SuperUserActivity) getActivity()).applyFilterSortUser(sortedUsers);
            } else if ("descDate".equals(userSelectedSortOrder)) {
                ArrayList<User> sortedUsers = SuperUserActivity.userList;
                SortUtils.sortUsersByDate(sortedUsers, false);
                ((SuperUserActivity) getActivity()).applyFilterSortUser(sortedUsers);
            }

            dismiss();
        });
    }


    private void eventSortUI(View view) {
        LinearLayout eventSortLayout = view.findViewById(R.id.eventSortLayout);
        RadioGroup sortRadioGroup = view.findViewById(R.id.eventSortRadioGroup);
        eventSortLayout.setVisibility(View.VISIBLE);

        sortRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.eventSortAsc) {
                eventSelectedSortOrder = "asc";
            } else if (checkedId == R.id.eventSortDesc) {
                eventSelectedSortOrder = "desc";
            }
        });

        applyButton.setOnClickListener(v -> {
            if ("asc".equals(eventSelectedSortOrder)) {
                ArrayList<Event> sortedEvents = SuperUserActivity.eventList;
                SortUtils.sortEventsByTitle(sortedEvents, true);
                ((SuperUserActivity) getActivity()).applyFilterSortEvent(sortedEvents);
            } else if ("desc".equals(eventSelectedSortOrder)) {
                ArrayList<Event> sortedEvents = SuperUserActivity.eventList;
                SortUtils.sortEventsByTitle(sortedEvents, false);
                ((SuperUserActivity) getActivity()).applyFilterSortEvent(sortedEvents);
            }

            dismiss();
        });
    }


    private void groupSortUI(View view) {
        LinearLayout groupSortLayout = view.findViewById(R.id.groupSortLayout);
        RadioGroup sortRadioGroup = view.findViewById(R.id.groupSortRadioGroup);
        groupSortLayout.setVisibility(View.VISIBLE);

        sortRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.groupNameSortAsc) {
                groupSelectedSortOrder = "ascName";
            } else if (checkedId == R.id.groupNameSortDesc) {
                groupSelectedSortOrder = "descName";
            } else if (checkedId == R.id.groupDateSortAsc) {
                groupSelectedSortOrder = "ascDate";
            } else if (checkedId == R.id.groupDateSortDesc) {
                groupSelectedSortOrder = "descDate";
            }
        });

        applyButton.setOnClickListener(v -> {
            if ("ascName".equals(groupSelectedSortOrder)) {
                ArrayList<Group> sortedGroups = SuperUserActivity.groupList;
                SortUtils.sortGroupsByName(sortedGroups, true);
                ((SuperUserActivity) getActivity()).applyFilterSortGroup(sortedGroups);
            } else if ("descName".equals(groupSelectedSortOrder)) {
                ArrayList<Group> sortedGroups = SuperUserActivity.groupList;
                SortUtils.sortGroupsByName(sortedGroups, false);
                ((SuperUserActivity) getActivity()).applyFilterSortGroup(sortedGroups);
            } else if ("ascDate".equals(groupSelectedSortOrder)) {
                ArrayList<Group> sortedGroups = SuperUserActivity.groupList;
                SortUtils.sortGroupsByDate(sortedGroups, true);
                ((SuperUserActivity) getActivity()).applyFilterSortGroup(sortedGroups);
            } else if ("descDate".equals(groupSelectedSortOrder)) {
                ArrayList<Group> sortedGroups = SuperUserActivity.groupList;
                SortUtils.sortGroupsByDate(sortedGroups, false);
                ((SuperUserActivity) getActivity()).applyFilterSortGroup(sortedGroups);
            }

            dismiss();
        });
    }
}
