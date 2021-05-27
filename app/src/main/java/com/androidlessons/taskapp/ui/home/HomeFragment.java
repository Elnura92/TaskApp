package com.androidlessons.taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlessons.taskapp.R;
import com.androidlessons.taskapp.models.Task;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    private AlertDialog.Builder builder;


    private HomeViewModel homeViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
        builder = new AlertDialog.Builder(requireContext());
        adapter.setClickListener(new TaskAdapter.MyClickListener() {

            @Override
            public void onItemClick(String item, int position) {
                Toast.makeText(requireActivity(), item + ":" + String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position) {

                builder.setMessage(R.string.alert_dialog_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.alert_dialog_yes_btn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.removeItem(position);

                            }
                        })

                        .setNegativeButton(R.string.alert_dialog_no_btn, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle(R.string.alert_dialog_title);
                alertDialog.show();


            }
        });


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskFragment();
            }
        });

        setResultListener();
        initList();

    }

    private void initList() {

        recyclerView.setAdapter(adapter);

    }

    private void setResultListener() {
        getParentFragmentManager().setFragmentResultListener("task", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String text = result.getString("text");
                Task task = new Task(text);
                adapter.addItem(task);
                Log.e("Home", "text:" + text);

            }


        });
    }

    private void openTaskFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.taskFragment);

    }


}