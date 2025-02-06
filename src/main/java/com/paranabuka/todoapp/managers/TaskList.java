package com.paranabuka.todoapp.managers;

import com.paranabuka.todoapp.dto.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskList {

    private static final List<Task> tasks = new ArrayList<>();

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        sortTasksByStatus();
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
}
