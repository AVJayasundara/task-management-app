package com.example.notesapp

data class Note(val id:Int,val title:String,val content: String,var isChecked: Boolean = false)
