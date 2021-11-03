package com.spacex.hilt

import com.spacex.placeholder.JSONPlaceHolderApi
import com.spacex.placeholder.JsonPlaceholder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class SpacexHiltModule {
    @Provides
    fun provideApiService(): JSONPlaceHolderApi = JsonPlaceholder.Factory.create()
}