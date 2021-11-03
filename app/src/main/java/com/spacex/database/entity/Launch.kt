package com.spacex.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "launch")
data class Launch (
    @ColumnInfo (name = "details")
    @SerializedName("details")
    var details: String?,

    @ColumnInfo(name = "mission_name")
    @SerializedName("mission_name")
    val missionName: String,

    @ColumnInfo (name = "flight_number")
    @SerializedName("flight_number")
    @PrimaryKey
    val flightNumber: Int,

    @ColumnInfo (name = "launch_year")
    @SerializedName("launch_year")
    val launchYear: Int,

    @ColumnInfo (name = "rocket")
    @SerializedName("rocket")
    val rocket: Rocket,

    @ColumnInfo (name = "links")
    @SerializedName("links")
    val links: Links,

    @ColumnInfo (name = "reuse")
    @SerializedName("reuse")
    val reuse: Reuse
) : Parcelable