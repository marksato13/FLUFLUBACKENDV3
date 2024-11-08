package com.example.demo.serviceImpl;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.entity.UserRoleId;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Optional<User> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User register(User user) {
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        user.setImage(null); 
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public String assignRole(Long userId, Long roleId) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            Optional<Role> roleOpt = roleRepository.findById(roleId);

            if (userOpt.isEmpty()) {
                return "Error: Usuario no encontrado";
            }

            if (roleOpt.isEmpty()) {
                return "Error: Rol no encontrado";
            }

            User user = userOpt.get();
            Role role = roleOpt.get();

            UserRoleId userRoleId = new UserRoleId(userId, roleId);

            if (userRoleRepository.existsById(userRoleId)) {
                return "El usuario ya tiene este rol";
            }

            UserRole userRole = new UserRole();
            userRole.setId(userRoleId);
            userRole.setUser(user);
            userRole.setRole(role);

            Timestamp now = new Timestamp(System.currentTimeMillis());
            userRole.setCreatedAt(now);
            userRole.setUpdatedAt(now);

            userRoleRepository.save(userRole);

            return "Rol asignado exitosamente";
        } catch (Exception e) {
            return "Error al asignar el rol: " + e.getMessage();
        }
    }
}
