package com.medimemo.data

data class MedicalRecord(
    val id: String,
    val date: String,
    val anamesis: String,
    val pemeriksaanFisik: String,
    val diagnosis: String,
    val tindakan: String,
    val pelayananLain: String,
    val namaDokter: String
)

data class RacikanObat(
    val id: String,
    val sakit:String,
    val obat1: String,
    val obat2: String,
    val obat3: String,
)

data class Reminder (
    val id: String,
    val date: String,
    val time: String,
    val location: String,
    val color: String,
)

data class Vaksinasi (
    val id: String,
    val date: String,
    val location: String,
)