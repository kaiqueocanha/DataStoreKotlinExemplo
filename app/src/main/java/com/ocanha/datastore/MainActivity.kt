package com.ocanha.datastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.ocanha.datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val _dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.btnSave.setOnClickListener {

            val key = stringPreferencesKey(_binding.edtKey.text.toString())

            lifecycleScope.launch {

                _dataStore.edit { settings ->
                    settings[key] = _binding.edtValue.text.toString()
                }

            }

        }

        _binding.btnRead.setOnClickListener {

            val key = stringPreferencesKey(_binding.edtKey.text.toString())

//            val exampleCounterFlow: Flow<String> = _dataStore.data
//                .map { preferences ->
//                    preferences[key] ?: ""
//                }

            lifecycleScope.launch {

//                exampleCounterFlow.collect {
//
//                    _binding.tvValue.text = it
//
//                }

                _binding.tvValue.text = _dataStore.data.first()[key]

            }

        }

        _binding.btnClean.setOnClickListener {

            val key = stringPreferencesKey(_binding.edtKey.text.toString())

            lifecycleScope.launch {

                _dataStore.edit { settings ->

                    settings.remove(key)

                }

            }

        }

        _binding.btnCleanAll.setOnClickListener {

            lifecycleScope.launch {

                _dataStore.edit { settings ->

                    settings.clear()

                }

            }

        }


    }
}