package com.scentalux.config;

import com.scentalux.model.Role;
import com.scentalux.repo.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    
    private final RolRepository roleRepo;
    
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
            roleRepo.save(admin);
            
            Role vendedor = new Role();
            vendedor.setName("VENDEDOR");
            vendedor.setDescription("Rol para vendedores");
            roleRepo.save(vendedor);
            
            System.out.println("✅ Roles iniciales creados: CLIENTE, ADMIN, VENDEDOR");
        }
    }
}