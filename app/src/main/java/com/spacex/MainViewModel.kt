package com.spacex

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacex.database.AppDatabase
import com.spacex.database.entity.Launch
import com.spacex.placeholder.JSONPlaceHolderApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val jsonPlaceHolderApi: JSONPlaceHolderApi) :
    ViewModel() {
    private var takenOnlyFromDatabase: Boolean = false
    private var check:Boolean? =  true
    private val preferences = "myPreferences"
    private val errorMessageLive = MutableLiveData<String>()
    private val checkString = "checkString"

    private lateinit var database: AppDatabase

    fun saveLaunchesToDB(
        activity: Activity
    ){
        jsonPlaceHolderApi.getLaunches()
            .enqueue(object : okhttp3.Callback, retrofit2.Callback<List<Launch>> {
                override fun onFailure(
                    call: retrofit2.Call<List<Launch>>,
                    t: Throwable
                ) {
                    takenOnlyFromDatabase = true

                    if(check!!) {
                        check = false
                        saveCheckToSharedPreference(activity, check!!)
                    }
                }

                override fun onResponse(
                    call: retrofit2.Call<List<Launch>>,
                    response: retrofit2.Response<List<Launch>>
                ) {
                    val body : List<Launch>? = response.body()
                    val bodyNotNull : List<Launch> = body!!

                    saveDataToDbAsync(activity, bodyNotNull)

                    takenOnlyFromDatabase = false

                    check =true
                    saveCheckToSharedPreference(activity, check!!)

                    saveTimeToSharedPreference(activity)
                }

                override fun onFailure(
                    call: okhttp3.Call,
                    e: IOException
                ) {
                    errorMessageLive.value = e.message

                    takenOnlyFromDatabase = true

                    if(check!!) {
//                        activity.findNavController(R.id.data_updated_time)
//                            .navigate(R.id.errorFragment)
                        check = false
                        saveCheckToSharedPreference(activity, check!!)
                    }
                }

                override fun onResponse(
                    call: okhttp3.Call,
                    response: okhttp3.Response
                ) {
                    check = true
                    saveCheckToSharedPreference(activity, check!!)
                }
            })
    }

    fun saveTimeToSharedPreference(context: Context?){
        val sharedPreference = context!!.getSharedPreferences(preferences, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putLong(context.getString(R.string.shared_preference_date_millis), System.currentTimeMillis())
        editor.apply()
    }

    fun saveCheckToSharedPreference(context: Context, check: Boolean){
        val sharedPreference = context.getSharedPreferences(preferences, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean(checkString, check)
        editor.apply()
    }

    private fun saveDataToDbAsync(context: Context, launches: List<Launch>){
        if (!::database.isInitialized) {
            database = AppDatabase.getInstance(context)!!
        }
        viewModelScope.launch(Dispatchers.IO) {
            run {
                database.getDao().insertLaunches(launches)
            }
        }
    }

    fun observeDataIdDB(spaceXApp: SpaceXApp): LiveData<List<Launch>> {
        return spaceXApp.database.getDao().getLaunches()
    }
}