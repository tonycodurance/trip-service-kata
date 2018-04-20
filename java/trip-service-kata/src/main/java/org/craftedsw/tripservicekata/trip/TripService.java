package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {
	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		User loggedInUser = getLoggedUser();
		if (loggedInUser == null) {
			throw new UserNotLoggedInException();
		}
		
		List<Trip> tripList = new ArrayList<Trip>();
			boolean isFriend = user.isFriendWith(loggedInUser);
			if (isFriend) {
				tripList = findTripsByUser(user);
			}
			return tripList;
	}

	protected List<Trip> findTripsByUser(User user) {
		return TripDAO.findTripsByUser(user);
	}

	protected User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}
}
