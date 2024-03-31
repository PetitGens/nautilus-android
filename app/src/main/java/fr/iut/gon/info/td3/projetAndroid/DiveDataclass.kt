package fr.iut.gon.info.td3.projetAndroid

data class DiveDataclass(
    val date: String = "2024-01-01",
    val hour: String = "9h",
    val level: String = "PA",
    val depth: Int = 20,
    val location: String = "Un super lieu",
    val nbSpots: Int = 4,
    val nbTakenSpots: Int = 1,
    val dive: Unit? = null,
    var isRegistered: Boolean = false,
    val participants: MutableList<String> = mutableListOf()
)