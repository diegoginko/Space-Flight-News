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

class LaunchLibraryApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var launchLibraryApiService: LaunchLibraryApiService
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

        launchLibraryApiService = retrofit.create(LaunchLibraryApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getLaunchById_Success_DevuelveDetallesDelLanzamiento() = runTest {
        // Given
        val launchId = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        val mockResponse = """
        {
            "id": "$launchId",
            "url": "https://ll.thespacedevs.com/2.2.0/launch/$launchId/",
            "launch_library_id": 123,
            "slug": "falcon-9-block-5-starlink-group-6-15",
            "name": "Falcon 9 Block 5 | Starlink Group 6-15",
            "status": {
                "id": 1,
                "name": "Go",
                "abbrev": "Go",
                "description": "Launch is go for liftoff"
            },
            "net": "2026-01-20T12:00:00Z",
            "window_start": "2026-01-20T11:30:00Z",
            "window_end": "2026-01-20T12:30:00Z",
            "inhold": false,
            "tbdtime": false,
            "tbddate": false,
            "probability": 90,
            "holdreason": null,
            "failreason": null,
            "hashtag": null,
            "launch_service_provider": {
                "id": 121,
                "url": "https://ll.thespacedevs.com/2.2.0/agencies/121/",
                "name": "SpaceX",
                "type": "Commercial"
            },
            "rocket": {
                "id": 1234,
                "configuration": {
                    "id": 123,
                    "url": "https://ll.thespacedevs.com/2.2.0/config/launcher/123/",
                    "name": "Falcon 9",
                    "family": "Falcon",
                    "full_name": "Falcon 9 Block 5",
                    "variant": "Block 5"
                }
            },
            "mission": {
                "id": 5678,
                "name": "Starlink Group 6-15",
                "description": "A batch of 22 Starlink satellites",
                "launch_designator": null,
                "type": "Communications",
                "orbit": {
                    "id": 8,
                    "name": "Low Earth Orbit",
                    "abbrev": "LEO"
                }
            },
            "pad": {
                "id": 80,
                "url": "https://ll.thespacedevs.com/2.2.0/pad/80/",
                "agency_id": 121,
                "name": "Launch Complex 39A",
                "info": null,
                "wiki_url": "https://en.wikipedia.org/wiki/Launch_Complex_39A",
                "map_url": "https://www.google.com/maps?q=28.608+N,80.604+W",
                "latitude": "28.608",
                "longitude": "-80.604",
                "location": {
                    "id": 27,
                    "url": "https://ll.thespacedevs.com/2.2.0/location/27/",
                    "name": "Kennedy Space Center, FL, USA",
                    "country_code": "USA",
                    "map_image": "https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/location_27_20200803142519.jpg",
                    "timezone_name": "America/New_York",
                    "total_launch_count": 230,
                    "total_landing_count": 60
                },
                "country_code": "USA",
                "map_image": "https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/pad_80_20200803143543.jpg",
                "total_launch_count": 185
            },
            "webcast_live": false,
            "image": "https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/falcon2520925_image_20240222094619.jpeg",
            "infographic": null,
            "program": []
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockResponse)
        )

        // When
        val result = launchLibraryApiService.getLaunchById(launchId)

        // Then
        assertNotNull(result)
        assertEquals(launchId, result.id)
        assertEquals("Falcon 9 Block 5 | Starlink Group 6-15", result.name)
        assertEquals("2026-01-20T12:00:00Z", result.netDate)
        assertEquals(90, result.probability)
        assertNotNull(result.status)
        assertEquals("Go", result.status?.name)
        assertNotNull(result.launchServiceProvider)
        assertEquals("SpaceX", result.launchServiceProvider?.name)
        assertNotNull(result.rocket)
        assertNotNull(result.rocket?.configuration)
        assertEquals("Falcon 9 Block 5", result.rocket?.configuration?.fullName)
        assertNotNull(result.mission)
        assertEquals("Starlink Group 6-15", result.mission?.name)
        assertNotNull(result.pad)
        assertEquals("Launch Complex 39A", result.pad?.name)
        assertNotNull(result.pad?.location)
        assertEquals("Kennedy Space Center, FL, USA", result.pad?.location?.name)

        // Verificar que la petición se hizo correctamente
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertTrue(request.path?.contains("/launch/$launchId/") == true)
    }

    @Test
    fun getLaunchById_Success_ConCamposOpcionalesNulos() = runTest {
        // Given
        val launchId = "test-launch-id"
        val mockResponse = """
        {
            "id": "$launchId",
            "url": null,
            "launch_library_id": null,
            "slug": null,
            "name": null,
            "status": null,
            "net": null,
            "window_start": null,
            "window_end": null,
            "inhold": null,
            "tbdtime": null,
            "tbddate": null,
            "probability": null,
            "holdreason": null,
            "failreason": null,
            "hashtag": null,
            "launch_service_provider": null,
            "rocket": null,
            "mission": null,
            "pad": null,
            "webcast_live": null,
            "image": null,
            "infographic": null,
            "program": null
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockResponse)
        )

        // When
        val result = launchLibraryApiService.getLaunchById(launchId)

        // Then
        assertNotNull(result)
        assertEquals(launchId, result.id)
        assertNull(result.name)
        assertNull(result.netDate)
        assertNull(result.status)
        assertNull(result.launchServiceProvider)
        assertNull(result.rocket)
        assertNull(result.mission)
        assertNull(result.pad)
    }

    @Test
    fun getLaunchById_Error404_LanzaExcepcion() = runTest {
        // Given
        val launchId = "invalid-launch-id"
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                .setBody("{\"detail\": \"Not found.\"}")
        )

        // When & Then
        try {
            launchLibraryApiService.getLaunchById(launchId)
            fail("Se esperaba una excepción")
        } catch (e: Exception) {
            assertNotNull(e)
        }
    }

    @Test
    fun getLaunchById_Error500_LanzaExcepcion() = runTest {
        // Given
        val launchId = "test-launch-id"
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .setBody("{\"detail\": \"Internal server error.\"}")
        )

        // When & Then
        try {
            launchLibraryApiService.getLaunchById(launchId)
            fail("Se esperaba una excepción")
        } catch (e: Exception) {
            assertNotNull(e)
        }
    }

    @Test
    fun getEventById_Success_DevuelveDetallesDelEvento() = runTest {
        // Given
        val eventId = 123
        val mockResponse = """
        {
            "id": $eventId,
            "url": "https://ll.thespacedevs.com/2.2.0/event/$eventId/",
            "slug": "spacex-starlink-mission-event",
            "name": "SpaceX Starlink Mission Event",
            "description": "A significant event related to SpaceX Starlink mission",
            "location": "International Space Station",
            "news_url": "https://example.com/news",
            "video_url": "https://example.com/video",
            "feature_image": "https://example.com/image.jpg",
            "date": "2026-01-25T14:00:00Z",
            "date_precision": "hour",
            "type": {
                "id": 1,
                "name": "Docking"
            },
            "launches": [
                {
                    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    "launch_library_id": 123,
                    "url": "https://ll.thespacedevs.com/2.2.0/launch/3fa85f64-5717-4562-b3fc-2c963f66afa6/",
                    "slug": "falcon-9-block-5-starlink",
                    "name": "Falcon 9 Block 5 | Starlink Group 6-15",
                    "status": {
                        "id": 1,
                        "name": "Go",
                        "abbrev": "Go",
                        "description": "Launch is go for liftoff"
                    },
                    "net": "2026-01-20T12:00:00Z",
                    "window_start": "2026-01-20T11:30:00Z",
                    "window_end": "2026-01-20T12:30:00Z",
                    "inhold": false,
                    "tbdtime": false,
                    "tbddate": false,
                    "probability": 90,
                    "hashtag": null,
                    "launch_service_provider": {
                        "id": 121,
                        "url": "https://ll.thespacedevs.com/2.2.0/agencies/121/",
                        "name": "SpaceX",
                        "type": "Commercial"
                    },
                    "rocket": null,
                    "mission": null,
                    "image": null,
                    "infographic": null
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
        val result = launchLibraryApiService.getEventById(eventId)

        // Then
        assertNotNull(result)
        assertEquals(eventId, result.id)
        assertEquals("SpaceX Starlink Mission Event", result.name)
        assertEquals("A significant event related to SpaceX Starlink mission", result.description)
        assertEquals("International Space Station", result.location)
        assertEquals("2026-01-25T14:00:00Z", result.date)
        assertNotNull(result.type)
        assertEquals("Docking", result.type?.name)
        assertNotNull(result.launches)
        assertEquals(1, result.launches?.size)
        assertEquals("3fa85f64-5717-4562-b3fc-2c963f66afa6", result.launches?.get(0)?.id)
        assertEquals("Falcon 9 Block 5 | Starlink Group 6-15", result.launches?.get(0)?.name)

        // Verificar que la petición se hizo correctamente
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertTrue(request.path?.contains("/event/$eventId/") == true)
    }

    @Test
    fun getEventById_Success_ConCamposOpcionalesNulos() = runTest {
        // Given
        val eventId = 456
        val mockResponse = """
        {
            "id": $eventId,
            "url": null,
            "slug": null,
            "name": null,
            "description": null,
            "location": null,
            "news_url": null,
            "video_url": null,
            "feature_image": null,
            "date": null,
            "date_precision": null,
            "type": null,
            "launches": null
        }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockResponse)
        )

        // When
        val result = launchLibraryApiService.getEventById(eventId)

        // Then
        assertNotNull(result)
        assertEquals(eventId, result.id)
        assertNull(result.name)
        assertNull(result.description)
        assertNull(result.location)
        assertNull(result.date)
        assertNull(result.type)
        assertNull(result.launches)
    }

    @Test
    fun getEventById_Error404_LanzaExcepcion() = runTest {
        // Given
        val eventId = 999
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                .setBody("{\"detail\": \"Not found.\"}")
        )

        // When & Then
        try {
            launchLibraryApiService.getEventById(eventId)
            fail("Se esperaba una excepción")
        } catch (e: Exception) {
            assertNotNull(e)
        }
    }

    @Test
    fun getEventById_Error500_LanzaExcepcion() = runTest {
        // Given
        val eventId = 123
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .setBody("{\"detail\": \"Internal server error.\"}")
        )

        // When & Then
        try {
            launchLibraryApiService.getEventById(eventId)
            fail("Se esperaba una excepción")
        } catch (e: Exception) {
            assertNotNull(e)
        }
    }

    @Test
    fun getLaunchById_VerificaParametrosCorrectos() = runTest {
        // Given
        val launchId = "test-launch-id-123"
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody("""{"id": "$launchId"}""")
        )

        // When
        launchLibraryApiService.getLaunchById(launchId)

        // Then
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertTrue(request.path?.contains("/launch/$launchId/") == true)
    }

    @Test
    fun getEventById_VerificaParametrosCorrectos() = runTest {
        // Given
        val eventId = 789
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody("""{"id": $eventId}""")
        )

        // When
        launchLibraryApiService.getEventById(eventId)

        // Then
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertTrue(request.path?.contains("/event/$eventId/") == true)
    }
}
