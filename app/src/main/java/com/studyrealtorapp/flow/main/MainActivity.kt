package com.studyrealtorapp.flow.main

import com.nikitosii.studyrealtorapp.databinding.ActivityMainBinding
import com.studyrealtorapp.flow.base.BaseActivity
import com.studyrealtorapp.util.annotation.RequiresViewModel

@RequiresViewModel(MainViewModel::class)
class MainActivity : BaseActivity<MainViewModel>({ ActivityMainBinding.inflate(it) }) {
}