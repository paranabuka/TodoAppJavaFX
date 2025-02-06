package com.paranabuka.todoapp.managers;

import com.paranabuka.todoapp.dto.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private static final List<Task> tasks = new ArrayList<>();

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
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
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getId().equals(task.getId())) {
                t.setTitle(task.getTitle());
                t.setDescription((task.getDescription()));
                t.setStatus(task.getStatus());
                t.setComments(task.getComments());
                break;
            }
        }
    }
}
