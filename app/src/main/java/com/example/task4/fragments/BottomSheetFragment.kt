package com.example.task4.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import com.example.task4.R
import com.example.task4.repository.Repository
import com.example.task4.viewModels.HabitsListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : BottomSheetDialogFragment(), TextWatcher {
    private lateinit var viewModel: HabitsListViewModel

    companion object {
        private const val ASCENDING_SORTING_MODE = "ascending"
        private const val DESCENDING_SORTING_MODE = "descending"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this.requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitsListViewModel(Repository(requireContext())) as T
            }
        }).get(HabitsListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchInput.addTextChangedListener(this@BottomSheetFragment)

        viewModel.sortingMode.observe(this.activity as LifecycleOwner, Observer {
            when (it) {
                ASCENDING_SORTING_MODE -> {
                    ascendingSortingControl.isChecked = true
                    descendingSortingControl.isChecked = false
                }
                DESCENDING_SORTING_MODE -> {
                    ascendingSortingControl.isChecked = false
                    descendingSortingControl.isChecked = true
                }
                else -> {
                    ascendingSortingControl.isChecked = false
                    descendingSortingControl.isChecked = false
                }
            }
        })

        viewModel.searchQuery.observe(this.activity as LifecycleOwner, Observer {
            if (searchInput.text.toString() != it) {
                searchInput.setText(it)
            }
        })

        ascendingSortingControl.setOnClickListener {
            if (ascendingSortingControl.isChecked) {
                viewModel.setSortingMode(ASCENDING_SORTING_MODE)
            }
            else {
                viewModel.setSortingMode(null)
            }

        }

        descendingSortingControl.setOnClickListener {
            if (descendingSortingControl.isChecked) {
                viewModel.setSortingMode(DESCENDING_SORTING_MODE)
            }
            else {
                viewModel.setSortingMode(null)
            }

        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        return
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        viewModel.setSearchQuery(text.toString())
    }

    override fun afterTextChanged(p0: Editable?) {
        return
    }
}