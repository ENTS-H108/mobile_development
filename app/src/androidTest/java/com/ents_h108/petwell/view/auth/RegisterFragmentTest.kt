package com.ents_h108.petwell.view.auth

import org.junit.Assert.*

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.ents_h108.petwell.R
import com.ents_h108.petwell.di.baseUrl
import com.ents_h108.petwell.utils.JsonConverter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class RegisterFragmentTest {
    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun registerSuccess() {
        // Launch the fragment
        val bundle = Bundle()
        launchFragmentInContainer<RegisterFragment>(bundle, R.style.Theme_PetWell)


        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("register_success_response.json"))
        mockWebServer.enqueue(mockResponse)


        onView(withId(R.id.et_username_register)).perform(typeText("username"))
        onView(withId(R.id.et_email_register)).perform(typeText("test@example.com"))
        onView(withId(R.id.et_password_register)).perform(typeText("password123"))
        onView(withId(R.id.et_cpassword_register)).perform(typeText("password123"))
        onView(withId(R.id.reg_btn)).perform(click())
        Thread.sleep(2000)



        onView(withId(R.id.loading_register)).check(matches(
            ViewMatchers.withEffectiveVisibility(
                ViewMatchers.Visibility.GONE
            )
        ))



    }
}