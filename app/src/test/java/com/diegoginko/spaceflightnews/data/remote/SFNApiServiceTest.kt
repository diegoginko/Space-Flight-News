package com.diegoginko.spaceflightnews.data.remote

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.net.HttpURLConnection

class SFNApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var SFNApiService: SFNApiService
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        SFNApiService = retrofit.create(SFNApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getArticles_Success_DevuelveListaDeArticulos() = runTest {
        // Given
        val mockResponse = """
        {
            "count": 1,
            "next": null,
            "previous": null,
            "results": [
                {
                    "id": 1,
                    "title": "Test Article",
                    "authors": [],
                    "url": "https://example.com",
                    "image_url": "https://example.com/image.jpg",
                    "news_site": "SpaceNews",
                    "summary": "Test summary",
                    "published_at": "2026-01-18T16:38:11.933Z",
                    "updated_at": null,
                    "featured": false,
                    "launches": [],
                    "events": []
                }
            ]
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockResponse)
        )

        // When
        val result = SFNApiService.getArticles(limit = 10)

        // Then
        assertNotNull(result)
        assertEquals(1, result.count)
        assertEquals(1, result.results.size)
        assertEquals("Test Article", result.results[0].title)
        assertEquals("SpaceNews", result.results[0].newsSite)
    }

    @Test
    fun getArticles_Success_ConParametrosDeBusqueda() = runTest {
        // Given
        val mockResponse = """
        {
            "count": 0,
            "next": null,
            "previous": null,
            "results": []
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockResponse)
        )

        // When
        val result = SFNApiService.getArticles(
            limit = 5,
            offset = 10,
            search = "SpaceX",
            newsSite = "SpaceNews"
        )

        // Then
        assertNotNull(result)
        val request = mockWebServer.takeRequest()
        assertTrue(request.path?.contains("limit=5") == true)
        assertTrue(request.path?.contains("offset=10") == true)
        assertTrue(request.path?.contains("search=SpaceX") == true)
        assertTrue(request.path?.contains("news_site=SpaceNews") == true)
    }

    @Test
    fun getArticleById_Success_DevuelveArticulo() = runTest {
        // Given
        val articleId = 123
        val mockResponse = """
        {
            "id": $articleId,
            "title": "Test Article",
            "authors": [],
            "url": "https://example.com",
            "image_url": null,
            "news_site": "SpaceNews",
            "summary": "Test summary",
            "published_at": "2026-01-18T16:38:11.933Z",
            "updated_at": null,
            "featured": false,
            "launches": [],
            "events": []
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockResponse)
        )

        // When
        val result = SFNApiService.getArticleById(articleId)

        // Then
        assertNotNull(result)
        assertEquals(articleId, result.id)
        assertEquals("Test Article", result.title)
        assertEquals("SpaceNews", result.newsSite)
    }

    @Test
    fun getArticleById_Error404_LanzaExcepcion() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                .setBody("Not Found")
        )

        // When / Then
        try {
            SFNApiService.getArticleById(999)
            fail("Debería haber lanzado una excepción")
        } catch (e: Exception) {
            assertNotNull(e)
        }
    }

    @Test
    fun getArticles_Error500_LanzaExcepcion() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .setBody("Internal Server Error")
        )

        // When / Then
        try {
            SFNApiService.getArticles()
            fail("Debería haber lanzado una excepción")
        } catch (e: Exception) {
            assertNotNull(e)
        }
    }

    @Test
    fun getArticles_ConAutoresYLanzamientos() = runTest {
        // Given
        val mockResponse = """
        {
            "count": 1,
            "next": null,
            "previous": null,
            "results": [
                {
                    "id": 1,
                    "title": "Test Article",
                    "authors": [
                        {
                            "name": "John Doe",
                            "socials": {
                                "x": "@johndoe",
                                "youtube": "johndoe",
                                "instagram": null,
                                "linkedin": null,
                                "mastodon": null,
                                "bluesky": null
                            }
                        }
                    ],
                    "url": "https://example.com",
                    "image_url": null,
                    "news_site": "SpaceNews",
                    "summary": "Test summary",
                    "published_at": "2026-01-18T16:38:11.933Z",
                    "updated_at": null,
                    "featured": false,
                    "launches": [
                        {
                            "launch_id": "abc-123",
                            "provider": "NASA"
                        }
                    ],
                    "events": [
                        {
                            "event_id": 456,
                            "provider": "SpaceX"
                        }
                    ]
                }
            ]
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockResponse)
        )

        // When
        val result = SFNApiService.getArticles()

        // Then
        assertNotNull(result)
        assertEquals(1, result.results.size)
        val article = result.results[0]
        assertEquals(1, article.authors.size)
        assertEquals("John Doe", article.authors[0].name)
        assertEquals("@johndoe", article.authors[0].socials?.x)
        assertEquals(1, article.launches.size)
        assertEquals("abc-123", article.launches[0].launchId)
        assertEquals(1, article.events.size)
        assertEquals(456, article.events[0].eventId)
    }
}
