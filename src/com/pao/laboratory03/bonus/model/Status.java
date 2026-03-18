package com.pao.laboratory03.bonus.model;

public enum Status {
    TODO{
        @Override
        public boolean canTransition(Status next){
            return next==IN_PROGRESS || next==CANCELED;
        }
    },IN_PROGRESS{
        @Override
        public boolean canTransition(Status next){
            return next==DONE || next==CANCELED;
        }
    },DONE{
        @Override
        public boolean canTransition(Status next){
            return false;
        }
    },CANCELED{
        @Override
        public boolean canTransition(Status next){
            return false;
        }
    };
    public abstract boolean canTransition(Status next);
}
