package study.data_jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.data_jpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;

//    @Test
//    public void save() {
//
//        Item item = new Item();
//        itemRepository.save(item);
//    }

    @Test
    public void save() { // save()전에 id값을 임의로 주입한 경우

        Item item = new Item("A");
        itemRepository.save(item);
    }

}