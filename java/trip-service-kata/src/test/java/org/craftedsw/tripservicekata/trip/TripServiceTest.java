package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {
    private static final User GUEST = null;
    private User loggedInUser;

    @Mock private TripDAO tripDAO;
    @InjectMocks @Spy private TripService realTripService = new TripService();
    
    @Before
    public void initialise() {
        loggedInUser = new User();
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_the_user_is_not_logged_in() {
        User user = new User();

        realTripService.getTripsByUser(user, GUEST);
    }

    @Test
    public void return_no_trips_if_the_user_has_no_friends() {
        List<Trip> expectedTrips = new ArrayList<Trip>();
        User potentialFriend = new User();

        List<Trip> trips = realTripService.getTripsByUser(potentialFriend, loggedInUser);

        assertThat(trips, is(expectedTrips));
    }

    @Test
    public void return_no_trips_when_users_friend_has_no_trips() {
        List<Trip> expectedFriendsTrips = new ArrayList<Trip>();
        User friend = new User();
        friend.addFriend(loggedInUser);

        List<Trip> trips = realTripService.getTripsByUser(friend, loggedInUser);

        assertThat(trips, is(expectedFriendsTrips));
    }

    @Test
    public void return_trips_when_users_friend_has_trips() {
        List<Trip> expectedFriendsTrips = new ArrayList<Trip>();
        Trip trip = new Trip();
        expectedFriendsTrips.add(trip);
        User friend = new User();
        friend.addFriend(loggedInUser);
        friend.addTrip(trip);
        given(tripDAO.tripsBy(friend)).willReturn(expectedFriendsTrips);
        List<Trip> trips = realTripService.getTripsByUser(friend, loggedInUser);

        assertThat(trips, is(expectedFriendsTrips));
    }

    @Test
    public void return_trips_when_user_has_many_friends_with_multiple_trips() {
        List<Trip> expectedFriendsTrips = new ArrayList<Trip>();
        Trip firstTrip = new Trip();
        Trip secondTrip = new Trip();
        expectedFriendsTrips.add(firstTrip);
        expectedFriendsTrips.add(secondTrip);
        User friend = new User();
        friend.addFriend(loggedInUser);
        friend.addTrip(firstTrip);
        friend.addTrip(secondTrip);
        given(tripDAO.tripsBy(friend)).willReturn(expectedFriendsTrips);

        List<Trip> trips = realTripService.getTripsByUser(friend, loggedInUser);

        assertThat(trips, is(expectedFriendsTrips));
    }
}
