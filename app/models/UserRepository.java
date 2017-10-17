package models;

import com.google.inject.ImplementedBy;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPAUserRepository.class)
public interface UserRepository {

    CompletionStage<Integer> sync(List<User> users);

    CompletionStage<Optional<User>> create(User user);

    CompletionStage<Optional<User>> get(Integer id);

    CompletionStage<Optional<User>> update(User user);

    CompletionStage<Optional<User>> delete(Integer id);

    CompletionStage<Stream<User>> list();

    CompletionStage<Optional<User>> getUser(int id);
}
