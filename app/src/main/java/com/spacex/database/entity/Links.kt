package com.spacex.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Links (
    @ColumnInfo(name = "mission_patch")
    @SerializedName("mission_patch")
    val missionPatch: String,

    @ColumnInfo (name = "mission_patch_small")
    @SerializedName("mission_patch_small")
    val missionPatchSmall: String
) : Parcelable