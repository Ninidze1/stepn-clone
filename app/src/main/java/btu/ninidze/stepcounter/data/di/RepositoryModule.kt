package btu.ninidze.stepcounter.data.di

import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IDataStoreRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IStepRepository
import btu.ninidze.stepcounter.data.repository.impl.CollectionRepository
import btu.ninidze.stepcounter.data.repository.impl.DataStoreRepository
import btu.ninidze.stepcounter.data.repository.impl.FirebaseRepository
import btu.ninidze.stepcounter.data.repository.impl.StepRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindStepRepository(stepRepository: StepRepository): IStepRepository

    @Binds
    abstract fun bindCollectionRepository(collectionRepository: CollectionRepository): ICollectionRepository

    @Binds
    abstract fun bindFirebaseRepository(firebaseRepository: FirebaseRepository): IFirebaseRepository

    @Binds
    abstract fun bindDataStoreRepository(dataStoreRepository: DataStoreRepository): IDataStoreRepository
}