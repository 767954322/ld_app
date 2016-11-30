/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.autodesk.shejijia.shared.components.common.uielements.commentview.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.autodesk.shejijia.shared.R;

import static com.autodesk.shejijia.shared.components.common.uielements.commentview.utils.CommonUtils.checkNotNull;


public class ActivityUtils {

    /**
     * 添加这个{@code fragment} 到container View中并指定 id  {@code frameId}。
     * 这个操作将会被{@code fragmentManager} 执行
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, String tag, boolean addToBackStack) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


}
