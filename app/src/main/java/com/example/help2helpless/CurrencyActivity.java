package com.example.help2helpless;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.help2helpless.adapter.CurrencyAdapter;
import com.example.help2helpless.model.Sections;

import java.util.ArrayList;

public class CurrencyActivity extends AppCompatActivity {

    RecyclerView currencycontainer;
    ArrayList<Sections> currencies;
    CurrencyAdapter currencyAdapter;
    public  static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String activity_value="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        Bundle bundle= getIntent().getExtras();
        activity_value= bundle.getString("value");
        initializeAll();
        initializeArraylist();


        EditText editText = findViewById(R.id.edittext_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<Sections> filteredList = new ArrayList<>();

        for (Sections item : currencies) {
            if (item.getDivision().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        currencyAdapter.filterList(filteredList);
    }

    private void initializeArraylist() {
        currencies.add(new Sections("Dhaka","Dhaka"));
        currencies.add(new Sections("Dhaka","Faridpur"));
        currencies.add(new Sections("Dhaka","Gazipur"));
        currencies.add(new Sections("Dhaka","Gopalganj"));
        currencies.add(new Sections("Dhaka","Kishoreganj"));
        currencies.add(new Sections("Dhaka","Madaripur"));
        currencies.add(new Sections("Dhaka","Manikganj"));
        currencies.add(new Sections("Dhaka","Munshiganj"));
        currencies.add(new Sections("Dhaka","Narayanganj"));
        currencies.add(new Sections("Dhaka","Narsingdi"));
        currencies.add(new Sections("Dhaka","Rajbari"));
        currencies.add(new Sections("Dhaka","Shariatpur"));
        currencies.add(new Sections("Dhaka","Tangail"));
        currencies.add(new Sections("Khulna","Jessore"));
        currencies.add(new Sections("Khulna","Chuadanga"));
        currencies.add(new Sections("Khulna","Bagerhat"));
        currencies.add(new Sections("Khulna","Jhenaidah"));
        currencies.add(new Sections("Khulna","Khulna"));
        currencies.add(new Sections("Khulna","Kushtia"));
        currencies.add(new Sections("Khulna","Magura"));

        currencies.add(new Sections("Khulna","Meherpur"));
        currencies.add(new Sections("Khulna","Narail"));
        currencies.add(new Sections("Mymensingh","Jamalpur"));
        currencies.add(new Sections("Mymensingh","Mymensingh"));
        currencies.add(new Sections("Mymensingh","Netrakona"));
        currencies.add(new Sections("Mymensingh","Sherpur"));

        currencies.add(new Sections("Sylhet","Habiganj"));
        currencies.add(new Sections("Sylhet","Moulvibazar"));
        currencies.add(new Sections("Sylhet","Sunamganj"));
        currencies.add(new Sections("Sylhet","Sylhet"));

        currencies.add(new Sections("Rajshahi","Bogra"));
        currencies.add(new Sections("Rajshahi","Chapainawabganj"));
        currencies.add(new Sections("Rajshahi","Joypurhat"));
        currencies.add(new Sections("Rajshahi","Naogaon"));
        currencies.add(new Sections("Rajshahi","Natore"));
        currencies.add(new Sections("Rajshahi","Pabna"));
        currencies.add(new Sections("Rajshahi","Rajshahi"));
        currencies.add(new Sections("Rajshahi","Sirajganj"));

        currencies.add(new Sections("Rangpur","Rangpur"));
        currencies.add(new Sections("Rangpur","Dinajpur"));
        currencies.add(new Sections("Rangpur","Gaibandha"));
        currencies.add(new Sections("Rangpur","Kurigram"));
        currencies.add(new Sections("Rangpur","Lalmonirhat"));
        currencies.add(new Sections("Rangpur","Nilphamari"));
        currencies.add(new Sections("Rangpur","Panchagarh"));
        currencies.add(new Sections("Rangpur","Thakurgaon"));

        currencies.add(new Sections("Barisal","Barisal")); //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Barisal","Barguna"));
        currencies.add(new Sections("Barisal","Bhola"));
        currencies.add(new Sections("Barisal","Jhalokati"));
        currencies.add(new Sections("Barisal","Patuakhali"));
        currencies.add(new Sections("Barisal","Pirojpur"));
       // "Chittagong":["Bandarban","Brahmanbaria",   "Chandpur", "Chittagong", "Comilla",    "Cox's Bazar","Feni",     "Khagrachhari","Lakshmipur", "Noakhali", "Rangamati"],
        //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Chittagong","Chittagong"));
        currencies.add(new Sections("Chittagong","Bandarban"));
        currencies.add(new Sections("Chittagong","Brahmanbaria"));
        currencies.add(new Sections("Chittagong","Chandpur"));
        currencies.add(new Sections("Chittagong","Comilla")); //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Chittagong","Cox's Bazar"));
        currencies.add(new Sections("Chittagong","Feni"));
        currencies.add(new Sections("Chittagong","Khagrachhari"));


        currencies.add(new Sections("Chittagong","Lakshmipur"));
        currencies.add(new Sections("Chittagong","Noakhali")); //$€£¥₽₩₪฿₫₴₹
        currencies.add(new Sections("Chittagong","Rangamati"));


        currencyAdapter=new CurrencyAdapter(currencies, CurrencyActivity.this, 2);
        currencycontainer.setAdapter(currencyAdapter);






    }







    private void initializeAll() {


        currencies=new ArrayList<>();
        currencycontainer=findViewById(R.id.currency_container);
        currencycontainer.setHasFixedSize(true);
        currencycontainer.setLayoutManager(new LinearLayoutManager(CurrencyActivity.this));
    }
}
