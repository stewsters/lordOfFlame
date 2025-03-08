package com.stewsters.com.stewsters.lordOfFlame.game

import org.hexworks.mixite.core.api.CubeCoordinate

enum class Facing(val index: Int, val add: (CubeCoordinate) -> CubeCoordinate) {

    NORTH(0, { o: CubeCoordinate -> CubeCoordinate.fromCoordinates(o.gridX, o.gridZ - 1) }),
    NORTHEAST(1, { o: CubeCoordinate -> CubeCoordinate.fromCoordinates(o.gridX + 1, o.gridZ - 1) }),
    SOUTHEAST(2, { o: CubeCoordinate -> CubeCoordinate.fromCoordinates(o.gridX + 1, o.gridZ) }),
    SOUTH(3, { o: CubeCoordinate -> CubeCoordinate.fromCoordinates(o.gridX, o.gridZ + 1) }),
    SOUTHWEST(4, { o: CubeCoordinate -> CubeCoordinate.fromCoordinates(o.gridX - 1, o.gridZ + 1) }),
    NORTHWEST(5, { o: CubeCoordinate -> CubeCoordinate.fromCoordinates(o.gridX - 1, o.gridZ) });

    fun rotateLeft() =
        entries[(index - 1 + 6) % 6]


    fun rotateRight() =
        entries[(index + 1) % 6]

    fun angle(): Float {
        return index * 60f
    }

}

fun CubeCoordinate.plus(facing: Facing): CubeCoordinate {
    return facing.add(this)
}
