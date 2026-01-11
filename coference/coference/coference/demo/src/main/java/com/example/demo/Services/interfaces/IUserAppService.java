package com.example.demo.Services.interfaces;

import com.example.demo.entites.UserApp;

import java.util.List;

public interface IUserAppService {

    UserApp addUserApp(UserApp userApp);
    UserApp getUserAppById(Long id);
    UserApp getUserAppByEmail(String email);
    void deleteUserApp(Long id);
    List<UserApp> getAllUserApps();

}
