package com.weeravit.datetimedialog;


import android.app.Dialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

public class DatetimeDialogFragment extends DialogFragment {

    TimePicker timePicker;
    DatePicker datePicker;
    LilunaButton btnConfirm;

    private OnDateTimeListerner listerner;

    public static DatetimeDialogFragment newInstance(OnDateTimeListerner listerner) {
        DatetimeDialogFragment fragment = new DatetimeDialogFragment();
        fragment.setListerner(listerner);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datetime_dialog, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        btnConfirm = (LilunaButton) view.findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(onConfirmClicked);

        removeYearFromDatePicker();
        timePicker.setIs24HourView(true);
    }

    public void removeYearFromDatePicker() {
        DatePicker dp_mes = datePicker;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android");
            if (yearSpinnerId != 0) {
                View yearSpinner = dp_mes.findViewById(yearSpinnerId);
                if (yearSpinner != null) {
                    yearSpinner.setVisibility(View.GONE);
                }
            }
        } else { //Older SDK versions
            Field f[] = dp_mes.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner")) {
                    field.setAccessible(true);
                    Object yearPicker = null;
                    try {
                        yearPicker = field.get(dp_mes);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ((View) yearPicker).setVisibility(View.GONE);
                }
            }
        }
    }

    View.OnClickListener onConfirmClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String datetime = String.format("%s/%s/%s %s.%s",
                    datePicker.getDayOfMonth(),
                    datePicker.getMonth() + 1,
                    datePicker.getYear(),
                    timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute());

            listerner.onDateTimeSet(datetime);
            dismiss();
        }
    };

    public void setListerner(OnDateTimeListerner listerner) {
        this.listerner = listerner;
    }

    public interface OnDateTimeListerner {
        void onDateTimeSet(String datetime);
    }

}
