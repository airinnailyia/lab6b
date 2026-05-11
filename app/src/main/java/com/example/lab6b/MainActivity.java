package com.example.lab6b;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    Button startSetDialog;

    TextView alarmprompt;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return insets;
        });

        editText =
                findViewById(R.id.editText);

        startSetDialog =
                findViewById(R.id.startSetDialog);

        alarmprompt =
                findViewById(R.id.alarmprompt);

        button =
                findViewById(R.id.button);

        startSetDialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openTimePicker();
            }
        });
    }

    private void openTimePicker() {

        Calendar calendar =
                Calendar.getInstance();

        int hour =
                calendar.get(Calendar.HOUR_OF_DAY);

        int minute =
                calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog =
                new TimePickerDialog(

                        MainActivity.this,

                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(
                                    TimePicker view,
                                    int hourOfDay,
                                    int minute
                            ) {

                                Calendar selectedTime =
                                        Calendar.getInstance();

                                selectedTime.set(
                                        Calendar.HOUR_OF_DAY,
                                        hourOfDay
                                );

                                selectedTime.set(
                                        Calendar.MINUTE,
                                        minute
                                );

                                selectedTime.set(
                                        Calendar.SECOND,
                                        0
                                );

                                String msg =
                                        editText.getText().toString();

                                alarmprompt.setText(
                                        "Alarm is set at "
                                                + hourOfDay
                                                + ":"
                                                + minute
                                                + "\nMessage : "
                                                + msg
                                );

                                Intent intent =
                                        new Intent(
                                                MainActivity.this,
                                                AlarmReceiver.class
                                        );

                                intent.putExtra(
                                        "message",
                                        msg
                                );

                                PendingIntent pendingIntent =
                                        PendingIntent.getBroadcast(

                                                MainActivity.this,
                                                0,
                                                intent,
                                                PendingIntent.FLAG_IMMUTABLE
                                        );

                                AlarmManager alarmManager =
                                        (AlarmManager)
                                                getSystemService(
                                                        Context.ALARM_SERVICE
                                                );

                                alarmManager.setExact(

                                        AlarmManager.RTC_WAKEUP,

                                        selectedTime.getTimeInMillis(),

                                        pendingIntent
                                );
                            }
                        },

                        hour,
                        minute,
                        false
                );

        timePickerDialog.show();
    }

    public void closeApp(View view) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Exit Application?");

        builder.setMessage("Click yes to exit");

        builder.setPositiveButton(

                "Yes",

                (dialog, which) -> finish()
        );

        builder.setNegativeButton(

                "No",

                (dialog, which) -> dialog.dismiss()
        );

        builder.show();
    }
}