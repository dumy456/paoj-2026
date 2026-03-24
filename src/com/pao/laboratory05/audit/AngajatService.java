package com.pao.laboratory05.audit;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService{
    private Angajat[] angajati=new Angajat[0];
    private AuditEntry[] auditLog= new AuditEntry[0];
    private static class Holder{
        public static AngajatService instance= new AngajatService();
    }
    public static AngajatService getInstance(){
        return Holder.instance;
    }
    private AngajatService(){}
    void addAngajat(Angajat a){
        Angajat[] aux= new Angajat[angajati.length+1];
        System.arraycopy(angajati,0,aux,0,angajati.length);
        aux[angajati.length]=a;
        angajati=aux;
        System.out.println(String.format("Angajat adaugat: %s",a.getNume()));
        logAction("ADD",a.getNume());
    }
    public void printAll(){
        for(Angajat a:angajati)
            System.out.println(a.toString());
    }
    public void listBySalary(){
        Angajat[] copy= angajati.clone();
        Arrays.sort(copy);
        for(Angajat a:copy)
            System.out.println(a.toString());
    }
    public void findByDepartament(String numeDept) {
        boolean found=false;
        for(Angajat a: angajati){
            if(a.getDepartament().nume().equalsIgnoreCase(numeDept)){
                System.out.println(a.toString());
                found=true;
            }
        }
        if(!found){
            System.out.println("Niciun angajat in departamentul: "+numeDept);
        }
        logAction("FIND_BY_DEPT",numeDept);
    }
    private void logAction(String action, String target){
        AuditEntry a= new AuditEntry(action,target, LocalDateTime.now().toString());
        AuditEntry[] aux= new AuditEntry[auditLog.length+1];
        System.arraycopy(auditLog,0,aux,0,auditLog.length);
        aux[auditLog.length]=a;
        auditLog=aux;
    }
    public void printAuditLog(){
        for(AuditEntry a: auditLog){
            System.out.println(a.toString());
        }
    }
}
