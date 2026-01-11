package com.example.demo.Services.implementation;

import com.example.demo.Services.interfaces.IUserAppService;
import com.example.demo.entites.UserApp;
import com.example.demo.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAppService implements IUserAppService {

    @Autowired
    private UserAppRepository userAppRepository;

    @Override
    public UserApp addUserApp(UserApp userApp) {
        return userAppRepository.save(userApp);
    }

    @Override
    public UserApp getUserAppById(Long id) {
        return userAppRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public UserApp getUserAppByEmail(String email) {
        return userAppRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    @Override
    public List<UserApp> getAllUserApps(){
        return userAppRepository.findAll();
    }


    @Override
    public void deleteUserApp(Long id) {
        userAppRepository.deleteById(id);
    }
}
