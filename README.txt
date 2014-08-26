GRAPHICS COURSEWORK

1)For the first question, I have added the second Bezier curve that has a smooth connecting
point.

I have replaced the drawing method provided by Java2D with the method defined by myself, using 
recursion and de Casteljau's algorithm.
I have changed the code that handles the mouse events and the updateLocation() method to allow 
the two curves to join smoothly at the middle point by:
-making the moves of the control points situated on one side and the other of the curve
symultaneous(points two and three in the code)
-making all the two points mentioned above and the middle point(end in the code) move together
when the middle point is dragged

2)For the second question , I have created a hierarchical animation for the Solar System.To
allow the planets spin in the desired direction, I have used different rotating coeficients,
to also make the move more realistic.

The steps used to create the systems were:
-creating of the sun, moon and earth shapes using Ellipse2D objects, and placing them to 
have the centre at the (0,0) coordinate of the screen
-apply on each of them three transformations:-rotate
					     -translate
                                             -rotate
The three operations allowed the planets to have both a rotation around the planet higher in
the hierarchy and also around its axis, at the distance specified by the translation operation.


