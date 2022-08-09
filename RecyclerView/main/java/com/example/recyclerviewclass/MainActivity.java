package com.example.recyclerviewclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.recyclerviewclass.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    AdapterClass adapterClass;

    List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list.add("One");
        list.add("Two");
        list.add("One");
        list.add("Two");
        list.add("One");
        list.add("Two");
        list.add("One");
        list.add("Two");
        list.add("One");
        list.add("Two");

        adapterClass=new AdapterClass(list,this);

        binding.recyclerView.setAdapter(adapterClass);


    }

}