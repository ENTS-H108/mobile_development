package com.ents_h108.petwell.view.auth

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.ents_h108.petwell.R
import com.ents_h108.petwell.di.baseUrl
import com.ents_h108.petwell.utils.JsonConverter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginFragmentTest {
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
    fun loginSuccess() {
        // Launch the fragment
        val bundle = Bundle()
        launchFragmentInContainer<LoginFragment>(bundle, R.style.Theme_PetWell)


        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("login_success_response.json"))
        mockWebServer.enqueue(mockResponse)


        onView(withId(R.id.et_email_login)).perform(typeText("test@example.com"))
        onView(withId(R.id.et_password_login)).perform(typeText("password123"))
        onView(withId(R.id.log_btn_login)).perform(click())

        Thread.sleep(2000)



        onView(withId(R.id.loading_login)).check(matches(withEffectiveVisibility(GONE)))
    }


}