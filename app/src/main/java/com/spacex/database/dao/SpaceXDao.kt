package com.spacex.database.dao

import com.spacex.database.entity.Launch
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SpaceXDao {
    @Query("SELECT * FROM Launch WHERE launch_year LIKE :year ")
    fun getLaunchesWithWhere(year: Int): LiveData<List<Launch>>

    @Query("SELECT * FROM Launch")
    fun getLaunches(): LiveData<List<Launch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLaunches(launchListList: List<Launch>)

    //@Query("DELETE FROM Launch")
    //fun truncateLaunch()
}
