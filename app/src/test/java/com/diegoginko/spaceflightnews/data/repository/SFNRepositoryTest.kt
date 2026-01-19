package com.diegoginko.spaceflightnews.data.repository

import com.diegoginko.spaceflightnews.data.remote.LaunchLibraryApiService
import com.diegoginko.spaceflightnews.data.remote.SFNApiService
import com.diegoginko.spaceflightnews.data.remote.model.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

class SFNRepositoryTest {

    private lateinit var SFNApiService: SFNApiService
    private lateinit var launchLibraryApiService: LaunchLibraryApiService
    private lateinit var repository: SFNRepositoryImpl

    @Before
    fun setup() {
        SFNApiService = mockk()
        launchLibraryApiService = mockk()
        repository = SFNRepositoryImpl(SFNApiService, launchLibraryApiService)
    }

    @Test
    fun getArticles_Success_DevuelveResultConArticulos() = runTest {
        // Given
        val mockArticleDto = ArticleDto(
            id = 1,
            title = "Test Article",
            authors = emptyList(),
            url = "https://example.com",
            imageUrl = null,
            newsSite = "SpaceNews",
            summary = "Test summary",
            publishedAt = "2026-01-18T16:38:11.933Z",
            updatedAt = null,
            featured = false,
            launches = emptyList(),
            events = emptyList()
        )

        val mockResponse = ArticlesResponse(
            count = 1,
            next = null,
            previous = null,
            results = listOf(mockArticleDto)
        )

        coEvery { SFNApiService.getArticles(any(), any(), any(), any()) } returns mockResponse

        // When
        val result = repository.getArticles(limit = 10)

        // Then
        assertTrue(result.isSuccess)
        result.fold(
            onSuccess = { paginatedResult ->
                assertEquals(1, paginatedResult.count)
                assertEquals(1, paginatedResult.results.size)
                assertEquals("Test Article", paginatedResult.results[0].title)
            },
            onFailure = { fail("No debería fallar") }
        )

        coVerify { SFNApiService.getArticles(limit = 10, offset = null, search = null, newsSite = null) }
    }

    @Test
    fun getArticles_NetworkError_DevuelveResultFailure() = runTest {
        // Given
        val networkError = IOException("No internet connection")
        coEvery { SFNApiService.getArticles(any(), any(), any(), any()) } throws networkError

        // When
        val result = repository.getArticles()

        // Then
        assertTrue(result.isFailure)
        result.fold(
            onSuccess = { fail("No debería tener éxito") },
            onFailure = { exception ->
                assertTrue(exception is IOException)
                assertEquals("No internet connection", exception.message)
            }
        )
    }

    @Test
    fun getArticles_TimeoutError_DevuelveResultFailure() = runTest {
        // Given
        val timeoutError = SocketTimeoutException("Connection timeout")
        coEvery { SFNApiService.getArticles(any(), any(), any(), any()) } throws timeoutError

        // When
        val result = repository.getArticles()

        // Then
        assertTrue(result.isFailure)
        result.fold(
            onSuccess = { fail("No debería tener éxito") },
            onFailure = { exception ->
                assertTrue(exception is SocketTimeoutException)
            }
        )
    }

    @Test
    fun getArticleById_Success_DevuelveResultConArticulo() = runTest {
        // Given
        val articleId = 123
        val mockArticleDto = ArticleDto(
            id = articleId,
            title = "Test Article",
            authors = emptyList(),
            url = "https://example.com",
            imageUrl = null,
            newsSite = "SpaceNews",
            summary = "Test summary",
            publishedAt = "2026-01-18T16:38:11.933Z",
            updatedAt = null,
            featured = false,
            launches = emptyList(),
            events = emptyList()
        )

        coEvery { SFNApiService.getArticleById(articleId) } returns mockArticleDto

        // When
        val result = repository.getArticleById(articleId)

        // Then
        assertTrue(result.isSuccess)
        result.fold(
            onSuccess = { article ->
                assertEquals(articleId, article.id)
                assertEquals("Test Article", article.title)
                assertEquals("SpaceNews", article.newsSite)
            },
            onFailure = { fail("No debería fallar") }
        )

        coVerify { SFNApiService.getArticleById(articleId) }
    }

    @Test
    fun getArticleById_NetworkError_DevuelveResultFailure() = runTest {
        // Given
        val articleId = 123
        val networkError = IOException("No internet connection")
        coEvery { SFNApiService.getArticleById(articleId) } throws networkError

        // When
        val result = repository.getArticleById(articleId)

        // Then
        assertTrue(result.isFailure)
        result.fold(
            onSuccess = { fail("No debería tener éxito") },
            onFailure = { exception ->
                assertTrue(exception is IOException)
            }
        )
    }

    @Test
    fun getArticles_ConParametrosDeBusqueda_LlamaAlServicioCorrectamente() = runTest {
        // Given
        val mockResponse = ArticlesResponse(
            count = 0,
            next = null,
            previous = null,
            results = emptyList()
        )

        coEvery { SFNApiService.getArticles(any(), any(), any(), any()) } returns mockResponse

        // When
        repository.getArticles(
            limit = 20,
            offset = 10,
            search = "SpaceX",
            newsSite = "SpaceNews"
        )

        // Then
        coVerify {
            SFNApiService.getArticles(
                limit = 20,
                offset = 10,
                search = "SpaceX",
                newsSite = "SpaceNews"
            )
        }
    }

    @Test
    fun getLaunchById_Success_DevuelveResultConLaunchDetail() = runTest {
        // Given
        val launchId = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        val mockLaunchDetailDto = LaunchDetailDto(
            id = launchId,
            url = "https://ll.thespacedevs.com/2.2.0/launch/$launchId/",
            launchLibraryId = 123,
            slug = "falcon-9-block-5-starlink",
            name = "Falcon 9 Block 5 | Starlink Group 6-15",
            status = LaunchStatusDto(
                id = 1,
                name = "Go",
                abbrev = "Go",
                description = "Launch is go for liftoff"
            ),
            netDate = "2026-01-20T12:00:00Z",
            windowStart = "2026-01-20T11:30:00Z",
            windowEnd = "2026-01-20T12:30:00Z",
            inHold = false,
            tbdTime = false,
            tbdDate = false,
            probability = 90,
            holdreason = null,
            failreason = null,
            hashtag = null,
            launchServiceProvider = LaunchServiceProviderDto(
                id = 121,
                url = "https://ll.thespacedevs.com/2.2.0/agencies/121/",
                name = "SpaceX",
                type = "Commercial"
            ),
            rocket = RocketDto(
                id = 1234,
                configuration = RocketConfigurationDto(
                    id = 123,
                    url = "https://ll.thespacedevs.com/2.2.0/config/launcher/123/",
                    name = "Falcon 9",
                    family = "Falcon",
                    fullName = "Falcon 9 Block 5",
                    variant = "Block 5"
                )
            ),
            mission = MissionDto(
                id = 5678,
                name = "Starlink Group 6-15",
                description = "A batch of 22 Starlink satellites",
                launchDesignator = null,
                type = "Communications",
                orbit = OrbitDto(
                    id = 8,
                    name = "Low Earth Orbit",
                    abbrev = "LEO"
                )
            ),
            pad = PadDto(
                id = 80,
                url = "https://ll.thespacedevs.com/2.2.0/pad/80/",
                agencyId = 121,
                name = "Launch Complex 39A",
                info = null,
                wiki_url = "https://en.wikipedia.org/wiki/Launch_Complex_39A",
                mapUrl = "https://www.google.com/maps?q=28.608+N,80.604+W",
                latitude = "28.608",
                longitude = "-80.604",
                location = LocationDto(
                    id = 27,
                    url = "https://ll.thespacedevs.com/2.2.0/location/27/",
                    name = "Kennedy Space Center, FL, USA",
                    countryCode = "USA",
                    mapImage = "https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/location_27_20200803142519.jpg",
                    timezoneName = "America/New_York",
                    totalLaunchCount = 230,
                    totalLandingCount = 60
                ),
                countryCode = "USA",
                mapImage = "https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/pad_80_20200803143543.jpg",
                totalLaunchCount = 185
            ),
            webcastLive = false,
            image = "https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/falcon2520925_image_20240222094619.jpeg",
            infographic = null,
            program = emptyList()
        )

        coEvery { launchLibraryApiService.getLaunchById(launchId) } returns mockLaunchDetailDto

        // When
        val result = repository.getLaunchById(launchId)

        // Then
        assertTrue(result.isSuccess)
        result.fold(
            onSuccess = { launchDetail ->
                assertEquals(launchId, launchDetail.id)
                assertEquals("Falcon 9 Block 5 | Starlink Group 6-15", launchDetail.name)
                assertEquals("2026-01-20T12:00:00Z", launchDetail.netDate)
                assertEquals(90, launchDetail.probability)
                assertNotNull(launchDetail.status)
                assertEquals("Go", launchDetail.status?.name)
                assertNotNull(launchDetail.launchServiceProvider)
                assertEquals("SpaceX", launchDetail.launchServiceProvider?.name)
                assertNotNull(launchDetail.rocket)
                assertNotNull(launchDetail.rocket?.configuration)
                assertEquals("Falcon 9 Block 5", launchDetail.rocket?.configuration?.fullName)
                assertNotNull(launchDetail.mission)
                assertEquals("Starlink Group 6-15", launchDetail.mission?.name)
                assertNotNull(launchDetail.pad)
                assertEquals("Launch Complex 39A", launchDetail.pad?.name)
                assertNotNull(launchDetail.pad?.location)
                assertEquals("Kennedy Space Center, FL, USA", launchDetail.pad?.location?.name)
            },
            onFailure = { fail("No debería fallar") }
        )

        coVerify { launchLibraryApiService.getLaunchById(launchId) }
    }

    @Test
    fun getLaunchById_NetworkError_DevuelveResultFailure() = runTest {
        // Given
        val launchId = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        val networkError = IOException("No internet connection")
        coEvery { launchLibraryApiService.getLaunchById(launchId) } throws networkError

        // When
        val result = repository.getLaunchById(launchId)

        // Then
        assertTrue(result.isFailure)
        result.fold(
            onSuccess = { fail("No debería tener éxito") },
            onFailure = { exception ->
                assertTrue(exception is IOException)
                assertEquals("No internet connection", exception.message)
            }
        )
    }

    @Test
    fun getLaunchById_TimeoutError_DevuelveResultFailure() = runTest {
        // Given
        val launchId = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        val timeoutError = SocketTimeoutException("Connection timeout")
        coEvery { launchLibraryApiService.getLaunchById(launchId) } throws timeoutError

        // When
        val result = repository.getLaunchById(launchId)

        // Then
        assertTrue(result.isFailure)
        result.fold(
            onSuccess = { fail("No debería tener éxito") },
            onFailure = { exception ->
                assertTrue(exception is SocketTimeoutException)
            }
        )
    }

    @Test
    fun getLaunchById_ConCamposOpcionalesNulos_DevuelveResultSuccess() = runTest {
        // Given
        val launchId = "test-launch-id"
        val mockLaunchDetailDto = LaunchDetailDto(
            id = launchId,
            url = null,
            launchLibraryId = null,
            slug = null,
            name = null,
            status = null,
            netDate = null,
            windowStart = null,
            windowEnd = null,
            inHold = null,
            tbdTime = null,
            tbdDate = null,
            probability = null,
            holdreason = null,
            failreason = null,
            hashtag = null,
            launchServiceProvider = null,
            rocket = null,
            mission = null,
            pad = null,
            webcastLive = null,
            image = null,
            infographic = null,
            program = null
        )

        coEvery { launchLibraryApiService.getLaunchById(launchId) } returns mockLaunchDetailDto

        // When
        val result = repository.getLaunchById(launchId)

        // Then
        assertTrue(result.isSuccess)
        result.fold(
            onSuccess = { launchDetail ->
                assertEquals(launchId, launchDetail.id)
                assertNull(launchDetail.name)
                assertNull(launchDetail.netDate)
                assertNull(launchDetail.status)
                assertNull(launchDetail.launchServiceProvider)
                assertNull(launchDetail.rocket)
                assertNull(launchDetail.mission)
                assertNull(launchDetail.pad)
            },
            onFailure = { fail("No debería fallar") }
        )
    }

    @Test
    fun getEventById_Success_DevuelveResultConEventDetail() = runTest {
        // Given
        val eventId = 123
        val mockEventDetailDto = EventDetailDto(
            id = eventId,
            url = "https://ll.thespacedevs.com/2.2.0/event/$eventId/",
            slug = "spacex-starlink-mission-event",
            name = "SpaceX Starlink Mission Event",
            description = "A significant event related to SpaceX Starlink mission",
            location = "International Space Station",
            newsUrl = "https://example.com/news",
            videoUrl = "https://example.com/video",
            featureImage = "https://example.com/image.jpg",
            date = "2026-01-25T14:00:00Z",
            datePrecision = "hour",
            type = EventTypeDto(
                id = 1,
                name = "Docking"
            ),
            launches = listOf(
                LaunchReferenceDto(
                    id = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    launchLibraryId = 123,
                    url = "https://ll.thespacedevs.com/2.2.0/launch/3fa85f64-5717-4562-b3fc-2c963f66afa6/",
                    slug = "falcon-9-block-5-starlink",
                    name = "Falcon 9 Block 5 | Starlink Group 6-15",
                    status = LaunchStatusDto(
                        id = 1,
                        name = "Go",
                        abbrev = "Go",
                        description = "Launch is go for liftoff"
                    ),
                    netDate = "2026-01-20T12:00:00Z",
                    windowStart = "2026-01-20T11:30:00Z",
                    windowEnd = "2026-01-20T12:30:00Z",
                    inHold = false,
                    tbdTime = false,
                    tbdDate = false,
                    probability = 90,
                    hashtag = null,
                    launchServiceProvider = LaunchServiceProviderDto(
                        id = 121,
                        url = "https://ll.thespacedevs.com/2.2.0/agencies/121/",
                        name = "SpaceX",
                        type = "Commercial"
                    ),
                    rocket = null,
                    mission = null,
                    image = null,
                    infographic = null
                )
            )
        )

        coEvery { launchLibraryApiService.getEventById(eventId) } returns mockEventDetailDto

        // When
        val result = repository.getEventById(eventId)

        // Then
        assertTrue(result.isSuccess)
        result.fold(
            onSuccess = { eventDetail ->
                assertEquals(eventId, eventDetail.id)
                assertEquals("SpaceX Starlink Mission Event", eventDetail.name)
                assertEquals("A significant event related to SpaceX Starlink mission", eventDetail.description)
                assertEquals("International Space Station", eventDetail.location)
                assertEquals("2026-01-25T14:00:00Z", eventDetail.date)
                assertNotNull(eventDetail.type)
                assertEquals("Docking", eventDetail.type?.name)
                assertNotNull(eventDetail.launches)
                assertEquals(1, eventDetail.launches?.size)
                assertEquals("3fa85f64-5717-4562-b3fc-2c963f66afa6", eventDetail.launches?.get(0)?.id)
                assertEquals("Falcon 9 Block 5 | Starlink Group 6-15", eventDetail.launches?.get(0)?.name)
            },
            onFailure = { fail("No debería fallar") }
        )

        coVerify { launchLibraryApiService.getEventById(eventId) }
    }

    @Test
    fun getEventById_NetworkError_DevuelveResultFailure() = runTest {
        // Given
        val eventId = 123
        val networkError = IOException("No internet connection")
        coEvery { launchLibraryApiService.getEventById(eventId) } throws networkError

        // When
        val result = repository.getEventById(eventId)

        // Then
        assertTrue(result.isFailure)
        result.fold(
            onSuccess = { fail("No debería tener éxito") },
            onFailure = { exception ->
                assertTrue(exception is IOException)
                assertEquals("No internet connection", exception.message)
            }
        )
    }

    @Test
    fun getEventById_TimeoutError_DevuelveResultFailure() = runTest {
        // Given
        val eventId = 456
        val timeoutError = SocketTimeoutException("Connection timeout")
        coEvery { launchLibraryApiService.getEventById(eventId) } throws timeoutError

        // When
        val result = repository.getEventById(eventId)

        // Then
        assertTrue(result.isFailure)
        result.fold(
            onSuccess = { fail("No debería tener éxito") },
            onFailure = { exception ->
                assertTrue(exception is SocketTimeoutException)
            }
        )
    }

    @Test
    fun getEventById_ConCamposOpcionalesNulos_DevuelveResultSuccess() = runTest {
        // Given
        val eventId = 789
        val mockEventDetailDto = EventDetailDto(
            id = eventId,
            url = null,
            slug = null,
            name = null,
            description = null,
            location = null,
            newsUrl = null,
            videoUrl = null,
            featureImage = null,
            date = null,
            datePrecision = null,
            type = null,
            launches = null
        )

        coEvery { launchLibraryApiService.getEventById(eventId) } returns mockEventDetailDto

        // When
        val result = repository.getEventById(eventId)

        // Then
        assertTrue(result.isSuccess)
        result.fold(
            onSuccess = { eventDetail ->
                assertEquals(eventId, eventDetail.id)
                assertNull(eventDetail.name)
                assertNull(eventDetail.description)
                assertNull(eventDetail.location)
                assertNull(eventDetail.date)
                assertNull(eventDetail.type)
                assertNull(eventDetail.launches)
            },
            onFailure = { fail("No debería fallar") }
        )
    }
}
