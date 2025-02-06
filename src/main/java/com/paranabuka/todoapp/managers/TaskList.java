package com.paranabuka.todoapp.managers;

import com.paranabuka.todoapp.dto.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String FILEPATH = "src/tasks.bin";

    private static List<Task> tasks = new ArrayList<>();

    public TaskList() {
        loadTasks();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        sortTasksByStatus();
        saveTasks();
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public Task getTaskById(String id) {
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public void updateTask(Task task) {
        for (Task t : tasks) {
            if (t.getId().equals(task.getId())) {
                t.setTitle(task.getTitle());
                t.setDescription((task.getDescription()));
                t.setStatus(task.getStatus());
                t.setComments(task.getComments());
                break;
            }
        }
        sortTasksByStatus();
        saveTasks();
    }

    private void sortTasksByStatus() {
        tasks.sort(Comparator.comparingInt(this::getStatusValue));
    }

    private int getStatusValue(Task t) {
        return switch (t.getStatus()) {
            case "ToDo" -> 1;
            case "InProgress" -> 2;
            case "Done" -> 3;
            default -> 4;
        };
    }

    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILEPATH))) {
            oos.writeObject(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadTasks() {
        File file = new File(FILEPATH);
        if (!file.exists()) { return; }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            tasks = (List<Task>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
