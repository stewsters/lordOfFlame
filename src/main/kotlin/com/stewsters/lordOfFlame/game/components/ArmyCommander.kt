package com.stewsters.lordOfFlame.game.components

import com.stewsters.lordOfFlame.game.Facing
import com.stewsters.lordOfFlame.map.HexMap
import com.stewsters.lordOfFlame.types.TerrainType
import org.hexworks.mixite.core.api.CubeCoordinate

class ArmyCommander(
    val members: MutableList<Soldier>,
    var armyCenter: CubeCoordinate,  //
    var stance: Stance = Stance.MUSTERING
) {

    val validCombatLocation = listOf(TerrainType.FIELDS, TerrainType.GRASSLAND, TerrainType.FOREST)

    fun rally(hexMap: HexMap, center: CubeCoordinate, facing: Facing) {


        val validLoc = center.getNeighbors().filter { coord->
            validCombatLocation.contains( hexMap.grid.getByCubeCoordinate(coord).get().satelliteData.get().type)
        }

        if(validLoc.isEmpty())
            return

        members.forEachIndexed { index, soldier -> soldier.order = Order(validLoc[index%validLoc.size] ) }


        // members
//        hexMap.grid.getNeighborsOf(center)

        // horses, infantry*, horses
        // arrow guys


    }


}

fun CubeCoordinate.getNeighbors(): List<CubeCoordinate> {

    return listOf(
        CubeCoordinate.fromCoordinates(gridX + 1, gridZ - 1),
        CubeCoordinate.fromCoordinates(gridX + 1, gridZ),
        CubeCoordinate.fromCoordinates(gridX, gridZ + 1),
        CubeCoordinate.fromCoordinates(gridX - 1, gridZ + 1),
        CubeCoordinate.fromCoordinates(gridX - 1, gridZ),
        CubeCoordinate.fromCoordinates(gridX, gridZ - 1)
    )
}


enum class Stance(
//    val cohesion:Int
) {
    MUSTERING(),
    MARCHING,
    COMBAT;
    // fleeing

}