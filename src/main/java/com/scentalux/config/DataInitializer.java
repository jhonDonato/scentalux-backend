package com.scentalux.config;

import com.scentalux.model.Role;
import com.scentalux.model.User;
import com.scentalux.repo.RolRepository;
import com.scentalux.repo.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    
    private final RolRepository roleRepo;
    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    
    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        // Verificar si ya existen roles
        if (roleRepo.count() == 0) {
            // Crear roles básicos
            Role cliente = new Role();
            cliente.setName("CLIENTE");
            cliente.setDescription("Rol para clientes de la aplicación");
            roleRepo.save(cliente);
            
            Role admin = new Role();
            admin.setName("ADMIN");
            admin.setDescription("Rol para administradores");
            Role savedAdmin = roleRepo.save(admin);
            
            System.out.println("✅ Roles iniciales creados: CLIENTE, ADMIN");
            
            // Crear usuario administrador por defecto
            createAdminUser(savedAdmin);
        }
        
        // También crear admin si los roles ya existen pero no hay usuarios admin
        checkAndCreateAdmin();
    }
    
    private void createAdminUser(Role adminRole) {
        // Verificar si ya existe el usuario admin
        if (userRepo.findOneByUsername("jhon@gmail.com") == null) {
            User adminUser = new User();
            adminUser.setUsername("jhon@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("123456"));
            adminUser.setEnabled(true);
            adminUser.setRoles(Arrays.asList(adminRole));
            
            userRepo.save(adminUser);
            System.out.println("✅ Usuario administrador creado: jhon@gmail.com / 123456");
        }
    }
    
    private void checkAndCreateAdmin() {
        // Buscar rol ADMIN
        Role adminRole = roleRepo.findAll().stream()
                .filter(role -> "ADMIN".equals(role.getName()))
                .findFirst()
                .orElse(null);
                
        if (adminRole != null && userRepo.findOneByUsername("jhon@gmail.com") == null) {
            createAdminUser(adminRole);
        }
    }
}