package com.example.firstnetworkapi.service

import com.example.firstnetworkapi.model.SchoolsItem
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {

    /**
     * method to get the schools from server
     */
    @GET(SCHOOL_PATH)
    suspend fun getAllSchools(): Response<List<SchoolsItem>>

    companion object {
        // https://data.cityofnewyork.us/resource/s3k6-pzi2.json

        const val BASE_URL = "https://data.cityofnewyork.us/resource/"
        const val SCHOOL_PATH = "s3k6-pzi2.json"
    }
}