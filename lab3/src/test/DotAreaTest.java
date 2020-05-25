package test;

import org.junit.Test;

import static main.DotDAOImpl.isHit;

public class DotAreaTest {
    @Test
    public void testIsHit() {
        assert isHit(1, 2, 3);
        assert !isHit(-4, 2, 4);
        assert !isHit(-1, -3, 2);
        assert !isHit(3, -1, 1);
        assert isHit(-1, 0, 1);
        assert isHit(0, -1, 1);
        assert isHit(-1,2,3);
        assert isHit(0,-0.5,2);
        assert isHit(-0.5,0,3);
        assert !isHit(-11,2,3);
        assert !isHit(18,-4,3);
        assert !isHit(-91,-2,3);
        assert !isHit(100,292,3);
        assert isHit(-1,2,9);
        assert isHit(0,-1,1);
        assert isHit(0,-1.0000000001,3);
        assert isHit(-0.5,-0.5,1);
        assert isHit(0.5,0,1);
        assert isHit(0,2,2);
        assert isHit(1.5,3,3);
        assert !isHit(1.500000000001,3,3);
        assert isHit(0.5,0.5,1);
        assert !isHit(0.6,0.5,1);
        assert isHit(0.5,2,2);
        assert isHit(0,0.5,1);
        assert !isHit(0.500000001,1.0000000000000000001,1);
        assert isHit(0,0,1);
        assert !isHit(-0.5,1,1);
        assert !isHit(-1,0.5,1);
        assert isHit(-1,1,3);
        assert isHit(-0.25,-0.25,2);
        assert isHit(0.49,0.5,3);
        assert !isHit(0.5, -0.001, 1);
        assert !isHit(0.5000000002, 0, 1);
        assert !isHit(0.5, 1.01, 1);
        assert !isHit(0.501, 1, 1);
        assert isHit(-0.5, Math.sqrt(0.75), 1);
        assert !isHit(0.0000000000001, -1.0000000001, 1);
        assert !isHit(-1.000000000001, 0, 1);
        assert !isHit(-1.000000000001, -0.00000000001, 1);
    }
}
