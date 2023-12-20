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
{
    // Tambahkan konstruktor tanpa argumen
    constructor() : this("", "", "", "", "","","","")
}

data class RacikanObat(
    val id: String,
    val sakit:String,
    val obat1: String,
    val obat2: String,
    val obat3: String,
){
    // Tambahkan konstruktor tanpa argumen
    constructor() : this("", "", "", "", "")
}

data class Reminder (
    val id: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val color: String,
)
{
    // Tambahkan konstruktor tanpa argumen
    constructor() : this("", "", "", "", "","")
}

data class Vacsine (
    val id: String,
    val date: String,
    val location: String,
) {
    // Tambahkan konstruktor tanpa argumen
    constructor() : this("", "", "")
}

