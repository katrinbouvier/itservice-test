package com.itservice.test.demo.back;

import org.springframework.stereotype.Component;

import com.itservice.test.demo.ui.MainView;

import java.util.*;

@Component
public class TaskSolver {

    private StringBuilder res = new StringBuilder();

    public String calculate(String type, String task) {

        String result;
        res = new StringBuilder();

            switch (type) {
                case MainView.TASK_1:
                    result = findSubstrings(task);
                    break;
                case MainView.TASK_2:
                    result = checkValidityNumbers(task) ? writeNumberExpanded(task) : "Invalid task";
                    break;
                default:
                    return "Invalid type";
            }
            return result;
    }

    private boolean checkValidityNumbers(String task) {

        StringBuilder newSb = new StringBuilder(task);

        for (int i = 0; i < newSb.length(); i++) {
            if(!(Character.toString(newSb.charAt(i)).matches("[-+]?\\d+"))) {
                return false;
            }
        }

        return true;
    }

    //TODO: checkValidityArrays()?

    private String findSubstrings(String task) {

        List<String[]> arr = decodeString(task);

        String[] a1 = arr.get(0);
        String[] a2 = arr.get(1);

        Set<String> result = new HashSet<>();

        for(String main : a2) {
            for (String sub : a1) {
                if(main.contains(sub)){
                    result.add(sub);
                }
            }
        }

        List<String> list = new ArrayList<>(result);
        Collections.sort(list);

        return String.join(", ", list);
    }

    private String writeNumberExpanded(String task) {

        StringBuilder newSb = new StringBuilder(task);

        if(newSb.length() == 0) return res.toString();

        if(newSb.charAt(0)=='0') {
            writeNumberExpanded(newSb.deleteCharAt(0).toString());
        } else {
            if (res.length()!=0) res.append("+");

            res.append(newSb.charAt(0));

            for (int i = 0; i < newSb.length() - 1; i++) {

                res.append("0");
            }

            writeNumberExpanded(newSb.deleteCharAt(0).toString());
        }

        return res.toString();
    }

    // TODO: optimise this
    public List<String[]> decodeString(String task) {

        ArrayList<String[]> list = new ArrayList<>();
        list.add(task
                .substring(task.indexOf('[')+1, task.indexOf(']'))
                .replace("\"", "")
                .replaceAll("\\s+", "")
                .split(","));
        list.add(task
                .substring(task.lastIndexOf('[')+1, task.lastIndexOf(']'))
                .replace("\"", "")
                .replaceAll("\\s+", "")
                .split(","));

        return list;
    }
}
