package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class MeFragment : Fragment() {


    private var title: String? = "我的";

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fg_content,container,false);
        val txtContent: TextView = view.findViewById(R.id.txt_content);
        txtContent.text = title;
        return view;
    }
}