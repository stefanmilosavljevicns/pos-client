package com.example.payten_template.repositories

import com.example.payten_template.domain.Location
import com.example.payten_template.repositories.source.RemoteLocationDataSource

class LocationRepository(
    private val remoteLocationDataSource: RemoteLocationDataSource = RemoteLocationDataSource()
) {
    suspend fun getAllLocations(): List<Location>{
        return remoteLocationDataSource.getAllLocations()
    }
}