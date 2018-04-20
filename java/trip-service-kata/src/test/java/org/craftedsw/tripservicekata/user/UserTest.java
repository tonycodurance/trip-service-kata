package org.craftedsw.tripservicekata.user;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserTest {
    private static final User PAUL = new User();
    private static final User BOB = new User();
    
    @Test
    public void should_inform_when_users_are_not_friends() {
        User user = new User();
        user.addFriend(BOB);

        assertThat(user.isFriendWith(PAUL), is(false));
    }
    
    @Test
    public void should_inform_when_users_are_friends() {
        User user = new User();
        user.addFriend(BOB);
        
        assertThat(user.isFriendWith(BOB), is(true));
    }
}
