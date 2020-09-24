package com.itservice.test.demo.ui;

import com.itservice.test.demo.back.FileUtils;
import com.itservice.test.demo.back.TaskSolver;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Route
public class MainView extends VerticalLayout {

    public final static String TASK_1 = "Задача с массивами";
    public final static String TASK_2 = "Задача с числами";

    private TextField taskField = new TextField();
    private ComboBox<String> taskTypeCombo = new ComboBox<>();
    private Button calcBtn = new Button("Посчитать");
    private Button resetBtn = new Button("Очистить");
    private Button uploadBtn = new Button("Загрузить");
    private MemoryBuffer buffer = new MemoryBuffer();
    private Upload upload = new Upload(buffer);
    private HorizontalLayout actions = new HorizontalLayout();
    private TextField resultField = new TextField();
    private Anchor download = new Anchor("");

    private TaskSolver taskSolver;
    private FileUtils fileUtils;

    public MainView(TaskSolver taskSolver, FileUtils fileUtils) {

        this.taskSolver = taskSolver;
        this.fileUtils = fileUtils;

        configureTaskField();
        configureTaskTypeCombo();
        configureCalcBtn();
        configureSaveBtn();
        configureResetButton();
        configureUpload();
        configureResultField();
        configureActions();

        add(taskField, taskTypeCombo,  upload, actions, resultField);
    }

    private void configureResetButton() {
        resetBtn.addClickListener(click -> {
            taskField.setValue("");
            taskTypeCombo.setValue("");
            resultField.setValue("");
        } );

    }

    private void configureActions() {
        actions.add(calcBtn, download, resetBtn);
    }

    private void configureResultField() {
        resultField.setLabel("Результат");
        resultField.setWidth("25em");
    }

    private void configureUpload() {
        upload.setAcceptedFileTypes(".txt");
        upload.setUploadButton(uploadBtn);
        upload.setMaxFiles(1);
        upload.setDropLabel(new Label("Upload file in .txt format"));

        upload.addFinishedListener(e -> {

            InputStream inputStream = buffer.getInputStream();
            Map<String, String> content = new HashMap<>();

            try {
                content = fileUtils.getFileContent(inputStream);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if(content.get("error")!=null) {
                resultField.setValue(content.get("error"));
            }

            taskField.setValue(content.get("task"));
            taskTypeCombo.setValue(content.get("type"));

            resultField.setValue("");

            resultField.setValue(taskSolver.calculate(taskTypeCombo.getValue(), taskField.getValue()));

        });
    }

    // TODO: nothing here yet
    private void configureSaveBtn() {

        Button saveBtn = new Button("Сохранить");

        download.add(saveBtn);

        saveBtn.addClickListener(click -> {

            String prep = encodeString(taskField.getValue(), taskTypeCombo.getValue());
            System.out.println(prep);

            if (taskField.getValue()==null||taskTypeCombo.getValue()==null)
                resultField.setValue("Something is missing...");

            StreamResource resource = new StreamResource("task.txt", () ->
            {
                try {
                    return new ByteArrayInputStream(prep.getBytes("windows-1251"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } return null;
            });

            download.setHref(resource);
            download.setId("link");

        });
    }

    private void configureCalcBtn() {
        calcBtn.addClickListener(click -> {
            resultField.setValue("");

            String result;

            if(taskTypeCombo.getValue()!= null && !taskField.getValue().equals("")) {
               result = taskSolver.calculate(taskTypeCombo.getValue(), taskField.getValue());
            } else result = "Something missing";
            resultField.setValue(result);
        });
    }

    private void configureTaskTypeCombo() {
        taskTypeCombo.setItems(TASK_1, TASK_2);

        taskTypeCombo.setPlaceholder("Тип задачи");
        taskTypeCombo.setWidth("25em");
    }

    private void configureTaskField() {
        taskField.setLabel("Введите условие");
        taskField.setWidth("25em");
    }


    // TODO: optimise this too
    public String encodeString(String task, String type) {

        StringBuilder t = new StringBuilder(task);
        StringBuilder res = new StringBuilder();

        if(type.equals(MainView.TASK_1)) {

            res.append("{ \"task1\": {" +
                    "\"a1\": []," +
                    "\"a2\": []" +
                    "}," +
                    "\"task2\": \"null\"," +
                    "\"type\": \"Задача с массивами\"");

        } else {
            res
                .append("{ \"task1\": null, \"task2\": {\"number\": \"")
                .append(task)
                .append("\"},")
                .append("\"type\": \"Задача с числами\"}");
        }

        String ta =  "a1 = [a, b], a2 = [c, d]";
        String ty =  "Задание с массивами";

        System.out.println("res" + res.toString());

        return res.toString();
    }

}

