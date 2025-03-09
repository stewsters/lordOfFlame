package com.stewsters.lordOfFlame.types

import processing.core.PImage


enum class TerrainType(
    val height: Int,
    val color: Int,
    val traversalCost: Double = 1.0,
    val multiIcon: Boolean = false,
    val blocksWalker: Boolean = false
) {

    URBAN(0, 0xFFEEEEEE.toInt()),
    TOWN(0, 0xFFA52A2A.toInt()),

    // grasslands
    // marsh
    SHALLOW_WATER(0, 0xFFadd8e7.toInt(), traversalCost = 50.0, blocksWalker = true),
    DEEP_WATER(0, 0xFF9dc8d7.toInt(), traversalCost = 100.0, blocksWalker = true),
    GRASSLAND(0, 0xFFbdd5a2.toInt(), traversalCost = 2.0),
    FIELDS(0, 0xFFaaaa00.toInt(), traversalCost = 2.0, multiIcon = true),
    FOREST(0, 0xff83a65a.toInt(), traversalCost = 4.0, multiIcon = true),
    HILL(1, 0xff9fc5ad.toInt(), traversalCost = 10.0),
    MOUNTAIN(2, 0xffb7b7b7.toInt(), traversalCost = 50.0, blocksWalker = true);

    var icons: List<PImage> = listOf()
}