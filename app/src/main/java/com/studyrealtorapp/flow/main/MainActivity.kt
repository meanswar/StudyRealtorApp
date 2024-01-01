package com.studyrealtorapp.flow.main

import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ActivityMainBinding
import com.studyrealtorapp.flow.base.BaseActivity

class MainActivity :
    BaseActivity<MainViewModel>({ ActivityMainBinding.inflate(it) }, R.layout.activity_main) {
}