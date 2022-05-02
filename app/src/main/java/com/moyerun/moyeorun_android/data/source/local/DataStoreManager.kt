package com.moyerun.moyeorun_android.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.moyerun.moyeorun_android.common.extension.readValue
import com.moyerun.moyeorun_android.common.extension.storeValue
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "dataStore")

        private val exampleStringKey = stringPreferencesKey(name = "exampleStringKey")
    }
    private val dataStore = context.dataStore

    suspend fun setExampleData(text: String) {
        dataStore.storeValue(key = exampleStringKey, value = text)
    }

    suspend fun getExampleData(): String? {
        return dataStore.readValue(key = exampleStringKey)
    }
}