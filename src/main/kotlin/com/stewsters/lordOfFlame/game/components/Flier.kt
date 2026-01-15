package com.stewsters.lordOfFlame.game.components

// fly height  0 - 3
// 0 - lake, grass, tree (though trees should block los 1 higher)
// 1 - hills
// 2 - mountains

class Flier(
    var airspeed: Int, // giving you an idea of how fast they are moving. movement cost will decrease, makes it harder to hit dragon
    var elevation: Int, //giving idea of height.  Cant be spent to boost airspeed, makes it harder to hit dragon

) {

    val averageAirspeed: Int = 3
    val minAirspeed: Int = 1
    val maxAirspeed: Int = 6

    val maxHeight = 4
}