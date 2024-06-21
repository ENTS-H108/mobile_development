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
            Doctor("7", "Drh. Dwi Putra", "Fish Doctor", "RS Hewan Makmur", "Veterinarian", "2 Tahun", "Rp 30.000", 40.6643, -73.9385, "Rp 250.000", "RS PERMATA"),
            Doctor("8", "Drh. Tri Putra", "Lion Doctor", "RS Hewan Gendut", "Veterinarian", "2 Tahun", "Rp 30.000", 40.7128, -74.0060, "Rp 2222222", "RS PERMATA BUNDA"),
            Doctor("9", "Drh. Eka Putra", "Ant Doctor", "RS Hewan Kurus", "Veterinarian", "2 Tahun", "Rp 30.000", 40.7128, -44.6666, "Rp 250.111", "RS PERMATA PAPA")
            )

        val filteredDoctors = filterDoctorsWithinRadius(doctors, 40.7128, -74.0060)
        assertEquals(2, filteredDoctors.size)
        assertEquals(doctors[0], filteredDoctors[0])
        assertEquals(doctors[1], filteredDoctors[1])
    }
}