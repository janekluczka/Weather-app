package com.example.wz_tracker.repositories

import com.example.wz_tracker.database.Match
import com.example.wz_tracker.database.WzDao
import com.example.wz_tracker.models.LifetimeStats
import com.example.wz_tracker.models.MatchesStats
import com.example.wz_tracker.network.MwApiService

class WzRepositoryImpl(private val database: WzDao) : WzRepository {

    override suspend fun fetchWzMatches(username: String, numbers: String) =
        try {
            val user = mergeUsernameAndNumbers(username, numbers)
            val matches = MwApiService.retrofitService.fetchWzMatches(user)

            database.deleteAllMatches()

            saveMatches(matches)

            database.getAllMatches()
        } catch (e: Exception) {
            null
        }

    private fun saveMatches(matches: MatchesStats) {
        matches.matches.forEachIndexed { index, wzMatch ->
            database.insertMatch(
                match = Match(
                    id = index,
                    place = wzMatch.playerStats.teamPlacement,
                    kills = wzMatch.playerStats.kills,
                    deaths = wzMatch.playerStats.deaths,
                    damageDone = wzMatch.playerStats.damageDone,
                    damageTaken = wzMatch.playerStats.damageTaken,
                    date = wzMatch.utcStartSeconds,
                    mode = wzMatch.mode
                )
            )
        }
    }

    override suspend fun fetchWzLifetimeStats(username: String, numbers: String): LifetimeStats? =
        try {
            // handle saving response
            val user = mergeUsernameAndNumbers(username, numbers)
            MwApiService.retrofitService.fetchWzLifetimeStats(user)
        } catch (e: Exception) {
            null
        }

    private fun mergeUsernameAndNumbers(username: String, numbers: String) = "$username%23$numbers"

    override fun getMatches(): List<Match> = database.getAllMatches()

    override fun getWzMatchByID(id: Int) = database.getMatchById(id)

}