package com.example.demo.Services.interfaces;
import com.example.demo.Enumeration.*;
import com.example.demo.entites.*;


public interface IAffecterRole {

    public void affecterRole(UserApp user, Conference conference, ERole role);

    public boolean ARole(UserApp user, Conference conference, ERole role);
}