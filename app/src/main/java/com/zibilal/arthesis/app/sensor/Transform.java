package com.zibilal.arthesis.app.sensor;

/**
 * Created by bmuhamm on 5/10/14.
 */
public class Transform {

    private float moveX;
    private float moveY;
    private float scaleX;
    private float scaleY;

    public Transform() {
        setMove(0, 0);
        setScale(1, 0);
    }

    public void setMove(float x, float y) {
        moveX = x;
        moveY = y;
    }

    public void setScale(float x, float y) {
        scaleX = x;
        scaleY = y;
    }

    public Pointf transform(Pointf p) {
        Pointf pointf = new Pointf();
        pointf.x = moveX + scaleX * p.x;
        pointf.y = moveY + scaleY * p.y;
        return pointf;
    }

    public Pointf rotate(Pointf po, double angle) {
        double rad = Math.toRadians(angle);
        double cosa = Math.cos(rad);
        double sina = Math.sin(rad);

        Pointf p = new Pointf();
        p.x = (float) (po.x * cosa - po.y * sina);
        p.y = (float) (po.x * sina + po.y * cosa);
        return p;
    }
}
