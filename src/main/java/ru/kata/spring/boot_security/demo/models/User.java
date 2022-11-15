package ru.kata.spring.boot_security.demo.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users_bs")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_name")
//    @NotBlank(message = "Имя не должно быть пустым")
//    @Size(min=2, max=100, message = "Имя должно быть от 2 до 100 символов")
    private String name;


    @Column(name="last_name")
//    @NotBlank(message = "Имя не должно быть пустым")
//    @Size(min=2, max=100, message = "Имя должно быть от 2 до 100 символов")
    private String surname;

//    @Min(value = 0, message = "Минимальное значение 1")
//    @Max(value = 120, message = "Максимальное значение 119")
    private int age;

//    @NotBlank(message = "поле не должно быть пустым")
    private String department;


//    @NotBlank(message = "пароль не должно быть пустым")
//    @NonNull
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Role> roles;

    public User() {
    }

    public User(String name, String surname, int age, String department, String password) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.department = department;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<String> getRolesName() {
        List<String> roleNames = new ArrayList<>();
        for (Role role: roles) {
            roleNames.add(role.getRole());
        }
        return roleNames;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setRolesList(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
