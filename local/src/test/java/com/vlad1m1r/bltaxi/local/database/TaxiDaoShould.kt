package com.vlad1m1r.bltaxi.local.database

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.vlad1m1r.bltaxi.taxi.domain.Language
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
class TaxiDaoShould {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val taxi = Taxi(
        taxiId = 10,
        name = "name",
        phoneNumber = "phone_number",
        tariff1Start = "start_price",
        tariff1PricePerKm = "price_per_km",
        tariff1HourOfWaiting = "hour_of_waiting",
        tariff2Start = "start_price_2",
        tariff2PricePerKm = "price_per_km_2",
        tariff2HourOfWaiting = "hour_of_waiting_2",
        additionalInfo = "additional_info",
        viberNumber = "viber_number",
        language = Language.HR
    )

    private lateinit var database: TaxiDatabase
    private lateinit var taxiDao: TaxiDao
    private val taxis = listOf(
        taxi.copy(taxiId = 1, language = Language.SR),
        taxi.copy(taxiId = 2, language = Language.EN),
        taxi.copy(taxiId = 3, language = Language.BS)
    )

    @Before
    fun setUp() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room
            .inMemoryDatabaseBuilder(context, TaxiDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taxiDao = database.taxiDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun returnEmptyList_whenNothingInDatabase() {
        val taxisFromDao = taxiDao.getAll(Language.EN)
        assertThat(taxisFromDao).isEmpty()
    }

    @Test
    fun deleteAll_forCorrectLanguage() {
        runBlocking {
            taxiDao.insertAll(taxis)
            taxiDao.deleteAll(Language.EN)

            val taxisFromDaoEn = taxiDao.getAll(Language.EN)
            val taxisFromDaoBs = taxiDao.getAll(Language.BS)
            val taxisFromDaoSr = taxiDao.getAll(Language.SR)

            assertThat(taxisFromDaoEn).isEmpty()
            assertThat(taxisFromDaoBs).containsExactly(taxis[2])
            assertThat(taxisFromDaoSr).containsExactly(taxis[0])
        }
    }

    @Test
    fun returnTaxis_forCorrectLanguage() {
        runBlocking {
            taxiDao.insertAll(taxis)
            val taxisFromDao = taxiDao.getAll(Language.EN)

            assertThat(taxisFromDao).containsExactly(taxis[1])
        }
    }

    @Test
    fun replaceTaxis_forCorrectLanguage() {
        runBlocking {
            taxiDao.insertAll(taxis)
            val newTaxis = listOf(
                taxi.copy(taxiId = 1, language = Language.SR),
                taxi.copy(taxiId = 2, language = Language.SR)
            )

            taxiDao.replaceAll(newTaxis, Language.SR)

            val taxisFromDaoSr = taxiDao.getAll(Language.SR)
            val taxisFromDaoEn = taxiDao.getAll(Language.EN)
            val taxisFromDaoBs = taxiDao.getAll(Language.BS)

            assertThat(taxisFromDaoSr).containsExactlyElementsIn(newTaxis)
            assertThat(taxisFromDaoEn).containsExactly(taxis[1])
            assertThat(taxisFromDaoBs).containsExactly(taxis[2])
        }
    }
}
