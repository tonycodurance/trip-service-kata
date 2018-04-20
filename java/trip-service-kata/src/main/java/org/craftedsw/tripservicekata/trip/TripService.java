package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

public class TripService {
	public List<Trip> getTripsByUser(User user, User loggedInUser) throws UserNotLoggedInException {
		if (loggedInUser == null) {
			throw new UserNotLoggedInException();
		} 

		if (user.isFriendWith(loggedInUser)) {
			return findTripsByUser(user);
		}
			
		return noTrips();
	}

	private ArrayList<Trip> noTrips() {
		return new ArrayList<Trip>();
	}

	protected List<Trip> findTripsByUser(User user) {
		return TripDAO.findTripsByUser(user);
	}
}
