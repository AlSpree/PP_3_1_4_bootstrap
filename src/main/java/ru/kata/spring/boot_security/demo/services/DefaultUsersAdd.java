package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultUsersAdd {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public DefaultUsersAdd(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    public void defaultUsers () {
        List<Role> rolesAdmin = new ArrayList<>();
        Role adminRole = new Role("ROLE_ADMIN");
        User adminUser = new User("admin", "admin_surname", 35, "HR", passwordEncoder.encode("admin"));

        rolesAdmin.add(adminRole);
        adminRole.setUser(adminUser);
        adminUser.setRoles(rolesAdmin);
        userRepository.save(adminUser);


        List<Role> rolesUser = new ArrayList<>();
        Role userRole = new Role("ROLE_USER");
        User userUser = new User("user", "user_surname", 40, "IT",passwordEncoder.encode("user"));

        rolesUser.add(userRole);
        userRole.setUser(userUser);
        userUser.setRoles(rolesUser);
        userRepository.save(userUser);

        List<Role> rolesCombo = new ArrayList<>();
        Role adminRoleCombo = new Role("ROLE_ADMIN");
        Role userRoleCombo = new Role("ROLE_USER");
        User userCombo = new User("combo", "combo_surname", 10, "Sales",passwordEncoder.encode("combo"));
        adminRoleCombo.setUser(userCombo);
        userRoleCombo.setUser(userCombo);
        rolesCombo.add(adminRoleCombo);
        rolesCombo.add(userRoleCombo);
        userCombo.setRoles(rolesCombo);
        userRepository.save(userCombo);

    }

}
