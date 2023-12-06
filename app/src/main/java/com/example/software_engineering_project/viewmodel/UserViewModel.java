package com.example.software_engineering_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.User;

import java.util.UUID;

public class UserViewModel extends ViewModel {

    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    private static MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();

    private static MutableLiveData<Group> currentGroup = new MutableLiveData<>();

    // TODO
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<User> getCurrentUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(User user) {
        userLiveData.setValue(user);
    }

    public static MutableLiveData<User> getCurrentAppUser() {
        User defaultUser = new User();
        defaultUser.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        defaultUser.setEmail("john.doe@example.com");
        defaultUser.setFirstName("John");
        currentUserLiveData.setValue(defaultUser);

        return currentUserLiveData;
    }

    // TODO get real currentGroup
    public static MutableLiveData<Group> getCurrentGroup() {
        Group group = new Group();
        group.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        group.setCreatedBy(UserViewModel.getCurrentAppUser().getValue());
        currentGroup.setValue(group);
        return currentGroup;
    }

    public static void setCurrentGroup(MutableLiveData<Group> currentGroup) {
        UserViewModel.currentGroup = currentGroup;
    }

    public static void setAppUser(User currentAppUser) {
        currentUserLiveData.setValue(currentAppUser);

        // TODO check if user is part of currentGroup. If not, set new or empty currentGroup
    }

    // TODO implement method
    // public void updateCurrentGroup(Group currentGroup) {}
}