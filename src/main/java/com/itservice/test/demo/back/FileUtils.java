package com.itservice.test.demo.back;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileUtils {

    private  Map<String, String> data = new HashMap<>();

    public Map<String, String> getFileContent(InputStream inputStream) throws IOException {

        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        int result = bis.read();
        while(result != -1) {
            buf.write((byte) result);
            result = bis.read();
        }

        // TODO: fix 187 191
        // weak solution
        StringBuilder fix = new StringBuilder(buf.toString("UTF-8"));
        fix.deleteCharAt(0);
        String fixedBuf = fix.toString();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        JsonFile file = gson.fromJson(fixedBuf, JsonFile.class);

        data.clear();

        if ((file.getTask1() == null && file.getTask2() == null)
                || file.getType()== null) {
            data.put("error", "Invalid data");
            System.out.println("both null");
            return data;
        }

        if(file.getTask1() != null) {
            data.put("task", file.getTask1().toString());
            data.put("type", file.getType());
        } else {
            data.put("task", file.getTask2().toString());
            data.put("type", file.getType());
        }
        return data;
    }

}
