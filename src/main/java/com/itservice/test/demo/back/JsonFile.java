package com.itservice.test.demo.back;

public class JsonFile {

    private TaskArrays task1;
    private TaskNumbers task2;
    private String type;

    public JsonFile() {}

    public JsonFile(TaskArrays task1, TaskNumbers task2, String type) {
        this.task1 = task1;
        this.task2 = task2;
        this.type = type;
    }

    public TaskArrays getTask1() {
        return task1;
    }

    public void setTask1(TaskArrays task1) {
        this.task1 = task1;
    }

    public TaskNumbers getTask2() {
        return task2;
    }

    public void setTask2(TaskNumbers task2) {
        this.task2 = task2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

class TaskArrays {

    private String[] a1;
    private String[] a2;

    public TaskArrays(){}

    public TaskArrays(String[] a1, String[] a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public String[] getA1() {
        return a1;
    }

    public void setA1(String[] a1) {
        this.a1 = a1;
    }

    public String[] getA2() {
        return a2;
    }

    public void setA2(String[] a2) {
        this.a2 = a2;
    }

    @Override
    public String toString() {

        String[] a1New = new String[a1.length];
        String[] a2New = new String[a2.length];

        for (int i = 0; i < a1.length; i++) {
            a1New[i] = "\"" + a1[i] + "\"";
        }

        for (int i = 0; i < a2.length; i++) {
            a2New[i] = "\"" + a2[i] + "\"";
        }

        return "a1: [" + String.join(", ", a1New) + "], " +
                "a2: [" + String.join(", ", a2New) + "]";
    }
}

class TaskNumbers {

    private String number;

    public TaskNumbers(){}

    public TaskNumbers(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return getNumber();
    }
}
