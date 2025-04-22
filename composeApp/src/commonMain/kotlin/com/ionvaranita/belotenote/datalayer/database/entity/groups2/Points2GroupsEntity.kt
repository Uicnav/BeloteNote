package com.ionvaranita.belotenote.datalayer.database.entity.groups2

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ionvaranita on 20/11/17.
 */
@Entity
data class Points2GroupsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idGame: Short,
    val pointsWe: Short = 0,
    val pointsYouP: Short = 0,
    val pointsGame: Short
)
