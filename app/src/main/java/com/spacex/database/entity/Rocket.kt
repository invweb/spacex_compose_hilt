package com.spacex.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Rocket (
    @ColumnInfo(name = "rocket_id")
    @SerializedName("rocket_id")
    val rocketId: String,

    @ColumnInfo (name = "rocket_name")
    @SerializedName("rocket_name")
    val rocketName: String,

    @ColumnInfo (name = "rocket_type")
    @SerializedName("rocket_type")
    val rocketType: String
) : Parcelable