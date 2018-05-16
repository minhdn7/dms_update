package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import vn.com.vsc.ptpm.VNPT_DMS.R;


@SuppressLint("ValidFragment")
public class DateTimePickerFragment extends DialogFragment {
    private Date mDate;
    private int year, month, day, hour, min;
    private TextView txtTimer;

    @SuppressLint("ValidFragment")
    public DateTimePickerFragment(TextView txtTimer) {
        this.txtTimer = txtTimer;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(mDate);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_date_time_picker, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker)v.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                DateTimePickerFragment.this.year = year;
                DateTimePickerFragment.this.month = month;
                DateTimePickerFragment.this.day = day;
                updateDateTime();
            }
        });

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hour, int min) {
                DateTimePickerFragment.this.hour = hour;
                DateTimePickerFragment.this.min = min;
                updateDateTime();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Chọn ngày giờ thực hiện".toUpperCase())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        sendResult(getActivity().RESULT_OK);
                        String time = day + "/" + (month + 1) + "/" + year + " " + hour + ":" + min;
                        txtTimer.setText(time);
                    }
                })
                .create();
    }

    public void updateDateTime() {
        mDate = new GregorianCalendar(year, month, day, hour, min).getTime();

//        getArguments().putSerializable(EXTRA_DATE, mDate);
    }


}
