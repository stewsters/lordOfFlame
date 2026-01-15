package com.stewsters.lordOfFlame.game

import org.hexworks.mixite.core.api.CubeCoordinate

enum class Facing(
    val index: Int,
    val offset: CubeCoordinate,
) {

    NORTH(0, CubeCoordinate.fromCoordinates(0, -1)),
    NORTHEAST(1, CubeCoordinate.fromCoordinates(1, -1)),
    SOUTHEAST(2, CubeCoordinate.fromCoordinates(1, 0)),
    SOUTH(3, CubeCoordinate.fromCoordinates(0, +1)),
    SOUTHWEST(4, CubeCoordinate.fromCoordinates(-1, 1)),
    NORTHWEST(5, CubeCoordinate.fromCoordinates(-1, 0));

    fun rotateLeft() =
        entries[(index - 1 + 6) % 6]


    fun rotateRight() =
        entries[(index + 1) % 6]

    fun angle(): Float {
        return index * 60f
    }

}

