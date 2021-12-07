package com.maasglobal.domain.di

import com.maasglobal.domain.repository.RemoteRepo
import com.maasglobal.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRemoteRepo(impl: Repository): RemoteRepo


}