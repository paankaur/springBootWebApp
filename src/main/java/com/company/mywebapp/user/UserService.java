package com.company.mywebapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ConnectionBuilder;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public ConnectionBuilder save;
    @Autowired private UserRepository repo;

    public List<User> listAll() {
        return (List<User>) repo.findAll();
    }

    public void save(User user) {
        if (user.getId() != null) {
            // Editing existing user
            User existingUser = repo.findById(user.getId())
                    .orElseThrow(() -> new UserNotFoundException("Could not find any user with id " + user.getId()));

            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setEnabled(user.isEnabled());

            // Only update password if a new one was provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }

            repo.save(existingUser);
        } else {
            // New user
            repo.save(user);
        }
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> optionalUser = repo.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFoundException("Could not find any users with id " + id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0 ) {
            throw new UserNotFoundException("Could not find any users with id " + id);
        }
        repo.deleteById(id);
    }
}
