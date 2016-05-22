package ru.nsu.fit.g13205.fink.raytracing;

public enum Quality {

    ROUGH(0.5), NORMAL(1), FINE(2);
    
    double num;

    private Quality(double num) {
        this.num = num;
    }

}
