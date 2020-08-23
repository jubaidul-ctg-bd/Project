#include<windows.h>
#include <GL/glut.h>
#include<math.h>
void init2D(float r, float g, float b)
{
glClearColor(r,g,b,.0);
glMatrixMode (GL_PROJECTION);
gluOrtho2D (-10, 90.0, -20, 80.0);
}

void display(void)
{
glClear(GL_COLOR_BUFFER_BIT);
//glColor3f(0.0, 0.52156, 0.247058);

glBegin(GL_QUADS);
glColor3f(.007843,.309803,.63529);
glVertex2f(10,10);
glVertex2f(80,10);
glVertex2f(80,15);
glVertex2f(10,15);

glColor3f(1,1,1);
glVertex2f(80,15);
glVertex2f(10,15);
glVertex2f(10,17);
glVertex2f(80,17);


glColor3f(.9294,.1098,.15294);
glVertex2f(10,17);
glVertex2f(80,17);
glVertex2f(80,28);
glVertex2f(10,28);


glColor3f(1,1,1);
glVertex2f(10,28);
glVertex2f(10,30);
glVertex2f(80,30);
glVertex2f(80,28);


glColor3f(.007843,.309803,.63529);
glVertex2f(10,30);
glVertex2f(10,35);
glVertex2f(80,35);
glVertex2f(80,30);

glEnd();

glBegin(GL_POLYGON);


float theta;
for(int i=0; i<360; i++)
{
theta = i*3.1416/180;
glVertex2f(32+5*cos(theta),22.5+5*sin(theta));
}
glEnd();
glBegin(GL_QUADS);
glColor3f(1,1,1);

glVertex2f(30.15,21.7);
glVertex2f(33.33,21.7);
glVertex2f(35.9,23.5);
glVertex2f(27.8,23.5);

glVertex2f(31.84,26.75);
glVertex2f(29.2,18.8);
glVertex2f(31.33,20.8);
glVertex2f(32.9,23.5);

glVertex2f(27.8,23.5);
glVertex2f(30.7,23.5);
glVertex2f(33.55,21.85);
glVertex2f(34.05,18.8);
glEnd();




glFlush();
}

int main(int argc,char *argv[])
{
glutInit(&argc,argv);
glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);
glutInitWindowSize (800, 750);
glutInitWindowPosition (50, 10);
glutCreateWindow ("Solomon Islands");
init2D(0,0,0);
glutDisplayFunc(display);
glutMainLoop();
return 0;
}

