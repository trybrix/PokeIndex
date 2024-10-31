import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Creates a DataStore instance associated with the context
val Context.dataStore by preferencesDataStore(name = "quiz_stats")

object StatsDataStore {

    /*
    * todo[ ] - review
    *   private fun getTotalScoreKey(userKey: String) - fun that takes userKey as an input
    *   intPreferencesKey - fun that creates a unique "key" that the app can use to store a particular value
    *   "${userKey}_total_score") - combines userKey to the "_total_score" - "123_total_score"
    */
    private fun getTotalScoreKey(userKey: String) = intPreferencesKey("${userKey}_total_score")

    //private fun getTotalCorrectAnswersKey(userKey: String) - fun that takes userKey as an input
    //intPreferencesKey - fun that creates a unique "key" that the app can use to store a particular value
    //"${userKey}_total_correct_answers") - combines userKey to the "total_correct_score" - "123_total_correct_score"
    private fun getTotalCorrectAnswersKey(userKey: String) = intPreferencesKey("${userKey}_total_correct_answers")

    /*
    * todo[ ] - review datastore
    *  suspend fun - fun is suspendable - it can pause and wait if needed - useful for saving data in the background
    *  --without slowing down the app
    *  saveQuizStats - fun name and saves the quiz stats
    *  context: Context - information about where the fun is being called in the app
    *  --helps the fun know where to store the data
    *  userKey: String - user's unique identifier (would be the email)
    *   used to label data so that it's saved  specifically for this user
    *  totalScore: Int and totalCorrectAnswer: Int - actual data points being saved for the user
    *  --their total score and the number of questions right
    */
    suspend fun saveQuizStats(context: Context, userKey: String, totalScore: Int, totalCorrectAnswers: Int) {
        //tells the app to open DataStore and make changes to the stored data preferences - name for the storage area that holds different values
        context.dataStore.edit { preferences ->
            //saves the users totalScore - uses the unique label from the getTotalSCoreKey(userKey) identify the score for that specific user and sets it to totlScore
            preferences[getTotalScoreKey(userKey)] = totalScore
            //similar above - getTotalCorrectAnswersKey(userKey) - to store the correct answer count for that user
            preferences[getTotalCorrectAnswersKey(userKey)] = totalCorrectAnswers
        }
    }

    /*
    * todo[]
    *  fun getQuizStats(context: Context, userKey: String): Flow<Pair<Int, Int>>:
    *  --context: Context: Allows the function to access the app’s DataStore
    *  --userKey: String: The unique identifier (email) for the user whose data to retrieve
    *  --: Flow<Pair<Int, Int>>: This means the function will return a Flow (a stream of data updates) containing
    *     a Pair of two integers: (totalScore, totalCorrectAnswers). So it returns both values together in one response
    */
    fun getQuizStats(context: Context, userKey: String): Flow<Pair<Int, Int>> {
        /*
        * todo[]
        *  --context.dataStore.data: Accesses the DataStore data where quiz stats are saved
        *  --map { preferences -> ... }: Processes each update from DataStore as it changes over time (useful if the data can be updated in real-time)
        *  --preferences: This is where all stored values are kept. We use it to look up specific keys (like score and correct answers for this user)
         */
        return context.dataStore.data.map { preferences ->
            //This retrieves the user’s score from DataStore using the unique key (getTotalScoreKey(userKey)).
            //?: 0: If there’s no score saved for this user, it returns 0 as a default.
            val score = preferences[getTotalScoreKey(userKey)] ?: 0
            //This retrieves the user’s correct answers from DataStore using their unique key (getTotalCorrectAnswersKey(userKey)).
            //?: 0: If there are no correct answers saved, it defaults to 0.
            val correctAnswers = preferences[getTotalCorrectAnswersKey(userKey)] ?: 0
            //Combines score and correctAnswers into a Pair (Pair<Int, Int>), returning both values together.
            score to correctAnswers
        }
    }

//    // This function retrieves the initial stats for a specific user in a synchronous way.
//    // It uses runBlocking to wait for the result from getQuizStats, which provides the initial stats.
//    fun getInitialStats(context: Context, userKey: String): Pair<Int, Int> {
//        return runBlocking {
//            // Calls getQuizStats and collects the first emitted value synchronously.
//            getQuizStats(context, userKey).first()
//        }
//    }

//    // Clears the saved quiz statistics for a specific user.
//    // This might be used, for instance, when a user logs out or wants to start fresh.
//    suspend fun clearStats(context: Context, userKey: String) {
//        context.dataStore.edit { preferences ->
//            // Removes the user's score and correct answer preferences from the DataStore.
//            preferences.remove(getTotalScoreKey(userKey))
//            preferences.remove(getTotalCorrectAnswersKey(userKey))
//        }
//    }
}
