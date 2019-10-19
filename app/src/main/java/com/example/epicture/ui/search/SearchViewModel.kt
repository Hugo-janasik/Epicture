package com.example.epicture.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Nothing To Display"
    }
    val text: LiveData<String> = _text
}