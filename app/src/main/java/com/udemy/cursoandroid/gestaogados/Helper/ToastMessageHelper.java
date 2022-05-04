package com.udemy.cursoandroid.gestaogados.Helper;

import android.content.Context;
import android.widget.Toast;

public class ToastMessageHelper {
    public static void SetToastMessageAndShow(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
