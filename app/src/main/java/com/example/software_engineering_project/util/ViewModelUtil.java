package com.example.software_engineering_project.util;


import androidx.lifecycle.ViewModelStoreOwner;
import com.example.software_engineering_project.viewmodel.EntityViewModel;

public class ViewModelUtil<T> {

//    public <T> EntityViewModel<T> createEntityViewModel(Class<T> entityClass) {
//        EntityViewModelFactory<T> factory = new EntityViewModelFactory<>(entityClass);
//        return new ViewModelProvider(this, factory).get(EntityViewModel.class);
//    }
//
//    public <T> EntityViewModel<T> createEntityViewModel(Application application, Class<T> entityClass, int i) {
//        EntityViewModelFactory<T> factory = new EntityViewModelFactory<>(entityClass);
//        return new ViewModelProvider(this, factory).get(EntityViewModel.class);
//    }

    public static <T> EntityViewModel<T> createEntityViewModel(Class<T> entityClass, ViewModelStoreOwner owner) {
//        EntityViewModelFactory<T> factory = new EntityViewModelFactory<>(entityClass);
//        return new ViewModelProvider(owner, factory).get(EntityViewModel.class);
        return null;
    }
}
