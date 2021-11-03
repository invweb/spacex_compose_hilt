package com.spacex.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.spacex.database.entity.Launch
import com.spacex.database.entity.Links
import com.spacex.database.entity.Reuse
import com.spacex.database.entity.Rocket

class Converters {
    @TypeConverter
    fun fromLaunchJson(launchJson: String): Launch {
        val gson: Gson = Gson()
        return gson.fromJson(launchJson, Launch::class.java)
    }

    @TypeConverter
    fun launchToJson(launch: Launch): String? {
        val gson: Gson = Gson()
        return gson.toJson(launch)
    }

    @TypeConverter
    fun fromLinksJson(linksJson: String): Links {
        val gson: Gson = Gson()
        return gson.fromJson(linksJson, Links::class.java)
    }

    @TypeConverter
    fun linksToJson(links: Links): String? {
        val gson: Gson = Gson()
        return gson.toJson(links)
    }

    @TypeConverter
    fun fromReuseJson(reuseJson: String): Reuse {
        val gson: Gson = Gson()
        return gson.fromJson(reuseJson, Reuse::class.java)
    }

    @TypeConverter
    fun reuseToJson(reuse: Reuse): String? {
        val gson: Gson = Gson()
        return gson.toJson(reuse)
    }

    @TypeConverter
    fun fromRocketJson(reuseJson: String): Rocket {
        val gson: Gson = Gson()
        return gson.fromJson(reuseJson, Rocket::class.java)
    }

    @TypeConverter
    fun rocketToJson(rocket: Rocket): String? {
        val gson: Gson = Gson()
        return gson.toJson(rocket)
    }
}