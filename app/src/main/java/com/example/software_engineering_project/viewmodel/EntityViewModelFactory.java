package com.example.software_engineering_project.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EntityViewModelFactory<T> extends ViewModelProvider.NewInstanceFactory {

//    private final Class<T> entityClass;
//
//    public EntityViewModelFactory(Class<T> entityClass) {
//        this.entityClass = entityClass;
//    }
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(EntityViewModel.class)) {
//            return (T) new EntityViewModel<>(entityClass.getClass());
//        }
//        throw new IllegalArgumentException("Unknown ViewModel class");
//    }
}
