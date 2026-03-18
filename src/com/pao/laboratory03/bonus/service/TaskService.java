package com.pao.laboratory03.bonus.service;
import com.pao.laboratory03.bonus.exception.DuplicateTaskException;
import com.pao.laboratory03.bonus.exception.InvalidTransitionException;
import com.pao.laboratory03.bonus.exception.TaskNotFoundException;
import com.pao.laboratory03.bonus.model.Task;
import com.pao.laboratory03.bonus.model.Priority;
import com.pao.laboratory03.bonus.model.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TaskService {
    private Map<String,Task> tasksById;
    private Map<Priority,List<Task>> tasksByPriority;
    private List<String> auditLog;
    private int nextId;
    private static TaskService instance;
    private TaskService() {
        this.tasksById=new HashMap<>();
        this.tasksByPriority=new HashMap<>();
        this.auditLog= new ArrayList<>();
        this.nextId=1;
    }
    public static TaskService getInstance(){
        if(instance == null){
            instance = new TaskService();
        }
        return instance;
    }
    public Task addTask(String title,Priority priority){
        String s = String.format("T%03d",nextId++);
        Task t= new Task(s,title,Status.TODO,priority,null);
        for(Task aux:tasksById.values()){
            if(aux.getTitle().equals(t.getTitle())){
                throw new DuplicateTaskException("");
            }
        }
        tasksById.put(s,t);
        tasksByPriority.computeIfAbsent(priority, k -> new ArrayList<>()).add(t);
        auditLog.add("[ADD] "+s+": "+ title + " ("+priority+")");
        return t;
    }
    public void assignTask(String taskid,String assignee){
        if(tasksById.containsKey(taskid)){
            tasksById.get(taskid).setAssignee(assignee);
            auditLog.add("[ASSIGN] "+taskid+" -> "+assignee);
        }else{
            throw new TaskNotFoundException("");
        }
    }
    public void changeStatus(String taskid,Status newStatus){
        if(tasksById.containsKey(taskid)){
           if(!tasksById.get(taskid).getStatus().canTransition(newStatus)){
               throw new InvalidTransitionException(tasksById.get(taskid).getStatus(),newStatus);
           }else{
               Status vechi=tasksById.get(taskid).getStatus();
               tasksById.get(taskid).setStatus(newStatus);
               auditLog.add("[STATUS] "+taskid+ ": "+vechi+" -> "+newStatus);
           }
        }else{
            throw new TaskNotFoundException("");
        }
    }
    public List<Task> getTasksByPriority(Priority priority){
        return tasksByPriority.getOrDefault(priority,new ArrayList<>());
    }
    public Map<Status,Long> getStatusSummary(){
        Map<Status,Long> sum = new HashMap<>();
        for(Status s: Status.values()){
            long count=0;
            for(Task t: tasksById.values()){
                if(t.getStatus()==s){
                    count++;
                }
            }
            sum.put(s,count);
        }
        return sum;
    }
    public List<Task> getUnassignedTasks(){
        List<Task> lista= new ArrayList<>();
        for(Task t: tasksById.values()){
            if(t.getAssignee()==null){
                lista.add(t);
            }
        }
        return lista;
    }
    public void printAuditLog(){
        for(String log: auditLog){
            System.out.println(log);
        }
    }
    public double getTotalUrgencyScore(int baseDays){
        double total=0;
        for(Task t: tasksById.values()){
            if(t.getStatus()==Status.TODO || t.getStatus()==Status.IN_PROGRESS){
                total+=t.getPriority().calculateScore(baseDays);
            }
        }
        return total;
    }
}
