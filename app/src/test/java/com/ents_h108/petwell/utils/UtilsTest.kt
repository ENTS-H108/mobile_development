package com.ents_h108.petwell.utils

import com.ents_h108.petwell.R
import com.ents_h108.petwell.data.model.Doctor
import com.ents_h108.petwell.utils.Utils.filterDoctorsWithinRadius
import com.ents_h108.petwell.utils.Utils.generateHashedNonce
import org.junit.Assert.*
import org.junit.Test

class UtilsTest {
    @Test
    fun `test Format Date With Different Time Zones`() {
        val currentDate = "2022-12-12T12:12:12Z"
        assertEquals("12 Dec 2022 | 19:12", Utils.formatDate(currentDate, "Asia/Jakarta"))
        assertEquals("12 Dec 2022 | 07:12", Utils.formatDate(currentDate, "America/New_York"))
        assertEquals("12 Dec 2022 | 23:12", Utils.formatDate(currentDate, "Australia/Sydney"))
    }

    @Test
    fun `test Generate Hashed Nonce Produces Valid SHA-256 Hash`() {
        val nonce = generateHashedNonce()
        assertEquals(64, nonce.length)
        assertTrue(nonce.all { it.isDigit() || it in 'a'..'f' })
    }

    @Test
    fun `test filter doctors to show only within 30km radius from user location`() {
        val doctors = listOf(
            Doctor("7", R.drawable.doctor_7, "Drh. Dwi Putra", "RS Hewan Makmur", "Veterinarian", "2 Tahun", "Rp 30.000", 40.6643, -73.9385),
            Doctor("2", R.drawable.doctor_2, "Drh. Ahmad Santoso", "RS Hewan Senang Hati", "Veterinarian", "32 Tahun", "Rp 250.000", 40.6782, -73.9442),
            Doctor("4", R.drawable.doctor_4, "Drh. Rina Hartati", "RS Hewan Ceria", "Veterinarian", "7 Tahun", "Rp 280.000", 51.5074, -0.1278),

            )

        val filteredDoctors = filterDoctorsWithinRadius(doctors, 40.7128, -74.0060)
        assertEquals(2, filteredDoctors.size)
        assertEquals(doctors[0], filteredDoctors[0])
        assertEquals(doctors[1], filteredDoctors[1])
    }
}