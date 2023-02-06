package com.example.firstnetworkapi.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstnetworkapi.databinding.LetterItemBinding
import com.example.firstnetworkapi.databinding.SchoolItemBinding
import com.example.firstnetworkapi.model.SchoolsItem

class SchoolAdapter(
    private val schoolSet: MutableList<ViewType> = mutableListOf(),
    private val onClickedSchool: (SchoolsItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateSchools(newSchools: List<SchoolsItem>) {
        var tempChar = '+'
        newSchools.sortedBy { it.schoolName }.forEach { school ->
            val firstLetter = school.schoolName?.first() ?: '+'
            if (firstLetter != tempChar) {
                schoolSet.add(ViewType.LETTER(firstLetter.toString()))
                tempChar = firstLetter
            }
            schoolSet.add(ViewType.SCHOOL(school))
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            LetterViewHolder(
                LetterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            SchoolViewHolder(
                SchoolItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = schoolSet[position]) {
            is ViewType.SCHOOL -> {
                (holder as SchoolViewHolder).schoolBinding(item.schoolItem, onClickedSchool)
            }
            is ViewType.LETTER -> {
                (holder as LetterViewHolder).bindLetter(item.letter)
            }
        }
    }

    override fun getItemCount(): Int = schoolSet.size

    override fun getItemViewType(position: Int): Int {
        return when(schoolSet[position]) {
            is ViewType.SCHOOL -> 1
            is ViewType.LETTER -> 0
        }
    }
}

class SchoolViewHolder(
    private val binding: SchoolItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun schoolBinding(school: SchoolsItem, onClickedSchool: (SchoolsItem) -> Unit) {
        binding.schoolName.text = school.schoolName
        binding.schoolAddress.text = school.location
        binding.schoolPhone.text = school.phoneNumber

        binding.moreBtn.setOnClickListener {
            onClickedSchool(school)
        }

        //Open Implicit intent (Maps) by clicking address
        binding.schoolAddress.setOnClickListener {
            val location = school.location?.substringAfterLast("(")
            val latitude = location?.substringBefore(",")
            val longitude = school.location?.substringAfterLast(",")?.substringBefore(")")
            val gmmIntentUri = Uri.parse("geo:$latitude,$longitude"+"?q="+"$latitude,$longitude(Here is your school selection: ${school.schoolName})")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            it.context.startActivity(mapIntent)

        }

    }
}

class LetterViewHolder(
    private val binding: LetterItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindLetter(letter: String) {
        binding.letterName.text = letter
    }
}