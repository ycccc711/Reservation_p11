package sg.edu.rp.c346.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName, etTelephone, etSize,etDay,etTime;
    TextView tvDay,tvTime;
    CheckBox checkBox;
//    DatePicker datePicker;
//    TimePicker timePicker;
    Button btReserve;
    Button btReset;

    Calendar date = Calendar.getInstance();
    Calendar time = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Reservation");

        tvDay = findViewById(R.id.textViewDay);
        tvTime = findViewById(R.id.textViewTime);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        etDay = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);

        checkBox = findViewById(R.id.checkBox);
//        datePicker = findViewById(R.id.datePicker);
//        timePicker = findViewById(R.id.timePicker);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);

        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the listener to set the date
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDay.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                };

                //Calendar date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH);
                int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);

                //create the date picker dialog
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, year, month, dayOfMonth);
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the listener to set the time
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };

                //Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR);
                int mins = time.get(Calendar.MINUTE);

                //create the time picker dialog
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, hour, mins, true);
                myTimeDialog.show();
            }
        });

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                } else {
                    isSmoke = "non-smoking";
                }

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage("New Reservation \n" + "Name: " + etName.getText().toString() + "\nSmoking: " + isSmoke + "\nSize: " + etSize.getText().toString()
                        + "\nDate: " + etDay.getText().toString() + "\nTime: " + etTime.getText().toString());
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveData();
                        Toast.makeText(MainActivity.this, "Reservation saved.", Toast.LENGTH_SHORT).show();
                    }
                });

                //configure the 'neutral' button
                myBuilder.setNeutralButton("Cancel", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

//                Toast.makeText(MainActivity.this,
//                        "Hi, " + etName.getText().toString() + ", you have booked a "
//                                + etSize.getText().toString() + " person(s) "
//                                + isSmoke + " table on "
////                                + datePicker.getYear() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth() + " at "
////                                + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ". Your phone number is "
//                                + etTelephone.getText().toString() + ".",
//                        Toast.LENGTH_LONG).show();
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText(null);
                etTelephone.setText(null);
                etSize.setText(null);
                checkBox.setChecked(false);
                etTime.setText("");
                etDay.setText("");
//                datePicker.updateDate(2019, 5, 1);
//                timePicker.setCurrentHour(19);
//                timePicker.setCurrentMinute(30);
            }
        });


    }

    private void saveData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = preferences.edit();

        String isSmoke = "";
        if (checkBox.isChecked()) {
            isSmoke = "smoking";
        } else {
            isSmoke = "non-smoking";
        }

        editor.putString("name",etName.getText().toString());
        editor.putInt("size",Integer.parseInt(etSize.getText().toString()));
        editor.putString("Smoke", isSmoke);
        editor.putString("date", String.valueOf(date));
        editor.putString("time", String.valueOf(time));
        editor.commit();

    }
}