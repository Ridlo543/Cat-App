package com.l0122138.ridlo.ppab_09.utils

import com.l0122138.ridlo.ppab_09.model.Cat
import com.l0122138.ridlo.ppab_09.model.ImageInfo
import com.l0122138.ridlo.ppab_09.model.VoteRequest
import com.l0122138.ridlo.ppab_09.model.VoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("v1/images/search?limit=10")
    suspend fun getCats(): List<Cat>

    @POST("v1/votes")
    suspend fun postVote(@Body voteRequest: VoteRequest): Response<VoteResponse>

    @GET("v1/votes")
    suspend fun getVotes(
        @Query("sub_id") subId: String,
        @Query("limit") limit: Int,
        @Query("order") order: String = "DESC"
    ): Response<List<VoteResponse>>

    @GET("v1/images/search")
    suspend fun getRandomCatImage(@Query("limit") limit: Int = 1): Response<List<ImageInfo>>
}
