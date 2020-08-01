package com.darji.darjifamilyapp.Adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.darji.darjifamilyapp.Model.MatrimonialData;
import com.darji.darjifamilyapp.R;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MatrimonialView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrimonial_view);

        TextView canName = findViewById(R.id.candidatefull_name),
                canEmail = findViewById(R.id.candidate_email),
                canDob = findViewById(R.id.candidate_dob),
                canCity = findViewById(R.id.candidate_city);

        TextView fathername = findViewById(R.id.middlename),
                nativeplace = findViewById(R.id.nativeplace),
                mothername = findViewById(R.id.mothername),
                mothernative = findViewById(R.id.mothernative),
                address = findViewById(R.id.address),
                brother = findViewById(R.id.brothercount),
                sister = findViewById(R.id.sistercount),
                aboutme = findViewById(R.id.aboutme),
                higerstedu = findViewById(R.id.highesteducation),
                specialization = findViewById(R.id.specialization),
                occupationa = findViewById(R.id.occupation),
                annualincome = findViewById(R.id.annualincome),
                height = findViewById(R.id.height),
                weight = findViewById(R.id.weight),
                age = findViewById(R.id.age),
                expectation = findViewById(R.id.expectation);

        Gson gson = new Gson();
        MatrimonialData data = gson.fromJson(getIntent().getStringExtra("data"),MatrimonialData.class);

        canName.setText(data.getFirstName()+" "+data.getMiddleName()+" "+data.getLastName());
        canEmail.setText(data.getEmailId());
        canCity.setText(data.getCityName()+ " , "+data.getCountryName());

        String dateStr = data.getBirthDte();
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = format.parse(dateStr);
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            dateStr = dateFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        canDob.setText(dateStr);



        fathername.setText(data.getMiddleName());
        nativeplace.setText(data.getNativePlace());
        mothername.setText(data.getMotherName());
        mothernative.setText(data.getMotherNativePlace());
        address.setText(data.getAddress());
        brother.setText(data.getBrothersHeadCount());
        sister.setText(data.getSistersHeadCount());
        aboutme.setText(data.getAboutMe());
        higerstedu.setText(data.getQualification());
        specialization.setText(data.getSpecialization());
        occupationa.setText(data.getOccupationName());
        annualincome.setText(data.getAnnualIncome());
        height.setText(data.getHeight() + " cms");
        weight.setText(data.getWeight() + " Kg");
        expectation.setText(data.getExpectationForPartner());

        String bdate = "";
//        String ar[] = data.getBirthDte().split("-",3);
//        bdate = getAge(Integer.parseInt(ar[0]),Integer.parseInt(ar[1]),Integer.parseInt(ar[2]));
        bdate = testDate(data.getBirthDte());
        age.setText(bdate);


    }
    private String testDate(String finalDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.parse(finalDate, formatter);
        Period p = Period.between(birthday, today);
        return p.getYears() + " years, " + p.getMonths() + " months";
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        String ageString = age + " Years";
        return ageString;
    }

}
