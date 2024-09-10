package com.openclassrooms.watchlist.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.repository.WatchlistRepository;

@RunWith(MockitoJUnitRunner.class)
public class WatchlistServiceTest {

    @InjectMocks
    private WatchlistService watchlistService;

    @Mock
    private WatchlistRepository watchlistRepositoryMock;

    @Mock
    private MovieRatingService movieRatingServiceMock;

    @Test
    public void testGetWatchlistItemsReturnsAllItemsFromRepository() {

        //Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", "7.7", "M" , "" );
        WatchlistItem item2 = new WatchlistItem("Star Treck", "8.8", "M" , "" );
        List<WatchlistItem> mockItems = Arrays.asList(item1, item2);

        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);

        //Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();

        //Assert
        assertEquals(2, result.size());
        assertEquals("Star Wars", result.get(0).getTitle());
        assertEquals("Star Treck", result.get(1).getTitle());
    }

    @Test
    public void testGetwatchlistItemsRatingFormOmdbServiceOverrideTheValueInItems() {

        //Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", "7.7", "M" , "" , 1);
        List<WatchlistItem> mockItems = List.of(item1);

        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);
        when(movieRatingServiceMock.getMovieRating(any(String.class))).thenReturn("10");

        //Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();

        //Assert
        assertEquals("10", result.get(0).getRating());
    }
}
