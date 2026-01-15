package com.stewsters.lordOfFlame.game.components

import com.stewsters.lordOfFlame.game.ai.Ai
import com.stewsters.lordOfFlame.game.ai.MeleeAi
import com.stewsters.lordOfFlame.game.ai.PlayerAi
import processing.core.PImage

enum class SoldierType(
    val displayName: String,
    val maxHp: Int,
    val maxMorale: Int,
    val groundSpeed: Int,
    val groundTurnSpeed: Int,
    val attacks: List<Attack> = listOf(),
    val defaultAi: Ai = MeleeAi(),
) {


    DRAGON(
        displayName = "Dragon",
        maxHp = 100,
        maxMorale = 1000,
        groundSpeed = 100,
        groundTurnSpeed = 30,
        defaultAi = PlayerAi(),
        attacks = listOf(
            Attack(damage = 100, armorPierce = 20, range = 1, timeCost = 100)
        )
    ),

    // TODO ranged
    ARCHERS(
        displayName = "Archers",
        maxHp = 100,
        maxMorale = 100,
        groundSpeed = 100,
        groundTurnSpeed = 20,
        attacks = listOf(
            Attack(damage = 20, armorPierce = 1, range = 1, timeCost = 100),
            Attack(damage = 40, armorPierce = 2, range = 4, timeCost = 100),
        ),
    ),

    // TODO: fast, expensive, charge
    CAVALRY(
        displayName = "Cavalry",
        maxHp = 200,
        maxMorale = 100,
        groundSpeed = 50,
        groundTurnSpeed = 40,
        attacks = listOf(
            Attack(damage = 60, armorPierce = 30, range = 1, timeCost = 100),
        )
    ),

    // TODO: armored, can ignore armor/shields
    GREATSWORDSMEN(
        displayName = "Greatswordsmen",
        maxHp = 100,
        maxMorale = 100,
        groundSpeed = 100,
        groundTurnSpeed = 20,
        attacks = listOf(
            Attack(damage = 60, armorPierce = 100, range = 1, timeCost = 100),
        )
    ),

    // TODO: defense vs front arc, charge
    SPEARMEN(
        displayName = "Spearmen",
        maxHp = 100,
        maxMorale = 100,
        groundSpeed = 100,
        groundTurnSpeed = 20,
        attacks = listOf(
            Attack(damage = 40, armorPierce = 10, range = 1, timeCost = 100),
        )
    ),

    // TODO: shielded
    SWORDSMEN(
        displayName = "Swordsmen",
        maxHp = 100,
        maxMorale = 100,
        groundSpeed = 100,
        groundTurnSpeed = 20,
        attacks = listOf(
            Attack(damage = 50, armorPierce = 10, range = 1, timeCost = 100),
        )
    );


    lateinit var icon: PImage   // will be filled in later
}