package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User("User 1", "email1@mail.ru", "123456", Role.USER, Role.USER));
        save(new User("User 2", "email2@mail.ru", "123456", Role.USER, Role.USER));
        save(new User( "User 3", "email3@mail.ru", "123456", Role.USER, Role.USER));
        save(new User("User 4", "email4@mail.ru", "123456", Role.USER, Role.USER));
        save(new User("User 6", "email6@mail.ru", "123456", Role.USER, Role.USER));
        save(new User( "User 5", "email5@mail.ru", "123456", Role.USER, Role.USER));
        save(new User("User 7", "email7@mail.ru", "123456", Role.USER, Role.USER));
        save(new User( "User 8", "email8@mail.ru", "123456", Role.USER, Role.USER));
        save(new User("Administrator 1", "administrator1@mail.ru", "123456", Role.ADMIN, Role.ADMIN));
        save(new User("Administrator 2", "administrator2@mail.ru", "123456", Role.ADMIN, Role.ADMIN));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return true;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
        } else {
            repository.get(user.getId()).setEmail(user.getEmail());
            repository.get(user.getId()).setEnabled(user.isEnabled());
            repository.get(user.getId()).setPassword(user.getPassword());
            repository.get(user.getId()).setRegistered(user.getRegistered());
            repository.get(user.getId()).setCaloriesPerDay(user.getCaloriesPerDay());
        }
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream().sorted(Comparator.comparing(AbstractNamedEntity::getName)).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Stream<User> userStream = repository.values().stream().filter(user -> user.getEmail().equals(email));
        return (User) userStream.toArray()[0];
    }

}
