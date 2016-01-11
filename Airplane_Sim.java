/******************************************/
//      name: Alexander Jurcau             //
//      Description: A flight simulator.   //
/******************************************/

// importing libraries
import java.awt.*;
import hsa.Console;
public class AlexsAirplane // class name
{
    // screen and other constants
    static Console c; // The output console
    static Console d; // The output console 2
    static final int screenX = 640; // x value of console size
    static final int screenY = 500; // y value of console size
    static final double scaleX = +1;
    static final double shiftX = screenX / 2;
    static final double scaleY = -1;
    static final double shiftY = screenY / 3;
    static final double ZV = 350; // changes perspective
    static final int DELAY = 30;
    static final double alpha = 0.04; // how much the plane turns when you press one of the WASD buttons

    //constants for all objects in the program
    static final double L = 1000; // length most objects are based upon (keep at 1000)
    static final double cOffset = -4000; // how far away the obstacle is from the starting point
    static final double rOffset = -1000; // how far away the runway is from the starting point
    static final double rHeight = 235; // how high the runway is
    static final int mountainPoints = 17; // the total number of points for the mountain (odd number for regular mountain shape)
    static final double dX = 3000; // distance between bottom points for the mountain
    static final double mOffset = -20000; // how far away the mountain is from the starting point
    static final double mHigh = 9 * L; // the highest point of the mountain
    static final double mLow = -L;   // the lowest point of the mountain
    static final int n = 5; //number of sides on the polygon
    static final double polyR = 300; //radius of the polygon
    static final int polyN = 3; // number of polygons
    static final double pOffest = 50;
    static final Color primaryC = Color.black;
    static final Color secondaryC = Color.blue;
    static final double velocity = 15;
    static final double infinity = 10e3;

    //constants for all the controls
    static final char UP = 'w';
    static final char UP_CAPS = 'W';
    static final char DOWN = 's';
    static final char DOWN_CAPS = 'S';
    static final char LEFT = 'a';
    static final char LEFT_CAPS = 'A';
    static final char RIGHT = 'd';
    static final char RIGHT_CAPS = 'D';
    static final char STOP = 'f';
    static final char STOP_CAPS = 'F';
    static final char SPEED_UP = 'e';
    static final char SPEED_UP_CAPS = 'E';
    static final char SPEED_DOWN = 'r';
    static final char SPEED_DOWN_CAPS = 'R';
    static final char SUPER_SPEED = ' ';
    static final char PAUSE = 'p';
    static final char PAUSE_CAPS = 'P';

    public static void main (String[] args)  // main program
    {
        c = new Console (); // simulator console    
        d = new Console (); // instructions console
        AlexsAirplane s = new AlexsAirplane (); // new object
        
        double theta, phi, psi; // rotation angles
        int speedup = 0,  // used for speed up/down buttons
            POLYGONi = 1;  // used for drawing polygons one after the other
        boolean fast = false, slow = false;

        // Coordinates for the obstacle
        double obstaclex0[] = new double [8];
        double obstacley0[] = new double [8];
        double obstaclez0[] = new double [8];
        double obstaclex[] = new double [8];
        double obstacley[] = new double [8];
        double obstaclez[] = new double [8];

        // Coordinates for the second obstacle
        double obstacle2x0[] = new double [8];
        double obstacle2y0[] = new double [8];
        double obstacle2z0[] = new double [8];
        double obstacle2x[] = new double [8];
        double obstacle2y[] = new double [8];
        double obstacle2z[] = new double [8];

        // Coordinates for the road
        double roadx0[] = new double [4];
        double roady0[] = new double [4];
        double roadz0[] = new double [4];
        double roadx[] = new double [4];
        double roady[] = new double [4];
        double roadz[] = new double [4];

        // Coordinates for the road lines
        double rlinesx0[] = new double [8];
        double rlinesy0[] = new double [8];
        double rlinesz0[] = new double [8];
        double rlinesx[] = new double [8];
        double rlinesy[] = new double [8];
        double rlinesz[] = new double [8];

        // Coordinates for the mountain
        double mountx0[] = new double [mountainPoints];
        double mounty0[] = new double [mountainPoints];
        double mountz0[] = new double [mountainPoints];
        double mountx[] = new double [mountainPoints];
        double mounty[] = new double [mountainPoints];
        double mountz[] = new double [mountainPoints];
        double peak_counter = 0;

        // Coordinates for the polygons
        double polyx0[] [] = new double [polyN] [n];
        double polyy0[] [] = new double [polyN] [n];
        double polyx[] [] = new double [polyN] [n];
        double polyy[] [] = new double [polyN] [n];

        // Other variables (non-constant and non-array variables)
        double t = 0; //time
        char key; //user input
        theta = phi = psi = 0; // rotation angles

        s.instructions (); // instructions that are shown right when the program is ran on the other console
        d.println ("Press any key to continue:");
        d.getChar (); // for the user to take their time to read instructions

        // setting starting positions of every object
        s.setObstacleCoordinates (obstaclex0, obstacley0, obstaclez0);
        s.setObstacle2Coordinates (obstacle2x0, obstacle2y0, obstacle2z0);
        s.setRunwayCoordinates (roadx0, roady0, roadz0);
        s.setRoadLinesCoordinates (rlinesx0, rlinesy0, rlinesz0);
        s.setMountainCoordinates (mountx0, mounty0, mountz0);
        s.setPolygonCoordinates (polyx0, polyy0);

        while (true) // main loop
        {
            if (c.isCharAvail ()) // checking for user input
            {
                key = c.getChar (); // checking for which key was pressed
                if (key == UP || key == UP_CAPS) // airplane moving up
                {
                    s.obstacleMovement (obstaclex0, obstacley0, obstaclez0, obstaclex, obstacley, obstaclez, alpha, 0);
                    s.obstacle2Movement (obstacle2x0, obstacle2y0, obstacle2z0, obstacle2x, obstacle2y, obstacle2z, alpha, 0);
                    s.runwayMovement (roadx0, roady0, roadz0, roadx, roady, roadz, alpha, 0);
                    s.roadLinesMovement (rlinesx0, rlinesy0, rlinesz0, rlinesx, rlinesy, rlinesz, alpha, 0);
                    s.mountainMovement (mountx0, mounty0, mountz0, mountx, mounty, mountz, alpha, 0);
                    t = 0;
                }
                else if (key == DOWN || key == DOWN_CAPS) // airplane moving down
                {
                    s.obstacleMovement (obstaclex0, obstacley0, obstaclez0, obstaclex, obstacley, obstaclez, -alpha, 0);
                    s.obstacle2Movement (obstacle2x0, obstacle2y0, obstacle2z0, obstacle2x, obstacle2y, obstacle2z, -alpha, 0);
                    s.runwayMovement (roadx0, roady0, roadz0, roadx, roady, roadz, -alpha, 0);
                    s.roadLinesMovement (rlinesx0, rlinesy0, rlinesz0, rlinesx, rlinesy, rlinesz, -alpha, 0);
                    s.mountainMovement (mountx0, mounty0, mountz0, mountx, mounty, mountz, -alpha, 0);
                    t = 0;
                }
                else if (key == LEFT || key == LEFT_CAPS) // airplane moving left
                {
                    s.obstacleMovement (obstaclex0, obstacley0, obstaclez0, obstaclex, obstacley, obstaclez, 0, -alpha);
                    s.obstacle2Movement (obstacle2x0, obstacle2y0, obstacle2z0, obstacle2x, obstacle2y, obstacle2z, 0, -alpha);
                    s.runwayMovement (roadx0, roady0, roadz0, roadx, roady, roadz, 0, -alpha);
                    s.roadLinesMovement (rlinesx0, rlinesy0, rlinesz0, rlinesx, rlinesy, rlinesz, 0, -alpha);
                    s.mountainMovement (mountx0, mounty0, mountz0, mountx, mounty, mountz, 0, -alpha);
                    t = 0;
                }
                else if (key == RIGHT || key == RIGHT_CAPS) // airplane moving right
                {
                    s.obstacleMovement (obstaclex0, obstacley0, obstaclez0, obstaclex, obstacley, obstaclez, 0, alpha);
                    s.obstacle2Movement (obstacle2x0, obstacle2y0, obstacle2z0, obstacle2x, obstacle2y, obstacle2z, 0, alpha);
                    s.runwayMovement (roadx0, roady0, roadz0, roadx, roady, roadz, 0, alpha);
                    s.roadLinesMovement (rlinesx0, rlinesy0, rlinesz0, rlinesx, rlinesy, rlinesz, 0, alpha);
                    s.mountainMovement (mountx0, mounty0, mountz0, mountx, mounty, mountz, 0, alpha);
                    t = 0;
                }
                if (key == STOP || key == STOP_CAPS) // airplane stopping all movement
                {
                    s.obstacleMovement (obstaclex0, obstacley0, obstaclez0, obstaclex, obstacley, obstaclez, 0, 0);
                    s.obstacle2Movement (obstacle2x0, obstacle2y0, obstacle2z0, obstacle2x, obstacle2y, obstacle2z, 0, 0);
                    s.runwayMovement (roadx0, roady0, roadz0, roadx, roady, roadz, 0, 0);
                    s.roadLinesMovement (rlinesx0, rlinesy0, rlinesz0, rlinesx, rlinesy, rlinesz, 0, 0);
                    s.mountainMovement (mountx0, mounty0, mountz0, mountx, mounty, mountz, 0, 0);
                    t = 0;
                    c.clear ();
                    s.drawObstacle (obstaclex, obstacley, obstaclez);
                    s.drawObstacle (obstacle2x, obstacle2y, obstacle2z);
                    s.drawRunway (roadx, roady, roadz);
                    s.drawRoadLines (rlinesx, rlinesy, rlinesz);
                    s.drawMountain (mountx, mounty, mountz);
                    c.getChar ();
                }
                else if (key == SPEED_UP || key == SPEED_UP_CAPS) // fast motion, decreasing delay
                {
                    if (speedup == 0) // if speed is normal, make it faster
                    {
                        speedup = -20;
                        fast = true;
                        d.clear ();
                        s.instructions ();
                        d.print ("Plane Status: FAST MOTION ACTIVATED");
                    }
                    else // if speed is not normal, make it normal
                    {
                        speedup = 0;
                        fast = false;
                        d.clear ();
                        s.instructions ();
                        d.print ("Plane Status: Normal Speed");
                    }
                }
                else if (key == SPEED_DOWN || key == SPEED_DOWN_CAPS) // slow motion, increasing delay
                {
                    if (speedup == 0) // if speed is normal, make it slower
                    {
                        speedup = 20;
                        slow = true;
                        d.clear ();
                        s.instructions ();
                        d.print ("Plane Status: SLOW MOTION ACTIVATED");
                    }
                    else // if speed is not normal, make it normal
                    {
                        speedup = 0;
                        slow = false;
                        d.clear ();
                        s.instructions ();
                        d.print ("Plane Status: Normal");
                    }
                }
                else if (key == SUPER_SPEED) // super speed, decreasing delay more than fast motion
                {
                    if (speedup == 0) // if speed is normal, make it faster
                    {
                        speedup = -DELAY + 4;
                        slow = true;
                        d.clear ();
                        s.instructions ();
                        d.print ("Plane Status: SUPER SPEED ACTIVATED");
                    }
                    else // if speed is not normal, make it normal
                    {
                        speedup = 0;
                        slow = false;
                        d.clear ();
                        s.instructions ();
                        d.print ("Plane Status: Normal");
                    }
                }
                else if (key == PAUSE || key == PAUSE_CAPS) // a seperate pause screen
                {
                    c.clear ();
                    c.println ();
                    c.println ("                                     -PAUSED-");
                    c.println ("                             Press any key to unpause.");
                    c.getChar ();
                }
            }
            // drawing all objects after checking for input
            c.clear ();
            s.drawObstacle (obstaclex, obstacley, obstaclez);
            s.drawObstacle (obstacle2x, obstacle2y, obstacle2z);
            s.drawRunway (roadx, roady, roadz);
            s.drawRoadLines (rlinesx, rlinesy, rlinesz);
            s.drawMountain (mountx, mounty, mountz);
            s.drawPolygon (polyx0, polyy0, POLYGONi);
            delay (DELAY + speedup);
            s.zMovement (obstaclex0, obstacley0, obstaclez0,
                    obstaclex, obstacley, obstaclez,
                    obstacle2x0, obstacle2y0, obstacle2z0,
                    obstacle2x, obstacle2y, obstacle2z,
                    roadx0, roady0, roadz0,
                    roadx, roady, roadz,
                    rlinesx0, rlinesy0, rlinesz0,
                    rlinesx, rlinesy, rlinesz,
                    mountx0, mounty0, mountz0,
                    mountx, mounty, mountz,
                    t);
            t += 1;
            POLYGONi++;
            if (POLYGONi == polyN)
                POLYGONi = 0;
        }
    } // main method


    public void instructions ()
    {
        d.println ("Welcome to Alexander Jurcau's Flight Simulator!");
        d.println ();
        d.println ("Controls:");
        d.println ();
        d.println ("W - UP");
        d.println ("S - DOWN");
        d.println ("A - LEFT");
        d.println ("D - RIGHT");
        d.println ("F - STOP");
        d.println ("E - FAST MOTION");
        d.println ("R - SLOW MOTION");
        d.println ("SPACE BAR - SUPER SPEED");
        d.println ("P - PAUSE");
        d.println ();
        d.println ("Objective: Go over the first obstacle, under the second one and over the mountains. Then turn around and go through the course again and land the plane on the runway.");
        d.println ();
        d.println ("After reading please move this window to the side to view the simulator.");
        d.println ();
    }


    public void setObstacleCoordinates (double obstaclex0[], double obstacley0[], double obstaclez0[])
    {
        obstaclex0 [0] = +L * 6;
        obstacley0 [0] = +L - L;
        obstaclez0 [0] = +L + cOffset - L * 2;
        obstaclex0 [1] = -L * 6;
        obstacley0 [1] = -L - L;
        obstaclez0 [1] = -L + cOffset- L * 2;
        obstaclex0 [2] = -L * 6;
        obstacley0 [2] = +L - L;
        obstaclez0 [2] = +L + cOffset - L * 2;
        obstaclex0 [3] = +L * 6;
        obstacley0 [3] = -L - L;
        obstaclez0 [3] = -L + cOffset- L * 2;
        obstaclex0 [4] = -L * 6;
        obstacley0 [4] = -L - L;
        obstaclez0 [4] = +L + cOffset - L * 2;
        obstaclex0 [5] = +L * 6;
        obstacley0 [5] = +L - L;
        obstaclez0 [5] = -L + cOffset- L * 2;
        obstaclex0 [6] = +L * 6;
        obstacley0 [6] = -L - L;
        obstaclez0 [6] = +L + cOffset - L * 2;
        obstaclex0 [7] = -L * 6;
        obstacley0 [7] = +L - L;
        obstaclez0 [7] = -L + cOffset- L * 2;
    }


    public void setObstacle2Coordinates (double obstacle2x0[], double obstacle2y0[], double obstacle2z0[])
    {
        obstacle2x0 [0] = +L * 6;
        obstacle2y0 [0] = +L * 6;
        obstacle2z0 [0] = +L + 3 * cOffset - L / 2;
        obstacle2x0 [1] = -L * 6;
        obstacle2y0 [1] = -L + 3 * L;
        obstacle2z0 [1] = -L + 2 * cOffset;
        obstacle2x0 [2] = -L * 6;
        obstacle2y0 [2] = +L * 6;
        obstacle2z0 [2] = +L + 3 * cOffset - L / 2;
        obstacle2x0 [3] = +L * 6;
        obstacle2y0 [3] = -L + 3 * L;
        obstacle2z0 [3] = -L + 2 * cOffset;
        obstacle2x0 [4] = -L * 6;
        obstacle2y0 [4] = -L + 3 * L;
        obstacle2z0 [4] = +L + 3 * cOffset - L / 2;
        obstacle2x0 [5] = +L * 6;
        obstacle2y0 [5] = +L * 6;
        obstacle2z0 [5] = -L + 2 * cOffset;
        obstacle2x0 [6] = +L * 6;
        obstacle2y0 [6] = -L + 3 * L;
        obstacle2z0 [6] = +L + 3 * cOffset - L / 2;
        obstacle2x0 [7] = -L * 6;
        obstacle2y0 [7] = +L * 6;
        obstacle2z0 [7] = -L + 2 * cOffset;
    }


    public void setRunwayCoordinates (double roadx0[], double roady0[], double roadz0[])
    {
        roadx0 [0] = +L / 3;
        roady0 [0] = -L + rHeight;
        roadz0 [0] = -L + rOffset;
        roadx0 [1] = -L / 3;
        roady0 [1] = -L + rHeight;
        roadz0 [1] = -L + rOffset;
        roadx0 [2] = +L / 3;
        roady0 [2] = -L + rHeight;
        roadz0 [2] = (+L + rOffset / -10) / 2 + rOffset;
        roadx0 [3] = -L / 3;
        roady0 [3] = -L + rHeight;
        roadz0 [3] = (+L + rOffset / -10) / 2 + rOffset;
    }


    public static void setRoadLinesCoordinates (double rlinesx0[], double rlinesy0[], double rlinesz0[])
    {
        rlinesx0 [0] = (+L / 3.0 + - L / 3.0) / 2.0 - 100;                 //centre of the runway
        rlinesy0 [0] = -L + rHeight;                                      //elevation of the road
        rlinesz0 [0] = (+L + rOffset / -10.0) / 2 + rOffset;
        rlinesx0 [1] = (+L / 3.0 + - L / 3.0) / 2.0 - 100;
        rlinesy0 [1] = -L + rHeight;
        rlinesz0 [1] = -L + rOffset;
        rlinesx0 [2] = (+L / 3.0 + - L / 3.0) / 2.0 + 100;
        rlinesy0 [2] = -L + rHeight;
        rlinesz0 [2] = (+L + rOffset / -10.0) / 2 + rOffset;
        rlinesx0 [3] = (+L / 3.0 + - L / 3.0) / 2.0 + 100;
        rlinesy0 [3] = -L + rHeight;
        rlinesz0 [3] = -L + rOffset;
        rlinesx0 [4] = (+L / 3.0 + - L / 3.0) / 2.0 + 50;
        rlinesy0 [4] = -L + rHeight;
        rlinesz0 [4] = (+L + rOffset / -10.0) / 2 + rOffset;
        rlinesx0 [5] = (+L / 3.0 + - L / 3.0) / 2.0 + 50;
        rlinesy0 [5] = -L + rHeight;
        rlinesz0 [5] = -L + rOffset;
        rlinesx0 [6] = (+L / 3.0 + - L / 3.0) / 2.0 - 50;
        rlinesy0 [6] = -L + rHeight;
        rlinesz0 [6] = (+L + rOffset / -10.0) / 2 + rOffset;
        rlinesx0 [7] = (+L / 3.0 + - L / 3.0) / 2.0 - 50;
        rlinesy0 [7] = -L + rHeight;
        rlinesz0 [7] = -L + rOffset;
    }


    public void setMountainCoordinates (double mountx0[], double mounty0[], double mountz0[])
    {
        for (int i = 0 ; i <= mountainPoints - 1 ; ++i)
        {
            if ((i % 2) > 0)
            {
                mounty0 [i] = mHigh;
            }
            else
                mounty0 [i] = mLow;
            mountx0 [i] = -L * 20 + dX * i;
            mountz0 [i] = mOffset;
        }
    }


    public static void setPolygonCoordinates (double polyx0[] [], double polyy0[] [])
    {
        for (int i = 0 ; i <= polyN - 1 ; ++i)
        {
            for (int j = 0 ; j <= n - 1 ; ++j)
            {
                polyx0 [i] [j] = (polyR + pOffest * i) * Math.cos (2 * Math.PI * j / n + 180);
                polyy0 [i] [j] = (polyR + pOffest * i) * Math.sin (2 * Math.PI * j / n + 180);
            }
        }
    }


    public static void drawObstacle (double x[], double y[], double z[])
    {
        c.setColor (primaryC);
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [0], z [0]))),
                (int) Math.round (ScaleShiftY (perspective (y [0], z [0]))),
                (int) Math.round (ScaleShiftX (perspective (x [2], z [2]))),
                (int) Math.round (ScaleShiftY (perspective (y [2], z [2]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [0], z [0]))),
                (int) Math.round (ScaleShiftY (perspective (y [0], z [0]))),
                (int) Math.round (ScaleShiftX (perspective (x [5], z [5]))),
                (int) Math.round (ScaleShiftY (perspective (y [5], z [5]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [0], z [0]))),
                (int) Math.round (ScaleShiftY (perspective (y [0], z [0]))),
                (int) Math.round (ScaleShiftX (perspective (x [6], z [6]))),
                (int) Math.round (ScaleShiftY (perspective (y [6], z [6]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [1], z [1]))),
                (int) Math.round (ScaleShiftY (perspective (y [1], z [1]))),
                (int) Math.round (ScaleShiftX (perspective (x [3], z [3]))),
                (int) Math.round (ScaleShiftY (perspective (y [3], z [3]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [1], z [1]))),
                (int) Math.round (ScaleShiftY (perspective (y [1], z [1]))),
                (int) Math.round (ScaleShiftX (perspective (x [4], z [4]))),
                (int) Math.round (ScaleShiftY (perspective (y [4], z [4]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [1], z [1]))),
                (int) Math.round (ScaleShiftY (perspective (y [1], z [1]))),
                (int) Math.round (ScaleShiftX (perspective (x [7], z [7]))),
                (int) Math.round (ScaleShiftY (perspective (y [7], z [7]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [2], z [2]))),
                (int) Math.round (ScaleShiftY (perspective (y [2], z [2]))),
                (int) Math.round (ScaleShiftX (perspective (x [4], z [4]))),
                (int) Math.round (ScaleShiftY (perspective (y [4], z [4]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [2], z [2]))),
                (int) Math.round (ScaleShiftY (perspective (y [2], z [2]))),
                (int) Math.round (ScaleShiftX (perspective (x [7], z [7]))),
                (int) Math.round (ScaleShiftY (perspective (y [7], z [7]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [3], z [3]))),
                (int) Math.round (ScaleShiftY (perspective (y [3], z [3]))),
                (int) Math.round (ScaleShiftX (perspective (x [5], z [5]))),
                (int) Math.round (ScaleShiftY (perspective (y [5], z [5]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [3], z [3]))),
                (int) Math.round (ScaleShiftY (perspective (y [3], z [3]))),
                (int) Math.round (ScaleShiftX (perspective (x [6], z [6]))),
                (int) Math.round (ScaleShiftY (perspective (y [6], z [6]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [4], z [4]))),
                (int) Math.round (ScaleShiftY (perspective (y [4], z [4]))),
                (int) Math.round (ScaleShiftX (perspective (x [6], z [6]))),
                (int) Math.round (ScaleShiftY (perspective (y [6], z [6]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [5], z [5]))),
                (int) Math.round (ScaleShiftY (perspective (y [5], z [5]))),
                (int) Math.round (ScaleShiftX (perspective (x [7], z [7]))),
                (int) Math.round (ScaleShiftY (perspective (y [7], z [7]))));
    }


    public static void drawRunway (double x[], double y[], double z[])
    {
        c.setColor (primaryC);
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [0], z [0]))),
                (int) Math.round (ScaleShiftY (perspective (y [0], z [0]))),
                (int) Math.round (ScaleShiftX (perspective (x [1], z [1]))),
                (int) Math.round (ScaleShiftY (perspective (y [1], z [1]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [0], z [0]))),
                (int) Math.round (ScaleShiftY (perspective (y [0], z [0]))),
                (int) Math.round (ScaleShiftX (perspective (x [2], z [2]))),
                (int) Math.round (ScaleShiftY (perspective (y [2], z [2]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [2], z [2]))),
                (int) Math.round (ScaleShiftY (perspective (y [2], z [2]))),
                (int) Math.round (ScaleShiftX (perspective (x [3], z [3]))),
                (int) Math.round (ScaleShiftY (perspective (y [3], z [3]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [3], z [3]))),
                (int) Math.round (ScaleShiftY (perspective (y [3], z [3]))),
                (int) Math.round (ScaleShiftX (perspective (x [1], z [1]))),
                (int) Math.round (ScaleShiftY (perspective (y [1], z [1]))));
    }


    public static void drawRoadLines (double x[], double y[], double z[])
    {
        c.setColor (primaryC);
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [0], z [0]))),
                (int) Math.round (ScaleShiftY (perspective (y [0], z [0]))),
                (int) Math.round (ScaleShiftX (perspective (x [1], z [1]))),
                (int) Math.round (ScaleShiftY (perspective (y [1], z [1]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [2], z [2]))),
                (int) Math.round (ScaleShiftY (perspective (y [2], z [2]))),
                (int) Math.round (ScaleShiftX (perspective (x [3], z [3]))),
                (int) Math.round (ScaleShiftY (perspective (y [3], z [3]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [4], z [4]))),
                (int) Math.round (ScaleShiftY (perspective (y [4], z [4]))),
                (int) Math.round (ScaleShiftX (perspective (x [5], z [5]))),
                (int) Math.round (ScaleShiftY (perspective (y [5], z [5]))));
        c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [6], z [6]))),
                (int) Math.round (ScaleShiftY (perspective (y [6], z [6]))),
                (int) Math.round (ScaleShiftX (perspective (x [7], z [7]))),
                (int) Math.round (ScaleShiftY (perspective (y [7], z [7]))));
    }


    public void drawMountain (double x[], double y[], double z[])
    {
        for (int i = 0 ; i <= mountainPoints - 2 ; ++i)
        {
            c.setColor (primaryC);
            c.drawLine ((int) Math.round (ScaleShiftX (perspective (x [i], z [i]))),
                    (int) Math.round (ScaleShiftY (perspective (y [i], z [i]))),
                    (int) Math.round (ScaleShiftX (perspective (x [i + 1], z [i + 1]))),
                    (int) Math.round (ScaleShiftY (perspective (y [i + 1], z [i + 1]))));
        }
    }


    public static void drawPolygon (double x[] [], double y[] [], int POLYGONi)
    {
        c.setColor (secondaryC);
        int i = POLYGONi;
        for (int j = 0 ; j <= n - 1 ; ++j)
        {
            c.drawLine ((int) ScaleShiftX (Math.round (x [i] [j % n])),
                    (int) ScaleShiftY (Math.round (y [i] [j % n])) + 80,
                    (int) ScaleShiftX (Math.round (x [i] [(j + 1) % n])),
                    (int) ScaleShiftY (Math.round ((y [i] [(j + 1) % n]))) + 80);
        }
        c.setColor (primaryC);
    }


    static void obstacleMovement (double x0[], double y0[], double z0[], double x[], double y[], double z[], double alpha, double beta)
    {
        for (int i = 0 ; i <= 7 ; ++i)
        {
            x0 [i] = Math.cos (beta) * x [i] + Math.sin (beta) * z [i];
            y0 [i] = -Math.sin (alpha) * Math.sin (beta) * x [i] + Math.cos (alpha) * y [i] + Math.sin (alpha) * Math.cos (beta) * z [i];
            z0 [i] = -Math.cos (alpha) * Math.sin (beta) * x [i] - Math.sin (alpha) * y [i] + Math.cos (alpha) * Math.cos (beta) * z [i];
        }
    }


    static void obstacle2Movement (double x0[], double y0[], double z0[], double x[], double y[], double z[], double alpha, double beta)
    {
        for (int i = 0 ; i <= 7 ; ++i)
        {
            x0 [i] = Math.cos (beta) * x [i] + Math.sin (beta) * z [i];
            y0 [i] = -Math.sin (alpha) * Math.sin (beta) * x [i] + Math.cos (alpha) * y [i] + Math.sin (alpha) * Math.cos (beta) * z [i];
            z0 [i] = -Math.cos (alpha) * Math.sin (beta) * x [i] - Math.sin (alpha) * y [i] + Math.cos (alpha) * Math.cos (beta) * z [i];
        }
    }


    static void runwayMovement (double x0[], double y0[], double z0[], double x[], double y[], double z[], double alpha, double beta)
    {
        for (int i = 0 ; i <= 3 ; ++i)
        {
            x0 [i] = Math.cos (beta) * x [i] + Math.sin (beta) * z [i];
            y0 [i] = -Math.sin (alpha) * Math.sin (beta) * x [i] + Math.cos (alpha) * y [i] + Math.sin (alpha) * Math.cos (beta) * z [i];
            z0 [i] = -Math.cos (alpha) * Math.sin (beta) * x [i] - Math.sin (alpha) * y [i] + Math.cos (alpha) * Math.cos (beta) * z [i];
        }
    }


    static void roadLinesMovement (double x0[], double y0[], double z0[], double x[], double y[], double z[], double alpha, double beta)
    {
        for (int i = 0 ; i <= 7 ; ++i)
        {
            x0 [i] = Math.cos (beta) * x [i] + Math.sin (beta) * z [i];
            y0 [i] = -Math.sin (alpha) * Math.sin (beta) * x [i] + Math.cos (alpha) * y [i] + Math.sin (alpha) * Math.cos (beta) * z [i];
            z0 [i] = -Math.cos (alpha) * Math.sin (beta) * x [i] - Math.sin (alpha) * y [i] + Math.cos (alpha) * Math.cos (beta) * z [i];
        }
    }


    static void mountainMovement (double x0[], double y0[], double z0[], double x[], double y[], double z[], double alpha, double beta)
    {
        for (int i = 0 ; i <= mountainPoints - 1 ; ++i)
        {
            x0 [i] = Math.cos (beta) * x [i] + Math.sin (beta) * z [i];
            y0 [i] = -Math.sin (alpha) * Math.sin (beta) * x [i] + Math.cos (alpha) * y [i] + Math.sin (alpha) * Math.cos (beta) * z [i];
            z0 [i] = -Math.cos (alpha) * Math.sin (beta) * x [i] - Math.sin (alpha) * y [i] + Math.cos (alpha) * Math.cos (beta) * z [i];
        }
    }


    static void zMovement (double obstaclex0[], double obstacley0[], double obstaclez0[],
            double obstaclex[], double obstacley[], double obstaclez[],
            double obstacle2x0[], double obstacle2y0[], double obstacle2z0[],
            double obstacle2x[], double obstacle2y[], double obstacle2z[],
            double roadx0[], double roady0[], double roadz0[],
            double roadx[], double roady[], double roadz[],
            double rlinesx0[], double rlinesy0[], double rlinesz0[],
            double rlinesx[], double rlinesy[], double rlinesz[],           
            double mountx0[], double mounty0[], double mountz0[],
            double mountx[], double mounty[], double mountz[],            
            double t)
    {
        for (int i = 0 ; i <= 7 ; ++i) //for the obstacle coordinates
        {
            obstaclex [i] = obstaclex0 [i];
            obstacley [i] = obstacley0 [i];
            obstaclez [i] = obstaclez0 [i] + velocity * t;
        }
        for (int i = 0 ; i <= 7 ; ++i) //for the obstacle 2 coordinates
        {
            obstacle2x [i] = obstacle2x0 [i];
            obstacle2y [i] = obstacle2y0 [i];
            obstacle2z [i] = obstacle2z0 [i] + velocity * t;
        }
        for (int i = 0 ; i <= mountainPoints - 1 ; ++i) //for the mountain coordinates
        {
            mountx [i] = mountx0 [i];
            mounty [i] = mounty0 [i];
            mountz [i] = mountz0 [i] + velocity * t;
        }
        for (int i = 0 ; i <= 3 ; ++i)
        {
            roadx [i] = roadx0 [i];
            roady [i] = roady0 [i];
            roadz [i] = roadz0 [i] + velocity * t;
        }
        for (int i = 0 ; i <= 7 ; ++i)
        {
            rlinesx [i] = rlinesx0 [i];
            rlinesy [i] = rlinesy0 [i];
            rlinesz [i] = rlinesz0 [i] + velocity * t;
        }
    }


    public static double ScaleShiftX (double x)
    {
        return scaleX * x + shiftX;
    }


    public static double ScaleShiftY (double y)
    {
        return scaleY * y + shiftY;
    }


    public static double perspective (double n, double z)  // perspective for
    {
        if (z < ZV)
            return n / (1 - z / ZV);
        else
            return n * infinity; //rolling up to infinity
    }


    public static void delay (int timer)
    {
        try
        {
            Thread.sleep (timer);
        }
        catch (InterruptedException error)
        {
        }
    }
} // Simulator class
