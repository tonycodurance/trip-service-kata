package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TripServiceTest {
    private static final User GUEST = null;

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_the_user_is_not_logged_in() {
        TripServiceTestable tripService = new TripServiceTestable(null, null);
        User user = new User();

        tripService.getTripsByUser(user, GUEST);
    }

    @Test
    public void return_no_trips_if_the_user_has_no_friends()
    {
        List<Trip> expectedTrips = new ArrayList<Trip>();
        User loggedInUser = new User();
        User potentialFriend = new User();
        TripServiceTestable tripService = new TripServiceTestable(loggedInUser, new ArrayList<User>());
        
        List<Trip> trips = tripService.getTripsByUser(potentialFriend, loggedInUser);
        
        assertThat(trips, is(expectedTrips));    
    }
    
    @Test
    public void return_no_trips_when_users_friend_has_no_trips()
    {
        List<Trip> expectedFriendsTrips = new ArrayList<Trip>();
        User loggedInUser = new User();
        User friend = new User();
        friend.addFriend(loggedInUser);
        
        TripServiceTestable tripService = new TripServiceTestable(loggedInUser, asList(friend));
        
        List<Trip> trips = tripService.getTripsByUser(friend, loggedInUser);
        
        assertThat(trips, is(expectedFriendsTrips));    
    }
    
    @Test
    public void return_trips_when_users_friend_has_trips()
    {
        List<Trip> expectedFriendsTrips = new ArrayList<Trip>();
        Trip trip = new Trip();
        expectedFriendsTrips.add(trip);
        User loggedInUser = new User();
        User friend = new User();
        friend.addFriend(loggedInUser);
        friend.addTrip(trip);
        TripServiceTestable tripService = new TripServiceTestable(loggedInUser, asList(friend));
        
        List<Trip> trips = tripService.getTripsByUser(friend, loggedInUser);
        
        assertThat(trips, is(expectedFriendsTrips));    
    }
    
    @Test
    public void return_trips_when_user_has_many_friends_with_multiple_trips()
    {
        List<Trip> expectedFriendsTrips = new ArrayList<Trip>();
        Trip firstTrip = new Trip();
        Trip secondTrip = new Trip();
        expectedFriendsTrips.add(firstTrip);
        expectedFriendsTrips.add(secondTrip);
        User loggedInUser = new User();
        User friend = new User();
        friend.addFriend(loggedInUser);
        friend.addTrip(firstTrip);
        friend.addTrip(secondTrip);
        TripServiceTestable tripService = new TripServiceTestable(loggedInUser, asList(friend));

        List<Trip> trips = tripService.getTripsByUser(friend, loggedInUser);
        
        assertThat(trips, is(expectedFriendsTrips));
    }
    
    private class TripServiceTestable extends TripService {
        private User user;
        private List<User> users;

        TripServiceTestable(User loggedInUser, List<User> users) {
            this.user = loggedInUser;
            this.users = users;
        }
        
        @Override
        public List<Trip> findTripsByUser(User user) {
            return user.trips();
        }
    }
}
