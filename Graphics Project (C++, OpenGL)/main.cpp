#include<bits/stdc++.h>
#include <GL/glu.h>
#include <GL/glext.h>
#include "GL/glut.h"

#pragma once

struct point
{
	float x;
	float y;
	float z;
};


//-------- Functions --------------------------------
void Render();
// The function responsible for drawing everything in the
// OpenGL context associated to a window.

void Resize(int w, int h);
// Handle the window size changes and define the world coordinate
// system and projection type

void Setup();
// Set up the OpenGL state machine and create a light source

void Idle();

void Keyboard(unsigned char key, int x, int y);
// Function for handling keyboard events.


void MouseWheel(int button, int dir, int x, int y);
//functin for handling mouse whell event

void RandomCoordinates(point*);

void DrawStars();

static float rotx = 0.0;
static float worldX = 15.0;
static float worldY = 0.0;
static float scaleFactor = 0.6;
static bool animate = false;
point stars[500];

using namespace std;

void keimeno(string str, float size)
{

	glPushMatrix();
	glScalef(size, size, size);

	for (int i = 0; i<str.size(); i++)
		glutStrokeCharacter(GLUT_STROKE_ROMAN, str[i]);
	glPopMatrix();

}

void Render()
{
	//CLEARS FRAME BUFFER ie COLOR BUFFER& DEPTH BUFFER (1.0)
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);  // Clean up the colour of the window
														 // and the depth buffer
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();


	glTranslatef(0, 0, -100);

	if (!animate) {
		glPushMatrix();
		glDisable(GL_LIGHTING);
		glDisable(GL_LIGHT0);
		glColor3f(1, 1, 1);
		glTranslatef(-8, 50, 0.0);
		keimeno("Pause", 0.05f);
		glPopMatrix();
	}
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);

	GLUquadricObj *quadric;
	quadric = gluNewQuadric();
	gluQuadricDrawStyle(quadric, GLU_FILL);
	gluDeleteQuadric(quadric);

	glScalef(scaleFactor, scaleFactor, scaleFactor);

	glRotatef(worldX, 1, 0, 0);
	glRotatef(worldY, 0, 1, 0);

	glDisable(GL_LIGHTING);

	glPushMatrix();
	glRotatef(rotx, 0, 1, 0);
	glTranslatef(2, 0.0, 2);
	glRotatef(rotx, 0, 0, 1);
	glColor3f(0.8, 0.498039, 0.196078);
	gluSphere(quadric, 15, 36, 18);
	glColor3f(1, 1, 0);
	glEnable(GL_BLEND);
	gluSphere(quadric, 20, 36, 18);
	glDisable(GL_BLEND);
	glPopMatrix();

	glEnable(GL_LIGHTING);
	GLfloat light_position[] = { 0.0, 0.0, 0.0,1 };
	glLightfv(GL_LIGHT0, GL_POSITION, light_position);
	GLfloat light_diff[] = { 1.0, 1.0, 1.0, 1.0 };
	glLightfv(GL_LIGHT0, GL_DIFFUSE, light_diff);
	GLfloat light_amb[] = { 0.0, 0.0, 0.0, 1.0 };
	glLightfv(GL_LIGHT0, GL_AMBIENT, light_amb);


	glPushMatrix();// 1 planet
	glRotatef(rotx, 0, 1, 0);
	glTranslatef(-20, 0.0, -70);
	glRotatef(rotx, 0, 0, 1);
	glColor3f(1, 1, 1);
	gluSphere(quadric, 10, 36, 18);
	glPopMatrix();


	glPushMatrix();// 2 planet
	glRotatef(rotx, 0, 1, 0);
	glTranslatef(20, 0, 44.0);
	glRotatef(rotx, 1, 0, 0);
	glColor3f(1, 1, 1);
	gluSphere(quadric, 11, 36, 18);
	glPopMatrix();

	DrawStars();


	glutSwapBuffers();
}

//-----------------------------------------------------------

void Resize(int w, int h)
{
	// define the visible area of the window ( in pixels )
	if (h == 0) h = 1;
	glViewport(0, 0, w, h);

	// Setup viewing volume
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	gluPerspective(60.0, (float)w / (float)h, 1.0, 1000.0);
}

void Idle()
{
	if (animate)
		rotx += 1.1;

	glutPostRedisplay();
}

void Keyboard(unsigned char key, int x, int y)
{
	switch (key)
	{
	case 'q': exit(0);
		break;
	case 'w': if (animate) worldX -= 1.0f;
		break;
	case 's': if (animate) worldX += 1.0f;
		break;
	case 'a': if (animate) worldY -= 1.0f;
		break;
	case 'd': if (animate)  worldY += 1.0f;
		break;
	case ' ': animate = !animate;;
		break;
	default: break;
	}
	glutPostRedisplay();

}



void Setup()  // TOUCH IT !!
{

	//get random cordinates for the stars
	for (int i = 0; i < 500; i++)
		RandomCoordinates(&stars[i]);
	srand(time(0));
	//Parameter handling
	glShadeModel(GL_SMOOTH);
	glEnable(GL_DEPTH_TEST);

	// polygon rendering mode
	glEnable(GL_COLOR_MATERIAL);
	glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

	// Black background
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
}

void RandomCoordinates(point *star)
{

	int lowest = -1000, highest = 1000;
	int range = (highest - lowest) + 1;
	star->x = lowest + int(range*rand() / (RAND_MAX + 1.0));
	star->y = lowest + int(range*rand() / (RAND_MAX + 1.0));
	star->z = lowest + int(range*rand() / (RAND_MAX + 1.0));

}

void DrawStars()
{
	GLUquadricObj *quadric;
	quadric = gluNewQuadric();
	gluQuadricDrawStyle(quadric, GLU_FILL);
	gluDeleteQuadric(quadric);
	for ( int i = 0; i < 500; i ++ ) {
		glPushMatrix();
		glTranslatef(stars[i].x, stars[i].y, stars[i].z);
		glColor3f(1, 1, 1);
		gluSphere(quadric, 1, 36, 18);
		glPopMatrix();
	}
}
 // Header file for our OpenGL functions
int main(int argc, char* argv[])
{
	// initialize GLUT library state
	glutInit(&argc, argv);

	glutInitDisplayMode(GLUT_RGBA | GLUT_DEPTH | GLUT_DOUBLE);


	// Define the main window size and initial position

	glutInitWindowSize(800, 800);
	glutInitWindowPosition(0, 0);

	// Create and label the main window
	glutCreateWindow("Two Moons are rotating around the Mars – viewpoint is from the space.");

	// Configure various properties of the OpenGL rendering context
	Setup();

	// Callbacks for the GL and GLUT events:

	// The rendering function
	glutDisplayFunc(Render);
	glutReshapeFunc(Resize);
	glutIdleFunc(Idle);
	glutKeyboardFunc(Keyboard);
	//glutMouseWheelFunc(MouseWheel);

	//Enter main event handling loop
	glutMainLoop();
	return 0;
}
