package com.e11even.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.e11even.backend.models.User;
import com.e11even.backend.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    // Récupère un utilisateur via son adresse email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

    // Met à jour les informations publiques du profil 
    public User update(Long id, User updated) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        if (updated.getFirstName() != null) user.setFirstName(updated.getFirstName());
        if (updated.getLastName() != null)  user.setLastName(updated.getLastName());
        if (updated.getBio() != null)       user.setBio(updated.getBio());
        if (updated.getAvatarUrl() != null) user.setAvatarUrl(updated.getAvatarUrl());
        return userRepository.save(user);
    }

    // Met à jour l'email après vérification de la disponibilité et du mot de passe
    public User updateEmail(Long id, String newEmail, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        if (userRepository.findByEmail(newEmail).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    // Modifie le mot de passe après vérification de l'ancien
    public User updatePassword(Long id, String currentPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Mot de passe actuel incorrect");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

}