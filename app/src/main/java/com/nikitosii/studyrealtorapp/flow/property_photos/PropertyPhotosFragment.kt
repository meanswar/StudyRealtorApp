package com.nikitosii.studyrealtorapp.flow.property_photos

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.util.ext.model.getTransitionName
import com.nikitosii.studyrealtorapp.databinding.FragmentPropertyPhotosBinding
import com.nikitosii.studyrealtorapp.util.ext.attachPagerSnap
import dagger.android.support.DaggerFragment

class PropertyPhotosFragment : DaggerFragment(R.layout.fragment_property_photos) {

    lateinit var binding: FragmentPropertyPhotosBinding

    private val adapter by lazy { PropertyPhotoAdapter() }
    private val args: PropertyPhotosFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPropertyPhotosBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            postponeEnterTransition()
            rvPhotos.adapter = adapter
            adapter.submitList(args.container.photos)
            val transitionName = args.container.getTransitionName()
            rvPhotos.smoothScrollToPosition(args.container.idStart)
            rvPhotos.transitionName = transitionName
            rvPhotos.attachPagerSnap()
            startPostponedEnterTransition()
        }
    }
}