package org.itacademy.infrastructure;

import org.flywaydb.core.Flyway;

public class DatabaseMigration {

    public static void migrate(String url, String user, String password) {
        // En Java moderno no hace falta el Class.forName, pero no estorba.

        // Configuración Flyway
        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .cleanDisabled(false) // 1. PASO CLAVE: Permitir que Flyway pueda borrar tablas
                .load();

        try {
            // 2. PASO CLAVE: Borrar todo lo que exista actualmente en la DB
            flyway.clean();

            // 3. Ejecutar las migraciones (V1__..., V2__..., etc.) desde cero
            flyway.migrate();

            System.out.println("Base de datos limpiada y migrada con éxito.");
        } catch (Exception e) {
            System.err.println("Error durante la migración: " + e.getMessage());
            // Si hubo un fallo previo (el error "failed migration" que tenías),
            // a veces es necesario ejecutar repair() antes de clean()
            flyway.repair();
            throw new RuntimeException("Fallo en la base de datos", e);
        }
    }
}