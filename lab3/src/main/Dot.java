package main;

import javax.persistence.*;

@Entity
@Table(name = "dots", schema = "public")
public class Dot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(table = "dots", name = "x")
    private double x;

    @Column(table = "dots", name = "y")
    private double y;

    @Column(table = "dots", name = "r")
    private double r;

    @Column(table = "dots", name = "is_hit")
    private boolean is_hit;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public int getId() {
        return id;
    }

    public boolean ishit() {
        return is_hit;
    }

    public void setIs_hit(boolean is_hit) {
        this.is_hit = is_hit;
    }

    public Dot(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Dot(double x, double y, double r, boolean is_hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.is_hit = is_hit;
    }

    public Dot() {
    }

    public Dot(int id, double x, double y, double r) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public String toString() {
        return "Dot{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", is_hit=" + is_hit +
                '}';
    }
}
