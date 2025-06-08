package com.example.claraterra.di

import android.content.Context
import androidx.room.Room
import com.example.claraterra.data.local.ClaraTerraDatabase
import com.example.claraterra.data.local.dao.GastoDao
import com.example.claraterra.data.local.dao.VentaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ClaraTerraDatabase {
        return Room.databaseBuilder(
            context,
            ClaraTerraDatabase::class.java,
            "clara_terra_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideVentaDao(db: ClaraTerraDatabase): VentaDao = db.ventaDao()

    @Provides
    fun provideGastoDao(db: ClaraTerraDatabase): GastoDao = db.gastoDao()
}