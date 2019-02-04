package com.example.android.converterlanguages.Controller;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.converterlanguages.Model.DBHelper;
import com.example.android.converterlanguages.R;

import java.security.PrivateKey;

public class LanguagesActivity extends AppCompatActivity {
    Toolbar toolbar;
    private DBHelper mDBHelper;
    String cName, pascalName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);

        mDBHelper = new DBHelper(this);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("C++"));
        tabLayout.addTab(tabLayout.newTab().setText("Pascal"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LanguagesActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_one, null);
            final EditText nameC = mView.findViewById(R.id.inputNameC);
            Button ok = mView.findViewById(R.id.btnOk);
            builder.setView(mView);
            final AlertDialog dialog = builder.create();

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cName = nameC.getText().toString();
                    mDBHelper.addOne(cName);
                        nameC.setError(null);
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(LanguagesActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_two, null);
                        final EditText namePascal = mView.findViewById(R.id.inputNamePascal);
                        Button ok2 = mView.findViewById(R.id.btnOkPascal);
                        builder2.setView(mView);
                        final AlertDialog dialog2 = builder2.create();
                        dialog2.show();
                        ok2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pascalName = namePascal.getText().toString();
                                mDBHelper.addTwo(pascalName, cName);
                                if(pascalName.isEmpty()) {
                                    namePascal.setError("Название выражения должно быть заполнено");
                                }
                                dialog2.dismiss();
                                Toast.makeText(LanguagesActivity.this, "Выражения успешно добавлены", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();


                }

            });

            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
