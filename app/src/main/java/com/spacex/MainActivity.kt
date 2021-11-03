package com.spacex

import android.os.Bundle
import android.telecom.Call
import android.util.EventLogTags
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.spacex.database.entity.Launch
import com.spacex.theme.SpacexComposeTheme
import dagger.hilt.android.AndroidEntryPoint

private var launch: Launch? = null

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var missionFlag: Boolean = false
    lateinit var launches: List<Launch>
    private var activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.saveLaunchesToDB(this)
        setContent {
            SpacexComposeTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = getString(R.string.app_name))
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        if (missionFlag) {
                                            missionFlag = false
                                            if(navController.currentDestination.toString() != "launches") {
                                                navController.popBackStack()
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Filled.ArrowBack, "")
                                    }
                                },
                                backgroundColor = Color.Blue,
                                contentColor = Color.White,
                                elevation = 12.dp
                            )

                        }, content = {
                            viewModel.observeDataIdDB(application as SpaceXApp).observe(this, {
                                launches = it
                            })

                            NavHost(
                                navController = navController,
                                startDestination = "launches"
                            ) {
                                composable("launches") {
                                    ShowLaunches(
                                        activity,
                                        navController,
                                        viewModel.observeDataIdDB(application as SpaceXApp)
                                    )
                                }
                                composable("launch") {
                                    missionFlag = true
                                    ShowLaunchComposable(navController, launch)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    @ExperimentalCoilApi
    @Composable
    private fun ShowLaunchComposable(navController: NavHostController, launch: Launch?) {
        Scaffold(modifier = Modifier.padding(0.dp), content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                launch?.let {
                    Title(itemName = it.missionName)

                    val details = it.details

                    val missionPatch = it.links.missionPatch
                    Details(itemDetails = details, missionPatch)
                }
            }
        })
    }

    @ExperimentalCoilApi
    @Composable
    private fun ShowLaunches(activity: MainActivity, navController: NavHostController, launches: LiveData<List<Launch>>) {
        Scaffold(modifier = Modifier.padding(0.dp), content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val launches: List<Launch?> by launches.observeAsState(listOf())
                TableComposable(navController, launches)
            }
        })
    }

    @Composable
    fun TableComposable(navController: NavHostController, myItems: List<Launch?>) {
        LazyColumn(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
        ) {
            items(items = myItems, itemContent = { item ->
                Column(modifier = Modifier.clickable(
                    onClick = {
                        launch = item
                        navController.navigate("launch")
                    }
                )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp, 10.dp, 0.dp, 10.dp)
                    ) {
                        item?.missionName?.let {
                            Title(itemName = it)
                        }
                    }
                    Row {
                        Divider(
                            color = Color.Black,
                            thickness = 1.dp
                        )
                    }
                }
            })
        }
    }

    @Composable
    fun Title(itemName: String) {
        Text(
            text = getString(R.string.missionName)
                .plus(" ")
                .plus(itemName),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }

    @ExperimentalCoilApi
    @Composable
    fun Details(itemDetails: String?, missionPatch: String?) {
        val detStable: String = if (itemDetails == null){
            ""
        } else {
            itemDetails
        }
        Text(
            text = getString(R.string.details)
                .plus(" ")
                .plus(detStable),
            fontSize = 15.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .padding(8.dp, 0.dp, 8.dp, 0.dp)
        )

        missionPatch?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(256.dp)
            )
        }
    }

}

