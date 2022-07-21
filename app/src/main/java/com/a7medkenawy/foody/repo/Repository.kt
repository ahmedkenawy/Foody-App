package com.a7medkenawy.foody.repo

import com.a7medkenawy.foody.data.datasource.local.LocalDataSource
import com.a7medkenawy.foody.data.datasource.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local=localDataSource
}