package com.stewsters.com.stewsters.lordOfFlame.game

import com.stewsters.com.stewsters.lordOfFlame.game.ai.Ai
import com.stewsters.com.stewsters.lordOfFlame.game.ai.MeleeAi
import com.stewsters.com.stewsters.lordOfFlame.game.ai.PlayerAi
import processing.core.PImage

enum class SoldierType(
    val displayName: String,
    val maxHp: Int,
    val maxMorale: Int,
    val groundSpeed: Int,
    val groundTurnSpeed: Int,
    val attacks: List<Attack> = listOf(), //range / damage / armorPierce?
    val defaultAi: Ai = MeleeAi(),
) {


    DRAGON(
        displayName = "Dragon",
        maxHp = 100,
        maxMorale = 1000,
        groundSpeed = 100,
        groundTurnSpeed = 30,
        defaultAi = PlayerAi()
    ),

    // TODO ranged
    ARCHERS(
        displayName = "Archers",
        maxHp = 100,
        maxMorale = 100,
        groundSpeed = 100,
        groundTurnSpeed = 20
    ),

    // TODO: fast, expensive, charge
    CAVALRY(
        displayName = "Cavalry",
        maxHp = 200,
        maxMorale = 100,
        groundSpeed = 50,
        groundTurnSpeed = 40
    ),

    // TODO: armored, can ignore armor/shields
    GREATSWORDSMEN(
        displayName = "Greatswordsmen",
        maxHp = 100,
        maxMorale = 100,
        groundSpeed = 100,
        groundTurnSpeed = 20
    ),

    // TODO: defense vs front arc, charge
    SPEARMEN(
        displayName = "Spearmen",
        maxHp = 100,
        maxMorale = 100,
        groundSpeed = 100,
        groundTurnSpeed = 20
    ),

    // TODO: shielded
    SWORDSMEN(
        displayName = "Swordsmen",
        maxHp = 100,
        maxMorale = 100,
        groundSpeed = 100,
        groundTurnSpeed = 20
    );


    lateinit var icon: PImage   // will be filled in later
}