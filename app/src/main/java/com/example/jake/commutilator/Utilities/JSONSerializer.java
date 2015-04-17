package com.example.jake.commutilator.Utilities;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * Created by Jake on 4/17/2015.
 */
public class JSONSerializer {

    public void Serialize(Object objectToSerialize, String fileName, Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(objectToSerialize);

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Object Deserialize(Type objectType, String fileName, Context context) {
        File file = context.getFileStreamPath(fileName);
        StringBuffer jsonBuffer = new StringBuffer("");
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                FileInputStream fIn = context.openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fIn);
                BufferedReader buffreader = new BufferedReader(isr);

                String readString = buffreader.readLine();
                while (readString != null) {
                    jsonBuffer.append(readString);
                    readString = buffreader.readLine();
                }

                isr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }

            try {
                return new Gson().fromJson(jsonBuffer.toString().trim(), objectType);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return null;
    }

}
