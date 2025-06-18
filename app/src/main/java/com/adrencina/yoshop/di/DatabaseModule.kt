package com.adrencina.yoshop.di

import android.content.Context
import androidx.room.Room
import com.adrencina.yoshop.data.local.ClaraTerraDatabase
import com.adrencina.yoshop.data.local.dao.GastoDao
import com.adrencina.yoshop.data.local.dao.ProductoDao
import com.adrencina.yoshop.data.local.dao.VentaDao
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

    @Provides
    fun provideProductoDao(db: ClaraTerraDatabase): ProductoDao = db.productoDao()
}