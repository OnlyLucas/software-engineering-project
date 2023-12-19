package com.example.software_engineering_project.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.software_engineering_project.entity.Group;
import com.example.software_engineering_project.entity.User;

/**
 * The AppStateRepository class is a subclass of Android's ViewModel class, responsible for managing and
 * providing data related to the current user and group within the application. It utilizes LiveData
 * to observe changes in user and group data.
 */
public class AppStateRepository extends ViewModel {

    private static MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();

    private static MutableLiveData<Group> currentGroupLiveData = new MutableLiveData<>();


    /**
     * Returns a LiveData object containing information about the default application user.
     *
     * @return MutableLiveData<User> The LiveData object containing information about the default application user.
     */
    public static MutableLiveData<User> getCurrentAppUserLiveData() {
        return currentUserLiveData;
    }

    public static void setCurrentUser(User currentUser) {
        currentUserLiveData.setValue(currentUser);
    }

    /**
     * Used for async update in background threads
     * @param currentUser User that should be set
     */
    public static void postCurrentUser(User currentUser) {
        currentUserLiveData.postValue(currentUser);
    }

    /**
     * Returns a LiveData object containing information about the current group.
     *
     * @return MutableLiveData<Group> The LiveData object containing information about the current group.
     */
    public static MutableLiveData<Group> getCurrentGroupLiveData() {
        return currentGroupLiveData;
    }

    /**
     * Sets the LiveData object containing information about the current group.
     *
     * @param currentGroup The LiveData object containing information about the current group.
     */
    public static void setCurrentGroup(Group currentGroup) {
        currentGroupLiveData.setValue(currentGroup);
    }

    /**
     * Used for async update in background threads
     * @param currentGroup group that should be set
      */
    public static void postCurrentGroup(Group currentGroup) {
        currentGroupLiveData.postValue(currentGroup);
    }

    // TODO implement method
    // public void updateCurrentGroup(Group currentGroup) {}
}