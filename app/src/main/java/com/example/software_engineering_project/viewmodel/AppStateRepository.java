package com.example.software_engineering_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.User;

import java.util.UUID;

/**
 * The AppStateRepository class is a subclass of Android's ViewModel class, responsible for managing and
 * providing data related to the current user and group within the application. It utilizes LiveData
 * to observe changes in user and group data.
 */
public class AppStateRepository extends ViewModel {

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

    /**
     * Returns a LiveData object containing information about the default application user.
     *
     * @return MutableLiveData<User> The LiveData object containing information about the default application user.
     */
    public static MutableLiveData<User> getCurrentAppUser() {
        User defaultUser = new User();
        defaultUser.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        defaultUser.setEmail("john.doe@example.com");
        defaultUser.setFirstName("John");
        currentUserLiveData.setValue(defaultUser);

        return currentUserLiveData;
    }

    /**
     * Returns a LiveData object containing information about the current group.
     *
     * @return MutableLiveData<Group> The LiveData object containing information about the current group.
     */
    // TODO get real currentGroup
    public static MutableLiveData<Group> getCurrentGroup() {
        Group group = new Group();
        group.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        group.setCreatedBy(AppStateRepository.getCurrentAppUser().getValue());
        currentGroup.setValue(group);
        return currentGroup;
    }

    /**
     * Sets the LiveData object containing information about the current group.
     *
     * @param currentGroup The LiveData object containing information about the current group.
     */
    public static void setCurrentGroup(MutableLiveData<Group> currentGroup) {
        AppStateRepository.currentGroup = currentGroup;
    }

    /**
     * Sets the current application user and checks if the user is part of the current group.
     * If not, sets a new or empty current group.
     *
     * @param currentAppUser The User object representing the current application user.
     */
    public static void setAppUser(User currentAppUser) {
        currentUserLiveData.setValue(currentAppUser);

        // TODO check if user is part of currentGroup. If not, set new or empty currentGroup
    }

    // TODO implement method
    // public void updateCurrentGroup(Group currentGroup) {}
}