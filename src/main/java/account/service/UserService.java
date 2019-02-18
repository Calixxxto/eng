package account.service;


import account.repository.UserRepository;
import data.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserById(Long userId) {
        return userRepository.findOne(userId);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User createUser(User user) {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        return saveUser(user);
    }

    @Transactional
    public User updateUser(Long userId, User user) {
        User currentUser = findUserById(userId);
        if (currentUser != null) {
            String newPassword = user.getPassword();

            // password can be changed by self or admin :
            if (newPassword != null) {
                currentUser.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            }
            if (user.getFullName() != null) {
                currentUser.setFullName(user.getFullName());
            }
            saveUser(currentUser);
        }
        return currentUser;
    }


    public User getUser(Long userId) {
        return userRepository.findOne(userId);
    }

}
