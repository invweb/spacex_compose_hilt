package com.spacex.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Reuse (
        @ColumnInfo(name = "core")
        @SerializedName("core")
        val core: Boolean,

        @ColumnInfo(name = "side_core1")
        @SerializedName("side_core1")
        val side_core1: Boolean,

        @ColumnInfo(name = "side_core2")
        @SerializedName("side_core2")
        val side_core2: Boolean,

        @ColumnInfo(name = "fairings")
        @SerializedName("fairings")
        val fairings: Boolean,

        @ColumnInfo(name = "capsule")
        @SerializedName("capsule")
        val capsule: Boolean
) : Parcelable
