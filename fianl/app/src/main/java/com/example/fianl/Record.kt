package com.example.fianl

data class Record(
    val id: Long = 0,
    val date: String,
    val weight: String,
    val status: String,
    val sleep: String,
    val exercised: Boolean,
    val pregnant: Boolean
)
