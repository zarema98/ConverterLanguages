package com.example.android.converterlanguages.Controller;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.converterlanguages.Model.DBHelper;
import com.example.android.converterlanguages.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{
    FloatingActionButton fab;
    private Spinner spinner;
    private ArrayList<String> languages = new ArrayList<>();

    private DBHelper mDBHelper;
    private EditText edit;
    private ImageButton convert;
    private TextView result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DBHelper(this);
        fab = findViewById(R.id.btnAdd);
        spinner = findViewById(R.id.spinner);
        edit = findViewById(R.id.editValue);
        convert = findViewById(R.id.btnConvert);
        result = findViewById(R.id.txtResult);
        spinner.setOnItemSelectedListener(this);

        languages.add("c++");
        languages.add("pascal");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LanguagesActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if(item.equals("c++")) {
            convertCPlusPlus();

        } else if(item.equals("pascal")) {
            convertPascal();


        }

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void convertCPlusPlus() {
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String current;
                 String cPlus;
                int last = 0;
                ArrayList<Integer> lIndexes = new ArrayList<>();
                HashMap<String, String> pair = new HashMap<>();
                int j=0;
                String value="";
                int start, end, total;
                current = edit.getText().toString();
                pair.clear();
                lIndexes.clear();
                cPlus = mDBHelper.searchCPlus(current.substring(0,2));
                String pascal = mDBHelper.getPascal(cPlus);
                // Toast.makeText(MainActivity.this, "size: " + pascal, Toast.LENGTH_LONG).show();
                for(int i=0; i < cPlus.length(); i++) {
                    if(cPlus.charAt(i) == '#') {
                        lIndexes.add(i);
                        if(j!=0) {
                            end = cPlus.indexOf('#', lIndexes.get(lIndexes.size()-2));
                            start = cPlus.indexOf('#', i);
                            total = start - end;
                            j = last + total;
                        } else {
                            j = i + 1;
                        }
                        while(j < current.length() && current.charAt(j) != ' ') {
                            value = value.concat(String.valueOf(current.charAt(j)));
                            last = j;
                            j++;
                        }


                        if(!pair.containsKey(String.valueOf(cPlus.charAt(i+1)))) {
                            pair.put(String.valueOf(cPlus.charAt(i+1)), value);
                        }
                    }
                    value =" ";
                }
                StringBuilder str = new StringBuilder(pascal);
                for(int i=0; i < str.length(); i++) {
                    if(str.charAt(i) == '#') {
                        str.replace(i+1, i+2, pair.get(String.valueOf(str.charAt(i+1))));
                    }
                }
                pascal = str.toString();
                pascal = pascal.replaceAll("[#]", "");
                if(pascal.length() > 0) {
                    result.setText(pascal);
                } else {
                    result.setText("Вы ввели неверное выражение");
                }

            }
        });

    }

    public void convertPascal() {
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current;
                String pascal;
                int last = 0;
                ArrayList<Integer> lIndexes = new ArrayList<>();
                HashMap<String, String> pair = new HashMap<>();
                int j=0;
                String value="";
                int start, end, total;
                current = edit.getText().toString();
                pair.clear();
                lIndexes.clear();
                pascal = mDBHelper.search(current.substring(0,2));
                String c_plus = mDBHelper.getCPlus(pascal);
                // Toast.makeText(MainActivity.this, "size: " + pascal, Toast.LENGTH_LONG).show();
                for(int i=0; i < pascal.length(); i++) {
                    if(pascal.charAt(i) == '#') {
                        lIndexes.add(i);
                        if(j!=0) {
                            end = pascal.indexOf('#', lIndexes.get(lIndexes.size()-2));
                            start = pascal.indexOf('#', i);
                            total = start - end;
                            j = last + total;
                        } else {
                            j = i + 1;
                        }
                        while(j < current.length() && current.charAt(j) != ' ') {
                            value = value.concat(String.valueOf(current.charAt(j)));
                            last = j;
                            j++;
                        }


                        if(!pair.containsKey(String.valueOf(pascal.charAt(i+1)))) {
                            pair.put(String.valueOf(pascal.charAt(i+1)), value);
                        }
                    }
                    value =" ";
                }
                StringBuilder str = new StringBuilder(c_plus);
                for(int i=0; i < str.length(); i++) {
                    if(str.charAt(i) == '#') {
                        str.replace(i+1, i+2, pair.get(String.valueOf(str.charAt(i+1))));
                    }
                }
                c_plus = str.toString();
                c_plus = c_plus.replaceAll("[#]", "");
                if(c_plus.length() > 0) {
                    result.setText(c_plus);
                } else {
                    result.setText("Вы ввели неверное выражение");
                }

            }
        });

    }
}
