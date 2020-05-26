package com.grigor.nearme.di

import com.grigor.nearme.data.network.sun.api.NetWorkManager
import com.grigor.nearme.data.repository.SunDataRepository
import com.grigor.nearme.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { NetWorkManager.getApiService() }
    single { SunDataRepository(get()) }

    viewModel { HomeViewModel(get()) }
}