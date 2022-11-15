package ru.kata.spring.boot_security.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    public List<User> findAll () {
        return userRepository.findAll();
    }


    public User findUserByName(String name) {
        Optional<User> foundUser = userRepository.findUserByName(name);
        return foundUser.orElse(null);
    }


    @Transactional
    public void saveNewUser(User user, String role) {
        List<Role> roles = new ArrayList<>();
        if(role==null) {
            role = "ROLE_NONE";
        }
        Role newRole = new Role(role, user);
        roles.add(newRole);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(User userUpdate, String roleUpdate) {
        List<Role> roles = new ArrayList<>();
        User foundUser = userRepository.findUserByName(userUpdate.getName()).get();

        if (roleUpdate == null) {
            if (!foundUser.getRolesName().contains("ROLE_NONE")) {
                System.out.println("getRolesName().contains(ROLE_NONE)");
                List<Role> roles1 = foundUser.getRoles();
                for (Role role : roles1) {
                    System.out.println(role.getId());
                    roleRepository.deleteById(role.getId());
                }
                roles.add(new Role("ROLE_NONE", userUpdate));
                userUpdate.setRoles(roles);
            }
        } else {
//            System.out.println("выбран ROLE_ADMIN или ROLE_USER");
            List<Role> roles1 = foundUser.getRoles();
            for (Role role : roles1) {
                System.out.println(role.getId());
                roleRepository.deleteById(role.getId());
            }
            roles.add(new Role(roleUpdate, userUpdate));
            userUpdate.setRoles(roles);
        }
        userUpdate.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        userRepository.save(userUpdate);
    }


    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFound = userRepository.findUserByName(username);
        if(userFound.isEmpty()){
            throw new UsernameNotFoundException(String.format("User '%s' not found!", username));
        }
        return new org.springframework.security.core.userdetails.User(userFound.get().getName(), userFound.get().getPassword(), userFound.get().getAuthorities());
    }

    public User getUser(String username) {
        return userRepository.findUserByName(username).get();
    }

    public List<String> getRoleList () {
        List<Role> roles = roleRepository.findAll();
        List<String> listRoles = new ArrayList<>();
        for(Role role: roles) {
            listRoles.add(role.getRole());
        }
        return listRoles;
    }



}
