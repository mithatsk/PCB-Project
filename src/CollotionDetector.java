/**
 * Created by aidgha14 on 2017-05-09.
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.io.IOException;

public class CollotionDetector {

    public boolean inside(int x, int y, Rectangle rect) {
        return (x > rect.x && x < rect.x+rect.width) &&
                (y > rect.y && y < rect.y+rect.height);
    }

    public  boolean hitCheck(int x, int y, Rectangle rect) {
        return (x >= rect.x && x <= rect.x+rect.width) &&
                (y >= rect.y && y <= rect.y+rect.height);
    }

    public boolean collisionCheck(Rectangle rect1, Rectangle rect2) {
        return hitCheck(rect1.x, rect1.y, rect2) ||
                hitCheck(rect1.x+rect1.width, rect1.y, rect2) ||
                hitCheck(rect1.x, rect1.y+rect1.height, rect2) ||
                hitCheck(rect1.x+rect1.width,
                        rect1.y+rect1.height, rect2);
    }
}
