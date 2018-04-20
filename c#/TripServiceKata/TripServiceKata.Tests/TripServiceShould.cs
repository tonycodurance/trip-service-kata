using System.Collections.Generic;
using NUnit.Framework;
using TripServiceKata.Trip;

namespace TripServiceKata.Tests
{
    [TestFixture]
    public class TripServiceShould
    {
        [Test]
        public void GetTripByUser()
        {
            var user = new User.User();
            var tripService = new TripServiceTestable(user);
            var expectedTrips = new List<Trip.Trip>();
            
            var trips = tripService.GetTripsByUser(user);
            
            Assert.That(trips, Is.EqualTo(expectedTrips));
        }
    }

    public class TripServiceTestable : TripService
    {
        private readonly User.User _user;

        public TripServiceTestable(User.User user)
        {
            _user = user;
        }
        
        public override User.User GetLoggedUser()
        {
            return _user;
        }
    }
}
