package com.nbserver.androideatitv2server.ui.category;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nbserver.androideatitv2server.Callback.ICategoryCallbackListener;
import com.nbserver.androideatitv2server.Common.Common;
import com.nbserver.androideatitv2server.Model.CategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel implements ICategoryCallbackListener {

    private MutableLiveData<List<CategoryModel>> categoryListMutable;
    private MutableLiveData<String> messageError = new MutableLiveData<>();
    private ICategoryCallbackListener categoryCallbackListener;

    public CategoryViewModel() {
        categoryCallbackListener = this;
    }

    public MutableLiveData<List<CategoryModel>> getCategoryListMutable() {
        if(categoryListMutable == null)
        {
            categoryListMutable = new MutableLiveData<>();
            messageError = new MutableLiveData<>();
            loadCategories();
        }

        return categoryListMutable;
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    public void loadCategories() {
        List<CategoryModel> tempList = new ArrayList<>();
        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference(Common.CATEGORY_REF);
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren())
                {
                    CategoryModel categoryModel = itemSnapshot.getValue(CategoryModel.class);
                    categoryModel.setMenu_id(itemSnapshot.getKey());
                    tempList.add(categoryModel);
                }
                categoryCallbackListener.onCategoryLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                categoryCallbackListener.onCategoryLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onCategoryLoadSuccess(List<CategoryModel> categoryList) {
        categoryListMutable.setValue(categoryList);
    }

    @Override
    public void onCategoryLoadFailed(String message) {
        messageError.setValue(message);
    }
}