package main;

import java.util.List;

public interface DotDAO {
    List<Dot> getDots();
    Dot getDotById(int id);
    void addDot(Dot dot);
    void addDots(List<Dot> dotList);
    void deleteDotById(int id);
}
