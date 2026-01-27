package com.example.smartHome.util;

import com.example.smartHome.entity.DeviceInventory;
import com.example.smartHome.entity.User;
import com.example.smartHome.repository.DeviceInventoryRepository;
import com.example.smartHome.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DeviceInventoryRepository deviceInventoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // -------- USERS --------
        List<User> users = List.of(
                new User("venkat", passwordEncoder.encode("password123"), "Venkat Kalyan", null),
                new User("alice", passwordEncoder.encode("password123"), "Alice Smith", null),
                new User("bob", passwordEncoder.encode("password123"), "Bob Johnson", null),
                new User("charlie", passwordEncoder.encode("password123"), "Charlie Brown", null),
                new User("david", passwordEncoder.encode("password123"), "David Lee", null)
        );

        userRepository.saveAll(users);
        String encodedDevicePassword = passwordEncoder.encode("device123");

        List<DeviceInventory> devices = List.of(
                new DeviceInventory("KI1001", "device_user_1", encodedDevicePassword,
                        LocalDateTime.of(2024, 1, 10, 10, 30), "Bangalore",false),
                new DeviceInventory("KI1002", "device_user_2", encodedDevicePassword,
                        LocalDateTime.of(2024, 1, 12, 11, 0), "Hyderabad",false),
                new DeviceInventory("KI1003", "device_user_3", encodedDevicePassword,
                        LocalDateTime.of(2024, 1, 15, 9, 45), "Chennai",false),
                new DeviceInventory("KI1004", "device_user_4", encodedDevicePassword,
                        LocalDateTime.of(2024, 1, 18, 14, 20), "Pune",false),
                new DeviceInventory("KI1005", "device_user_5", encodedDevicePassword,
                        LocalDateTime.of(2024, 1, 20, 16, 10), "Noida",false),
                new DeviceInventory("KI1006", "device_user_6", encodedDevicePassword,
                        LocalDateTime.of(2024, 1, 22, 8, 40), "Gurgaon",false),
                new DeviceInventory("KI1007", "device_user_7", encodedDevicePassword,
                        LocalDateTime.of(2024, 1, 25, 13, 15), "Mumbai",false),
                new DeviceInventory("KI1008", "device_user_8", encodedDevicePassword,
                        LocalDateTime.of(2024, 1, 28, 17, 50), "Kolkata",false),
                new DeviceInventory("KI1009", "device_user_9", encodedDevicePassword,
                        LocalDateTime.of(2024, 2, 1, 12, 0), "Ahmedabad",false),
                new DeviceInventory("KI1010", "device_user_10", encodedDevicePassword,
                        LocalDateTime.of(2024, 2, 5, 15, 30), "Jaipur",false)
        );

        deviceInventoryRepository.saveAll(devices);
    }
}
