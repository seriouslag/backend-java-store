package com.landongavin.services.Interfaces;

import com.landongavin.interfaces.UserRoleCallback;

public interface IFirebaseService {
    void addListenerForUserRole(String uid, UserRoleCallback userRoleCallback);
    boolean firebaseUserExists(String uid);
    boolean addRoleToFirebaseUser(String uid, String role);
}
