package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.expection.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import(UserService.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ShareItUsersTests {
    @Autowired
    private final UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetUserById_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test Name");
        User createdUser = userService.createUser(user);

        User foundUser = userService.getUserById(createdUser.getId());

        assertThat(foundUser)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", createdUser.getId())
                .hasFieldOrPropertyWithValue("email", "test@example.com")
                .hasFieldOrPropertyWithValue("name", "Test Name");
    }

    @Test
    public void testGetUserById_UserNotFound_ThrowsNotFoundException() {
        assertThatThrownBy(() -> userService.getUserById(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Такого юзера нет в списке!");
    }

    @Test
    public void testGetAllUsers_Success() {
        createTestUser("user1@example.com", "User One");
        createTestUser("user2@example.com", "User Two");

        Collection<User> allUsers = userService.getAllUsers();
        assertThat(allUsers)
                .isNotEmpty()
                .hasSize(2)
                .extracting("email")
                .containsExactlyInAnyOrder("user1@example.com", "user2@example.com");
    }

    @Test
    public void testCreateUser_Success() {
        User user = new User();
        user.setEmail("newuser@example.com");
        user.setName("New User");

        User createdUser = userService.createUser(user);

        assertThat(createdUser)
                .isNotNull()
                .hasFieldOrPropertyWithValue("email", "newuser@example.com")
                .hasFieldOrPropertyWithValue("name", "New User");

        assertThat(createdUser.getId()).isNotNull();
    }

    @Test
    public void testUpdateUser_Success() {
        User user = createTestUser("update@example.com", "Update User");

        User user2 = new User();
        user2.setId(1L);
        user2.setEmail("test@example.com");
        user2.setName("Test Name");

        User updatedUser = userService.updateUser(user2);

        assertThat(updatedUser)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", user.getId())
                .hasFieldOrPropertyWithValue("name", "Test Name")
                .hasFieldOrPropertyWithValue("email", "test@example.com");
    }

    private User createTestUser(String email, String name) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        return userService.createUser(user);
    }
}
