package ru.practicum.shareit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({ItemService.class, UserService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ShareItItemsTests {

    @Autowired
    private final ItemService itemService;
    @Autowired
    private final UserService userService;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    void userLoads() {
        User user = new User();
        user.setEmail("newuser@example.com");
        user.setName("New User");

        userService.createUser(user);

        User user2 = new User();
        user2.setEmail("newuser2@example.com");
        user2.setName("New User2");

        userService.createUser(user2);

        User user3 = new User();
        user3.setEmail("newuser3@example.com");
        user3.setName("New User3");

        userService.createUser(user3);
    }

    @Test
    public void testGetItemById_Success() {
        Item item = new Item();
        item.setName("Test Name");
        item.setDescription("Test desc");
        item.setAvailable(true);
        Item createdItem = itemService.createItem(item, 1L);

        Item foundItem = itemService.getItemById(createdItem.getId());

        assertThat(foundItem)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", createdItem.getId())
                .hasFieldOrPropertyWithValue("name", "Test Name")
                .hasFieldOrPropertyWithValue("description", "Test desc");
    }

    @Test
    public void getAllUserItems() {
        Item item = new Item();
        item.setName("Test Name");
        item.setDescription("Test desc");
        item.setAvailable(true);
        itemService.createItem(item, 1L);

        Item item2 = new Item();
        item2.setName("Test Name2");
        item2.setDescription("Test desc2");
        item2.setAvailable(true);
        itemService.createItem(item, 1L);

        Collection<Item> allItems = itemService.getAllUserItems(1L);

        assertThat(allItems)
                .hasSize(2);
    }

    @Test
    public void createItem() {
        Item item = new Item();
        item.setName("Test Name");
        item.setDescription("Test desc");
        item.setAvailable(true);
        Item createdItem = itemService.createItem(item, 1L);

        assertThat(createdItem)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Test Name")
                .hasFieldOrPropertyWithValue("description", "Test desc");
    }

    @Test
    public void updateItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Name");
        item.setDescription("Test desc");
        item.setAvailable(true);
        itemService.createItem(item, 1L);

        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("Test2 Name2");
        item2.setDescription("Test2 desc2");
        item2.setAvailable(true);
        Item updatedItem = itemService.updateItem(item2, 1L);

        assertThat(updatedItem)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Test2 Name2")
                .hasFieldOrPropertyWithValue("description", "Test2 desc2");
    }

    @Test
    public void searchItems() {
        Item item = new Item();
        item.setName("Test Name");
        item.setDescription("Test desc");
        item.setAvailable(true);
        itemService.createItem(item, 1L);

        Item item2 = new Item();
        item2.setName("Test Name2");
        item2.setDescription("Test desc2");
        item2.setAvailable(true);
        itemService.createItem(item2, 2L);

        Item item3 = new Item();
        item3.setName("cho");
        item3.setDescription("kogo");
        item3.setAvailable(true);
        itemService.createItem(item3, 3L);

        Collection<Item> result = itemService.searchItems("test");

        assertThat(result)
                .hasSize(2);
    }
}
